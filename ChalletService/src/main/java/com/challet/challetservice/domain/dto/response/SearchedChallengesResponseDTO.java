package com.challet.challetservice.domain.dto.response;

import com.challet.challetservice.domain.entity.SearchedChallenge;
import java.util.List;
import lombok.Builder;

@Builder
public record SearchedChallengesResponseDTO(int count, boolean isLastPage, List<SearchedChallenge> searchedChallenges) {

    public static SearchedChallengesResponseDTO fromSearchedChallenges(List<SearchedChallenge> searchedChallenges, boolean isLastPage) {
        return SearchedChallengesResponseDTO.builder()
            .count(searchedChallenges.size())
            .searchedChallenges(searchedChallenges)
            .isLastPage(isLastPage)
            .build();
    }

    public static SearchedChallengesResponseDTO fromSearchedChallenges(List<SearchedChallenge> searchedChallenges) {
        return SearchedChallengesResponseDTO.builder()
            .count(searchedChallenges.size())
            .searchedChallenges(searchedChallenges)
            .build();
    }
}
