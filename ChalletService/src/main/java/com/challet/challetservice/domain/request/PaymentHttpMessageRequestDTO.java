package com.challet.challetservice.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "결제 내용 DTO")
@Builder
public record PaymentHttpMessageRequestDTO(

    @Schema(description = "유저 전화번호")
    String phoneNumber,

    @Schema(description = "결제 금액")
    Long transactionAmount,

    @Schema(description = "결제 장소")
    String deposit,

    @Schema(description = "결제 카테고리", allowableValues = {"DELIVERY", "TRANSPORT", "COFFEE",
        "SHOPPING"})
    String category
) {

}