package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.entity.Reward;
import java.util.List;

public interface RewardRepositoryCustom {

    List<Reward> findMyRewards(Long userId);

}
