package com.challet.challetservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "회원가입 요청 DTO")
public record UserRegisterRequestDTO(

    @Schema(description = "전화번호")
    @NotBlank(message = "전화번호는 필수 입력값 입니다.")
    String phoneNumber,

    @Schema(description = "간편비밀번호, 6자리만 입력가능")
    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    @Size(min = 6, max = 6)
    String password,

    @Schema(description = "닉네임")
    @NotBlank(message = "닉네임은 필수 입력값 입니다.")
    String nickname,

    @Schema(description = "프로필 사진")
    String profileImage,

    @Schema(description = "나이")
    @NotNull(message = "나이는 필수 입력값 입니다.")
    Integer age,

    @Schema(description = "성별")
    @NotNull(message = "성별은 필수 입력값 입니다.")
    Boolean gender,

    @Schema(description = "이름")
    @NotBlank(message = "이름은 필수 입력값 입니다.")
    String name

) {

}
