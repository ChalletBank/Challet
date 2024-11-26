package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.dto.response.ChallengeRoomHistoryResponseDTO;
import com.challet.challetservice.domain.dto.response.SharedTransactionDetailResponseDTO;
import com.challet.challetservice.domain.entity.Challenge;
import com.challet.challetservice.domain.entity.SharedTransaction;
import com.challet.challetservice.domain.entity.User;
import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.Lock;

public interface SharedTransactionRepositoryCustom {

    ChallengeRoomHistoryResponseDTO findHistoryByChallenge(Challenge challenge, User user, Long cursor);

    SharedTransactionDetailResponseDTO getDetail(SharedTransaction sharedTransaction, User user);

    Boolean isSameUser(SharedTransaction sharedTransaction, User user);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<SharedTransaction> findByChallengeIdWithLock(Long id);
}
