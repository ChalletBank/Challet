package com.challet.challetservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공유 거래 내역 수정 요청 DTO")
public record SharedTransactionUpdateRequestDTO(

    @Schema(description = "이미지")
    String image,

    @Schema(description = "결제 항목")
    String deposit,

    @Schema(description = "결제 금액")
    Long transactionAmount,

    @Schema(description = "결제 내용")
    String content

) {

}
