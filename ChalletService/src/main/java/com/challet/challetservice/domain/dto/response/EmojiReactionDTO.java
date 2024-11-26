package com.challet.challetservice.domain.dto.response;

import com.challet.challetservice.domain.entity.EmojiType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이모지 관련 정보 DTO")
public record EmojiReactionDTO(

    @Schema(description = "GOOD 이모지 개수")
    Long goodCount,

    @Schema(description = "SOSO 이모지 개수")
    Long sosoCount,

    @Schema(description = "BAD 이모지 개수")
    Long badCount,

    @Schema(description = "내가 누른 이모지")
    EmojiType userEmoji

) {

}
