package com.challet.challetservice.domain.dto.request;

import com.challet.challetservice.domain.entity.EmojiType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이모지 요청 DTO")
public record EmojiRequestDTO(

    @Schema(description = "대상 공유 거래내역 ID")
    Long sharedTransactionId,

    @Schema(description = "유저의 행위", allowableValues = {"ADD", "UPDATE", "DELETE"})
    ActionType action,

    @Schema(description = "유저가 선택한 이모지", allowableValues = {"GOOD", "SOSO", "BAD"})
    EmojiType type

) {

}
