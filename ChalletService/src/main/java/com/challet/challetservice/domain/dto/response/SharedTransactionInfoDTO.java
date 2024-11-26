package com.challet.challetservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "공유 거래 내역 정보 DTO")
public record SharedTransactionInfoDTO(

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

    @Schema(description = "댓글 개수")
    Long commentCount

) {

}
