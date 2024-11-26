package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.entity.Category;
import com.challet.challetservice.domain.entity.Challenge;
import com.challet.challetservice.domain.entity.ChallengeStatus;
import com.challet.challetservice.domain.entity.QChallenge;
import com.challet.challetservice.domain.entity.QUserChallenge;
import com.challet.challetservice.domain.entity.User;
import com.challet.challetservice.domain.entity.UserChallenge;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserChallengeRepositoryImpl implements UserChallengeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserChallenge> getChallengeByPaymentCategory(String category, User user) {
        QUserChallenge qUserChallenge = QUserChallenge.userChallenge;
        QChallenge qChallenge = QChallenge.challenge;

        return queryFactory
            .select(qUserChallenge)
            .from(qUserChallenge)
            .join(qChallenge).on(qUserChallenge.challenge.eq(qChallenge))
            .where(qUserChallenge.user.eq(user)
                .and(qChallenge.category.eq(Category.valueOf(category)))
                .and(qChallenge.status.eq(ChallengeStatus.PROGRESSING)))
            .fetch();

    }
}
