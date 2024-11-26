package com.challet.challetservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "챌린지 내 현재 소비 금액 조회 응답 DTO")
public record SpendingAmountResponseDTO(

    @Schema(description = "소비 금액")
    Long spendingAmount

) {

}
