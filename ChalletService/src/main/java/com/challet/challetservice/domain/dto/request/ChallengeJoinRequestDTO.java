package com.challet.challetservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "챌린지 참여 신청 요청 DTO")
public record ChallengeJoinRequestDTO(

    @Schema(description = "챌린지 공개 여부 (공개 : true, 비공개 : false)")
    Boolean isPublic,

    @Schema(description = "초대코드")
    String inviteCode

) {

}
