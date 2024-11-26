package com.challet.challetservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공유 거래 내역에 달린 댓글 조회 응답 DTO")
public record CommentResponseDTO(

    @Schema(description = "댓글 단 사람의 프로필 이미지")
    String profileImage,

    @Schema(description = "댓글 단 사람의 닉네임")
    String nickname,

    @Schema(description = "댓글 내용")
    String content

) {

}
