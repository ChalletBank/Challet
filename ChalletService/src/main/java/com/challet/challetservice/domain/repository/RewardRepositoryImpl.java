package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.entity.QReward;
import com.challet.challetservice.domain.entity.Reward;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RewardRepositoryImpl implements RewardRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    public List<Reward> findMyRewards(Long userId) {
        QReward qReward = QReward.reward;

        return queryFactory.selectFrom(qReward)
            .where(qReward.user.id.eq(userId))
            .orderBy(qReward.challenge.endDate.desc())
            .fetch();
    }

}
