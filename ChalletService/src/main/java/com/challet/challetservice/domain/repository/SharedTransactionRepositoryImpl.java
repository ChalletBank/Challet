package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.dto.response.ChallengeRoomHistoryResponseDTO;
import com.challet.challetservice.domain.dto.response.EmojiReactionDTO;
import com.challet.challetservice.domain.dto.response.SharedTransactionDetailResponseDTO;
import com.challet.challetservice.domain.dto.response.SharedTransactionInfoDTO;
import com.challet.challetservice.domain.entity.Challenge;
import com.challet.challetservice.domain.entity.QComment;
import com.challet.challetservice.domain.entity.QSharedTransaction;
import com.challet.challetservice.domain.entity.QUser;
import com.challet.challetservice.domain.entity.QUserChallenge;
import com.challet.challetservice.domain.entity.SharedTransaction;
import com.challet.challetservice.domain.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class SharedTransactionRepositoryImpl implements SharedTransactionRepositoryCustom{
    
    private final JPAQueryFactory queryFactory;
    private final EmojiRepository emojiRepository;
    private final CommentRepository commentRepository;
    private static final int ITEM_SIZE = 7;
    private final EmojiRepositoryImpl emojiRepositoryImpl;

    @Override
    @Transactional(readOnly = true)
    public ChallengeRoomHistoryResponseDTO findHistoryByChallenge(Challenge challenge, User user, Long cursor) {

        QSharedTransaction qSharedTransaction = QSharedTransaction.sharedTransaction;
        QUserChallenge qUserChallenge = QUserChallenge.userChallenge;
        QUser qUser = QUser.user;
        QComment qComment = QComment.comment;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qSharedTransaction.userChallenge.challenge.eq(challenge));
        if (cursor != null) {
            builder.and(qSharedTransaction.id.lt(cursor));
        }

        List<SharedTransactionInfoDTO> infoList = queryFactory
            .select(Projections.constructor(
                SharedTransactionInfoDTO.class,
                qUser.id.as("userId"),
                qUser.nickname.as("nickname"),
                qUser.profileImage.as("profileImage"),
                qSharedTransaction.id.as("sharedTransactionId"),
                qSharedTransaction.deposit.as("deposit"),
                qSharedTransaction.transactionAmount.as("transactionAmount"),
                qSharedTransaction.transactionDateTime.as("transactionDateTime"),
                qSharedTransaction.content.as("content"),
                qSharedTransaction.image.as("image"),
                qComment.count().as("commentCount")
            ))
            .from(qSharedTransaction)
            .join(qUserChallenge).on(qSharedTransaction.userChallenge.eq(qUserChallenge))
            .join(qUser).on(qUserChallenge.user.eq(qUser))
            .leftJoin(qComment).on(qComment.sharedTransaction.eq(qSharedTransaction))
            .where(builder)
            .groupBy(
                qUser.id,
                qUser.nickname,
                qUser.profileImage,
                qSharedTransaction.id,
                qSharedTransaction.deposit,
                qSharedTransaction.transactionAmount,
                qSharedTransaction.transactionDateTime,
                qSharedTransaction.content,
                qSharedTransaction.image
            )
            .orderBy(qSharedTransaction.id.desc())
            .limit(ITEM_SIZE +1)
            .fetch();

        boolean hasNextPage = infoList.size() > ITEM_SIZE;
        if (hasNextPage) {
            infoList.removeLast();
        }

        List<SharedTransactionDetailResponseDTO> history = infoList.stream()
            .map(info -> {
                EmojiReactionDTO reaction = emojiRepositoryImpl.getEmojiReaction(
                    info.sharedTransactionId(), user);
                return SharedTransactionDetailResponseDTO.fromInfoAndReaction(info, reaction);
            })
            .toList();

        return new ChallengeRoomHistoryResponseDTO(hasNextPage, history);
    }

    @Override
    @Transactional(readOnly = true)
    public SharedTransactionDetailResponseDTO getDetail(SharedTransaction sharedTransaction, User user) {
        QSharedTransaction qSharedTransaction = QSharedTransaction.sharedTransaction;
        QUserChallenge qUserChallenge = QUserChallenge.userChallenge;
        QUser qUser = QUser.user;
        QComment qComment = QComment.comment;

        EmojiReactionDTO emojiReaction = emojiRepositoryImpl.getEmojiReaction(sharedTransaction.getId(), user);


        SharedTransactionInfoDTO info = queryFactory
            .select(Projections.constructor(
                SharedTransactionInfoDTO.class,
                qUser.id.as("userId"),
                qUser.nickname.as("nickname"),
                qUser.profileImage.as("profileImage"),
                qSharedTransaction.id.as("sharedTransactionId"),
                qSharedTransaction.deposit.as("deposit"),
                qSharedTransaction.transactionAmount.as("transactionAmount"),
                qSharedTransaction.transactionDateTime.as("transactionDateTime"),
                qSharedTransaction.content.as("content"),
                qSharedTransaction.image.as("image"),
                qComment.count().as("commentCount")
            ))
            .from(qSharedTransaction)
            .join(qUserChallenge).on(qSharedTransaction.userChallenge.eq(qUserChallenge))
            .join(qUser).on(qUserChallenge.user.eq(qUser))
            .leftJoin(qComment).on(qComment.sharedTransaction.eq(qSharedTransaction))
            .where(qSharedTransaction.eq(sharedTransaction))
            .fetchOne();

        return SharedTransactionDetailResponseDTO.fromInfoAndReaction(info, emojiReaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isSameUser(SharedTransaction sharedTransaction, User user) {
        QSharedTransaction qSharedTransaction = QSharedTransaction.sharedTransaction;
        QUser qUser = QUser.user;
        QUserChallenge qUserChallenge = QUserChallenge.userChallenge;

        Optional<User> sharedUser = Optional.ofNullable(queryFactory
            .select(qUser)
            .from(qSharedTransaction)
            .join(qSharedTransaction.userChallenge, qUserChallenge)
            .join(qUserChallenge.user, qUser)
            .where(qSharedTransaction.eq(sharedTransaction))
            .fetchOne());

        return sharedUser.map(user::equals).orElse(false);
    }

    @Override
    public List<SharedTransaction> findByChallengeIdWithLock(Long id) {
        QSharedTransaction qSharedTransaction = QSharedTransaction.sharedTransaction;
        return queryFactory
            .selectFrom(qSharedTransaction)
            .where(qSharedTransaction.userChallenge.challenge.id.eq(id))
            .fetch();
    }


}
