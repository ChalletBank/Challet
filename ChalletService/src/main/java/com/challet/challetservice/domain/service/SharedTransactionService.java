package com.challet.challetservice.domain.service;

import com.challet.challetservice.domain.dto.request.CommentRegisterRequestDTO;
import com.challet.challetservice.domain.dto.request.EmojiRequestDTO;
import com.challet.challetservice.domain.dto.response.CommentListResponseDTO;
import com.challet.challetservice.domain.dto.response.EmojiReactionDTO;
import com.challet.challetservice.domain.dto.response.EmojiResponseDTO;
import com.challet.challetservice.domain.dto.response.SharedTransactionDetailResponseDTO;

public interface SharedTransactionService {

    EmojiResponseDTO handleEmoji(String header, EmojiRequestDTO request);

    SharedTransactionDetailResponseDTO getDetail(String header, Long id);

    CommentListResponseDTO getComment(String header, Long id);

    void registerComment(String header, Long id, CommentRegisterRequestDTO request);
}
