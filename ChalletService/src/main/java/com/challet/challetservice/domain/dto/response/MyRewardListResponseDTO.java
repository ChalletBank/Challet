package com.challet.challetservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "내가 획득한 리워드 리스트 DTO")
public record MyRewardListResponseDTO(

    @Schema(description = "내가 획득한 리워드 리스트")
    List<MyRewardInfoResponseDTO> rewardList

) {

}
