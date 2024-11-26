package com.challet.challetservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공유 거래 내역에 댓글 달기 요청 DTO")
public record CommentRegisterRequestDTO(

    @Schema(description = "댓글 내용")
    String content

) {

}
