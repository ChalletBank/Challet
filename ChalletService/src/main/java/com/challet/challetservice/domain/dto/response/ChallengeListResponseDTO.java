package com.challet.challetservice.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "챌린지 리스트 응답 DTO")
public record ChallengeListResponseDTO(
	@Schema(description = "챌린지 개수")
	int count,
	@Schema(description = "챌린지 리스트")
	List<ChallengeInfoResponseDTO> challengeList
) {

	public static ChallengeListResponseDTO fromChallengeList(
		List<ChallengeInfoResponseDTO> challengeList
	) {
		return ChallengeListResponseDTO.builder()
			.count(challengeList.size())
			.challengeList(challengeList)
			.build();
	}
}
