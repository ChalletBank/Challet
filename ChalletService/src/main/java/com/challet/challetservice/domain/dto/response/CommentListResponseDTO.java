package com.challet.challetservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "댓글 목록 응답 DTO")
public record CommentListResponseDTO(

    @Schema(description = "댓글 목록")
    List<CommentResponseDTO> comments

) {

}
