package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.entity.Emoji;
import com.challet.challetservice.domain.entity.EmojiType;
import com.challet.challetservice.domain.entity.SharedTransaction;
import com.challet.challetservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmojiRepository extends JpaRepository<Emoji, Long> {

    long countBySharedTransactionAndType(SharedTransaction sharedTransaction, EmojiType type);

    Emoji findByUserAndSharedTransaction(User user, SharedTransaction sharedTransaction);
}
