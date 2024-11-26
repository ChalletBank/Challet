package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.dto.response.CommentListResponseDTO;
import com.challet.challetservice.domain.entity.SharedTransaction;

public interface CommentRepositoryCustom {

    CommentListResponseDTO getCommentList(SharedTransaction sharedTransaction);

}
