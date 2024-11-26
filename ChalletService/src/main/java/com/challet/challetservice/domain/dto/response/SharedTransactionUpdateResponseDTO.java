package com.challet.challetservice.domain.dto.response;

import com.challet.challetservice.domain.dto.request.ActionType;
import com.challet.challetservice.domain.dto.request.SharedTransactionUpdateRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record SharedTransactionUpdateResponseDTO(

    @Schema(description = "유저 행위")
    ActionType action,

    @Schema(description = "수정된 거래 내역 ID")
    Long id,

    @Schema(description = "이미지")
    String image,

    @Schema(description = "결제 항목")
    String deposit,

    @Schema(description = "결제 금액")
    Long transactionAmount,

    @Schema(description = "결제 내용")
    String content

) {

    public static SharedTransactionUpdateResponseDTO fromRequest(SharedTransactionUpdateRequestDTO request, Long transactionId) {
        return SharedTransactionUpdateResponseDTO.builder()
            .action(ActionType.UPDATE)
            .id(transactionId)
            .image(request.image())
            .deposit(request.deposit())
            .transactionAmount(request.transactionAmount())
            .content(request.content())
            .build();
    }

}
