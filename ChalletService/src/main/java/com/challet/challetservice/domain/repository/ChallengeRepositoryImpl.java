package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.entity.Category;
import com.challet.challetservice.domain.entity.Challenge;
import com.challet.challetservice.domain.entity.ChallengeStatus;
import com.challet.challetservice.domain.entity.QChallenge;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChallengeRepositoryImpl implements ChallengeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Challenge> searchChallengeByKeywordAndCategory(String keyword, String category) {
        QChallenge qChallenge = QChallenge.challenge;
        BooleanBuilder builder = new BooleanBuilder();

        // keyword가 있으면 조건 적용
        if (keyword != null && !keyword.isEmpty()) {
            builder.and(qChallenge.title.containsIgnoreCase(keyword));
        }

        // category가 있으면 조건 적용
        if (category != null && !category.isEmpty()) {
            builder.and(qChallenge.category.eq(Category.valueOf(category)));
        }

        // 모집중인 챌린지만 조회
        builder.and(qChallenge.status.eq(ChallengeStatus.RECRUITING));

        return queryFactory.selectFrom(qChallenge).where(builder).fetch();
    }

    @Override
    public List<Challenge> getChallengesToStart(LocalDate today) {
        QChallenge qChallenge = QChallenge.challenge;
        return queryFactory
            .selectFrom(qChallenge)
            .where(qChallenge.startDate.eq(today)
                .and(qChallenge.status.eq(ChallengeStatus.RECRUITING)))
            .fetch();
    }

    @Override
    public List<Challenge> getChallengesToEnd(LocalDate today) {
        QChallenge qChallenge = QChallenge.challenge;
        return queryFactory
            .selectFrom(qChallenge)
            .where(qChallenge.endDate.before(today)
                .and(qChallenge.status.eq(ChallengeStatus.PROGRESSING)))
            .fetch();
    }

}
