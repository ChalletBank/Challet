package com.challet.challetservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "이모지 응답 DTO")
@Builder
public record EmojiResponseDTO(

    @Schema(description = "공유 거래 내역 ID")
    Long sharedTransactionId,

    @Schema(description = "이모지 요청 보낸 사람 ID")
    Long userId,

    @Schema(description = "이모지 반응 정보")
    EmojiReactionDTO emoji

) {

}
