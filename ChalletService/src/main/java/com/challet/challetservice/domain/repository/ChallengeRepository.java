package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.entity.Challenge;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeRepositoryCustom {

    Optional<Challenge> findById(Long challengeId);

}
