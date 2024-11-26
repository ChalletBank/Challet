package com.challet.challetservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로필 사진 수정 요청 DTO")
public record UserUpdateProfileRequestDTO(

    @Schema(description = "새 프로필 이미지")
    String profileImage

) {

}
