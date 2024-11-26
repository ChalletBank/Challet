package com.challet.challetservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "챌린지방 내역 조회 응답 DTO")
public record ChallengeRoomHistoryResponseDTO(

    @Schema(description = "다음 페이지 유무")
    Boolean hasNextPage,

    @Schema(description = "공유 거래 내역 리스트")
    List<SharedTransactionDetailResponseDTO> history

) {

}
