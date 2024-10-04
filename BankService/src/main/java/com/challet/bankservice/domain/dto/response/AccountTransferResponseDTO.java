package com.challet.bankservice.domain.dto.response;

import com.challet.bankservice.domain.entity.ChalletBank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "계좌 이체 후 응답 DTO")
public record AccountTransferResponseDTO(

    @Schema(description = "id")
    Long id,

    @Schema(description = "이름")
    String name,

    @Schema(description = "내 계좌")
    String myAccountNumber,

    @Schema(description = "카테고리")
    String category,

    @Schema(description = "잔액")
    Long balance,

    @Schema(description = "출금 금액")
    Long amount
) {

    public static AccountTransferResponseDTO fromTransferInfo(Long id ,ChalletBank fromBank,
        String toBankName, Long amount, String category) {
        return AccountTransferResponseDTO.builder()
            .id(id)
            .name(toBankName)
            .myAccountNumber(fromBank.getAccountNumber())
            .category(category)
            .balance(fromBank.getAccountBalance())
            .amount(amount)
            .build();
    }
}
