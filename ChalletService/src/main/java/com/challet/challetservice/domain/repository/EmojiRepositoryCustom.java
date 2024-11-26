package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.dto.response.EmojiReactionDTO;
import com.challet.challetservice.domain.entity.SharedTransaction;
import com.challet.challetservice.domain.entity.User;

public interface EmojiRepositoryCustom {

    EmojiReactionDTO getEmojiReaction(Long sharedTransaction, User user);

}
