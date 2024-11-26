package com.challet.challetservice.domain.dto.response;

import com.challet.challetservice.domain.entity.EmojiType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;

@Schema(description = "공유 거래 내역 조회 상세 DTO")
@Builder
public record SharedTransactionDetailResponseDTO(

    @Schema(description = "공유자 ID")
    Long userId,

    @Schema(description = "공유자 닉네임")
    String nickname,

    @Schema(description = "공유자 프로필 이미지")
    String profileImage,

    @Schema(description = "공유 거래 내역 ID")
    Long sharedTransactionId,

    @Schema(description = "출금처")
    String deposit,

    @Schema(description = "거래 금액")
    Long transactionAmount,

    @Schema(description = "거래일시")
    LocalDateTime transactionDateTime,

    @Schema(description = "내용")
    String content,

    @Schema(description = "이미지")
    String image,

    @Schema(description = "GOOD 이모지 개수")
    Long goodCount,

    @Schema(description = "SOSO 이모지 개수")
    Long sosoCount,

    @Schema(description = "BAD 이모지 개수")
    Long badCount,

    @Schema(description = "댓글 개수")
    Long commentCount,

    @Schema(description = "내가 누른 이모지")
    EmojiType userEmoji

) {

    public static SharedTransactionDetailResponseDTO fromInfoAndReaction(SharedTransactionInfoDTO info, EmojiReactionDTO reaction){
        return  SharedTransactionDetailResponseDTO.builder()
            .userId(info.userId())
            .nickname(info.nickname())
            .profileImage(info.profileImage())
            .sharedTransactionId(info.sharedTransactionId())
            .deposit(info.deposit())
            .transactionAmount(info.transactionAmount())
            .transactionDateTime(info.transactionDateTime())
            .content(info.content())
            .image(info.image())
            .commentCount(info.commentCount())
            .goodCount(reaction.goodCount())
            .sosoCount(reaction.sosoCount())
            .badCount(reaction.badCount())
            .userEmoji(reaction.userEmoji())
            .build();
    }

}
