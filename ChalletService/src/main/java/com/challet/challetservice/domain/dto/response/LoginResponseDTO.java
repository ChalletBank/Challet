package com.challet.challetservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입, 로그인 응답 access token 정보")
public record LoginResponseDTO(

    @Schema(description = "access token 정보")
    String accessToken,

    @Schema(description = "유저ID")
    Long userId

) {

}
