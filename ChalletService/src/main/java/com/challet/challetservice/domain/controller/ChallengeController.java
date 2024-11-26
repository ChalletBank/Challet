package com.challet.challetservice.domain.controller;

import com.challet.challetservice.domain.dto.request.ChallengeJoinRequestDTO;
import com.challet.challetservice.domain.dto.request.ChallengeRegisterRequestDTO;
import com.challet.challetservice.domain.dto.response.ChallengeDetailResponseDTO;
import com.challet.challetservice.domain.dto.response.ChallengeRoomHistoryResponseDTO;
import com.challet.challetservice.domain.dto.response.ChallengeListResponseDTO;
import com.challet.challetservice.domain.dto.response.SearchedChallengesResponseDTO;
import com.challet.challetservice.domain.dto.response.SpendingAmountResponseDTO;
import com.challet.challetservice.domain.service.ChallengeService;
import com.challet.challetservice.global.exception.ExceptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/challet/challenges")
@RequiredArgsConstructor
@Tag(name = "ChallengeController", description = "챌린지 관련 Controller - Authorize 필수")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Operation(summary = "챌린지 생성 (완료)", description = "챌린지 정보를 입력하여 챌린지 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "챌린지 생성 성공"),
        @ApiResponse(responseCode = "400", description = "챌린지 생성 실패", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
        @ApiResponse(responseCode = "401", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PostMapping()
    public ResponseEntity<String> registerChallenge(
        @RequestHeader(value = "Authorization", required = false) String header,
        @Valid @RequestBody ChallengeRegisterRequestDTO request) {
        challengeService.createChallenge(header, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("챌린지 생성 성공");
    }

    @Operation(summary = "내 챌린지 조회 (완료)", description = "내가 참여한 챌린지 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "내 챌린지 목록 조회 성공"),
        @ApiResponse(responseCode = "204", description = "내 챌린지 목록이 비어있음"),
        @ApiResponse(responseCode = "400", description = "내 챌린지 목록 조회 실패", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
        @ApiResponse(responseCode = "401", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @GetMapping("/my-challenges")
    public ResponseEntity<ChallengeListResponseDTO> getMyChallenges(
        @RequestHeader(value = "Authorization", required = false) String header) {
        ChallengeListResponseDTO myChallenges = challengeService.getMyChallenges(header);
        if (myChallenges == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(myChallenges);
    }

    @Operation(summary = "챌린지 검색 - Elasticsearch (완료)", description = "모집중인 챌린지 중 검색어와 카테고리로 챌린지 검색" +
        "검색어와 카테고리 모두 주어진 값이 없다면 전체조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "챌린지 검색 성공"),
        @ApiResponse(responseCode = "204", description = "검색 결과 없음"),
        @ApiResponse(responseCode = "400", description = "챌린지 검색 실패", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
        @ApiResponse(responseCode = "401", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @Parameters(value = {
        @Parameter(name = "category", description = "카테고리", in = ParameterIn.QUERY),
        @Parameter(name = "keyword", description = "검색어", in = ParameterIn.QUERY),
    })
    @GetMapping()
    public ResponseEntity<SearchedChallengesResponseDTO> searchChallenges(
        @RequestHeader("Authorization") String header,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        SearchedChallengesResponseDTO result = challengeService.searchChallengesFromElasticsearch(header, category, keyword, page, size);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "챌린지 검색 - Elasticsearch (완료)", description = "모집중인 챌린지 중 검색어와 카테고리로 챌린지 검색" +
        "검색어와 카테고리 모두 주어진 값이 없다면 전체조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "챌린지 검색 성공"),
        @ApiResponse(responseCode = "204", description = "검색 결과 없음"),
        @ApiResponse(responseCode = "400", description = "챌린지 검색 실패", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
        @ApiResponse(responseCode = "401", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @Parameters(value = {
        @Parameter(name = "category", description = "카테고리", in = ParameterIn.QUERY),
        @Parameter(name = "keyword", description = "검색어", in = ParameterIn.QUERY),
    })
    @GetMapping("/elasticsearch")
    public ResponseEntity<SearchedChallengesResponseDTO> searchChallengesByElasticSearch(
        @RequestHeader("Authorization") String header,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String keyword) {

        SearchedChallengesResponseDTO result = challengeService.searchChallengesFromElasticsearch(header, category, keyword);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "챌린지 검색 - MySQL (완료)", description = "모집중인 챌린지 중 검색어와 카테고리로 챌린지 검색" +
        "검색어와 카테고리 모두 주어진 값이 없다면 전체조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "챌린지 검색 성공"),
        @ApiResponse(responseCode = "204", description = "검색 결과 없음"),
        @ApiResponse(responseCode = "400", description = "챌린지 검색 실패", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
        @ApiResponse(responseCode = "401", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @Parameters(value = {
        @Parameter(name = "category", description = "카테고리", in = ParameterIn.QUERY),
        @Parameter(name = "keyword", description = "검색어", in = ParameterIn.QUERY),
    })
    @GetMapping("/mysql")
    public ResponseEntity<ChallengeListResponseDTO> searchChallengesByMySQL(
        @RequestHeader(value = "Authorization", required = false) String header,
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "keyword", required = false) String keyword) {
        ChallengeListResponseDTO searchChallenges = challengeService.searchChallengesFromMySQL(header,
            category, keyword);
        if (searchChallenges == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(searchChallenges);

    }

    @Operation(summary = "챌린지 정보 상세 조회 (완료)", description = "챌린지ID로 챌린지 정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "챌린지 상세 조회 성공"),
        @ApiResponse(responseCode = "400", description = "챌린지 상세 조회 실패", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
        @ApiResponse(responseCode = "401", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @Parameters(value = {
        @Parameter(name = "id", description = "챌린지ID", in = ParameterIn.PATH),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDetailResponseDTO> getChallengeDetail(
        @RequestHeader(value = "Authorization", required = false) String header,
        @PathVariable("id") Long id) {
        ChallengeDetailResponseDTO challengeDetail = challengeService.getChallengeDetail(header,
            id);
        return ResponseEntity.status(HttpStatus.OK).body(challengeDetail);
    }

    @Operation(summary = "챌린지 참여 신청 (완료)", description = "챌린지 참여 신청하는 요청" +
        "공개 챌린지의 경우 초대코드는 null")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "챌린지 참여 신청 성공"),
        @ApiResponse(responseCode = "400", description = "챌린지 참여 신청 실패", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
        @ApiResponse(responseCode = "401", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @Parameters(value = {
        @Parameter(name = "id", description = "챌린지ID", in = ParameterIn.PATH),
    })
    @PostMapping("/{id}")
    public ResponseEntity<String> joinChallenge(
        @RequestHeader(value = "Authorization", required = false) String header,
        @PathVariable("id") Long id,
        @RequestBody ChallengeJoinRequestDTO request) {
        challengeService.joinChallenge(header, id, request);
        return ResponseEntity.status(HttpStatus.OK).body("챌린지 참여 신청 성공");
    }

    @Operation(summary = "챌린지 내 공유 거래 내역 조회 (완료)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "공유 거래 내역 조회 성공"),
        @ApiResponse(responseCode = "400", description = "공유 거래 내역 조회 실패", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
        @ApiResponse(responseCode = "401", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @Parameters(value = {
        @Parameter(name = "id", description = "챌린지ID", in = ParameterIn.PATH),
    })
    @GetMapping("/{id}/history")
    public ResponseEntity<ChallengeRoomHistoryResponseDTO> getChallengeRoomHistory(
        @RequestHeader(value = "Authorization", required = false) String header,
        @PathVariable("id") Long id,
        @RequestParam(required = false) Long cursor) {
        ChallengeRoomHistoryResponseDTO history = challengeService.getChallengeRoomHistory(header, id, cursor);
        return ResponseEntity.status(HttpStatus.OK).body(history);
    }

    @Operation(summary = "챌린지 내 현재 소비 금액 조회 (완료)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "공유 거래 내역 조회 성공"),
        @ApiResponse(responseCode = "400", description = "공유 거래 내역 조회 실패", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
        @ApiResponse(responseCode = "401", description = "접근 권한 없음", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
    })
    @Parameters(value = {
        @Parameter(name = "id", description = "챌린지ID", in = ParameterIn.PATH),
    })
    @GetMapping("/{id}/spending-amount")
    public ResponseEntity<SpendingAmountResponseDTO> getSpendingAmount(
        @RequestHeader(value = "Authorization", required = false) String header,
        @PathVariable("id") Long id) {
        SpendingAmountResponseDTO spendingAmount = challengeService.getSpendingAmount(header, id);
        return ResponseEntity.status(HttpStatus.OK).body(spendingAmount);
    }
}
