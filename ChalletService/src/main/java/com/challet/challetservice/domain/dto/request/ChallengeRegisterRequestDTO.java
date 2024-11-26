package com.challet.challetservice.domain.dto.request;

import com.challet.challetservice.domain.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "챌린지 생성 요청 DTO")
public record ChallengeRegisterRequestDTO(

    @Schema(description = "챌린지 카테고리", allowableValues = {"DELIVERY", "TRANSPORT", "COFFEE",
        "SHOPPING"})
    @NotNull(message = "카테고리는 필수 항목입니다.")
    Category category,

    @Schema(description = "챌린지 제목")
    @NotBlank(message = "제목은 필수 항목입니다.")
    String title,

    @Schema(description = "챌린지 소비한도")
    @NotNull(message = "소비한도는 필수 항목입니다.")
    Long spendingLimit,

    @Schema(description = "챌린지 시작날짜")
    @NotNull(message = "시작 날짜는 필수 항목입니다.")
    LocalDate startDate,

    @Schema(description = "챌린지 마감날짜")
    @NotNull(message = "마감 날짜는 필수 항목입니다.")
    LocalDate endDate,

    @Schema(description = "참여 가능 인원")
    @NotNull(message = "참여 가능 인원은 필수 항목입니다.")
    @Min(value = 1, message = "1명이상이어야 합니다.")
    @Max(value = 10, message = "10명 이하여야 합니다.")
    Integer maxParticipants,

    @Schema(description = "공개 여부 (비공개 : false, 공개 : true)")
    @NotNull(message = "공개 여부는 필수 항목입니다.")
    Boolean isPublic

) {

}
