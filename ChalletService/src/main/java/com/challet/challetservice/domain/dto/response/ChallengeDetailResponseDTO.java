package com.challet.challetservice.domain.dto.response;

import com.challet.challetservice.domain.entity.Challenge;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;

@Schema(description = "챌린지 상세 정보 응답 DTO")
@Builder
public record ChallengeDetailResponseDTO(

    @Schema(description = "챌린지ID")
    Long challengeId,

    @Schema(description = "챌린지 진행 상태", allowableValues = {"RECRUITING", "PROGRESSING", "END"})
    String status,

    @Schema(description = "챌린지 참여 여부")
    Boolean isIncluded,

    @Schema(description = "챌린지 카테고리", allowableValues = {"DELIVERY", "TRANSPORT", "COFFEE",
        "SHOPPING"})
    String category,

    @Schema(description = "챌린지 제목")
    String title,

    @Schema(description = "챌린지 소비한도")
    Long spendingLimit,

    @Schema(description = "챌린지 시작날짜")
    LocalDate startDate,

    @Schema(description = "챌린지 마감날짜")
    LocalDate endDate,

    @Schema(description = "참여 가능 인원")
    Integer maxParticipants,

    @Schema(description = "현재 참여 인원")
    Integer currentParticipants,

    @Schema(description = "공개 여부 (비공개 : 0, 공개 : 1)")
    Boolean isPublic,

    @Schema(description = "초대코드")
    String inviteCode

) {

    public static ChallengeDetailResponseDTO fromChallenge(Challenge challenge,
        boolean isIncluded, int currentParticipants) {
        return ChallengeDetailResponseDTO.builder()
            .challengeId(challenge.getId())
            .status(String.valueOf(challenge.getStatus()))
            .isIncluded(isIncluded)
            .category(String.valueOf(challenge.getCategory()))
            .title(challenge.getTitle())
            .spendingLimit(challenge.getSpendingLimit())
            .startDate(challenge.getStartDate())
            .endDate(challenge.getEndDate())
            .maxParticipants(challenge.getMaxParticipants())
            .currentParticipants(currentParticipants)
            .isPublic(challenge.getInviteCode() == null)
            .inviteCode(challenge.getInviteCode())
            .build();
    }

}
