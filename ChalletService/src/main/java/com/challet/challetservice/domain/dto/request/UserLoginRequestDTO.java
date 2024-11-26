package com.challet.challetservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청 DTO")
public record UserLoginRequestDTO(

    @Schema(description = "가입한 전화번호")
    String phoneNumber,

    @Schema(description = "가입한 비밀번호")
    String password

) {

}
