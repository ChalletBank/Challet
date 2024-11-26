package com.challet.challetservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "닉네임 수정 요청 DTO")
public record UserUpdateNicknameRequestDTO(

    @Schema(description = "새 닉네임")
    String nickname

) {

}
