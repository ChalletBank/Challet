package com.challet.challetservice.domain.dto.response;

import com.challet.challetservice.domain.entity.Category;
import com.challet.challetservice.domain.entity.Challenge;
import com.challet.challetservice.domain.entity.Reward;
import com.challet.challetservice.domain.entity.UserChallenge;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

@Schema(description = "리워드 상세 조회 응답 DTO")
@Builder
public record RewardDetailResponseDTO(

    @Schema(description = "챌린지 제목")
    String title,

    @Schema(description = "챌린지 카테고리")
    Category category,

    @Schema(description = "챌린지 소비한도")
    Long spendingLimit,

    @Schema(description = "챌린지 시작날짜")
    LocalDate startDate,

    @Schema(description = "챌린지 마감날짜")
    LocalDate endDate,

    @Schema(description = "실제 소비금액")
    Long spendingAmount,

    @Schema(description = "리워드 종류(실패 : 0, 성공 : 1)")
    Boolean type

) {

    public static RewardDetailResponseDTO fromReward(Reward reward, UserChallenge userChallenge) {
        Challenge challenge = userChallenge.getChallenge();
        return RewardDetailResponseDTO.builder()
            .title(challenge.getTitle())
            .category(challenge.getCategory())
            .spendingLimit(challenge.getSpendingLimit())
            .startDate(challenge.getStartDate())
            .endDate(challenge.getEndDate())
            .spendingAmount(userChallenge.getSpendingAmount())
            .type(reward.getType())
            .build();
    }

}
