package com.challet.challetservice.domain.dto.response;

import com.challet.challetservice.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "유저 정보 조회 응답 DTO")
@Builder
public record UserInfoResponseDTO(

    @Schema(description = "프로필 사진 url")
    String profileImage,

    @Schema(description = "닉네임")
    String nickname

) {

    public static UserInfoResponseDTO fromUser(User user) {
        return UserInfoResponseDTO.builder()
            .profileImage(user.getProfileImage())
            .nickname(user.getNickname())
            .build();
    }

}
