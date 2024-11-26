package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.entity.Reward;
import com.challet.challetservice.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Integer>, RewardRepositoryCustom {

    Optional<Reward> findById(Long id);

}
