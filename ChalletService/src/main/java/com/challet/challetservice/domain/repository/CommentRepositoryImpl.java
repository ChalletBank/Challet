package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.dto.response.CommentListResponseDTO;
import com.challet.challetservice.domain.dto.response.CommentResponseDTO;
import com.challet.challetservice.domain.entity.QComment;
import com.challet.challetservice.domain.entity.QUser;
import com.challet.challetservice.domain.entity.SharedTransaction;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public CommentListResponseDTO getCommentList(SharedTransaction sharedTransaction) {
        QComment qComment = QComment.comment;
        QUser qUser = QUser.user;

        List<CommentResponseDTO> comments = queryFactory
            .select(Projections.constructor(
                CommentResponseDTO.class,
                qUser.profileImage,
                qUser.nickname,
                qComment.content
            ))
            .from(qComment)
            .join(qUser).on(qComment.user.eq(qUser))
            .where(qComment.sharedTransaction.eq(sharedTransaction))
            .fetch();

        return new CommentListResponseDTO(comments);
    }
}
