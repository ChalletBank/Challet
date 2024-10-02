package com.challet.kbbankservice.domain.dto.response;

import com.challet.kbbankservice.domain.entity.KbBank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "타 계좌 이체 후 전달 DTO")
public record BankTransferResponseDTO(

    @Schema(description = "이체 받은 사용자 이름")
    String name
) {
    public static BankTransferResponseDTO fromBankTransferResponseDTO(KbBank kbBank) {
        return BankTransferResponseDTO
            .builder()
            .name(kbBank.getName())
            .build();
    }
}