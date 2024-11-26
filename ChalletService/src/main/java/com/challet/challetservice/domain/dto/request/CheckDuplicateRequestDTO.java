package com.challet.challetservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 중복검사 요청 DTO")
public record CheckDuplicateRequestDTO(

    @Schema(description = "회원가입 할 전화번호")
    String phoneNumber

) {

}
