package com.challet.challetservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 중복검사 응답 DTO")
public record CheckDuplicateResponseDTO(

    @Schema(description = "중복여부", allowableValues = {"true", "false"})
    Boolean isDuplicated

) {

}
