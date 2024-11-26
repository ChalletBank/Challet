package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.dto.response.UserInfoMessageResponseDTO;

public interface UserRepositoryCustom {

    UserInfoMessageResponseDTO getUserInfoMessage(String phoneNumber);
}
