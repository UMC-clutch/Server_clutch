package clutch.clutchserver.report.controller;

import clutch.clutchserver.building.dto.BuildingRequestDto;
import clutch.clutchserver.building.dto.BuildingResponseDto;
import clutch.clutchserver.global.payload.ApiResponse;
import clutch.clutchserver.report.dto.ReportResponseDto;
import clutch.clutchserver.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@Tag(name = "Report", description = "신고접수와 관련된 API")
public class ReportController {

    private final ReportService reportService;

    @Operation(description = "건물 정보 입력")
    @SecurityRequirement(name = "access-token")
    @ApiResponses(
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = ReportResponseDto.class))
            )
    )
    @PostMapping(value = "/v1/report")
    public ResponseEntity<?> receiveReport(@RequestBody BuildingRequestDto buildingRequestDto) {

        BuildingResponseDto buildingResponseDto = reportService.getBuildingResDto(buildingRequestDto);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(buildingResponseDto)
                .build();

        return ResponseEntity.ok(apiResponse);

    }

    @Operation(summary = "신고 접수 내역 조회", description = "user 토큰으로 신고내역, 계약, 건물 정보 조회")
    @GetMapping("/v1/report")
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<?> completedReport() {
        // 현재 토큰을 사용중인 유저 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        ReportResponseDto reportResponseDto = reportService.getCompReport(useremail);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(reportResponseDto)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "신고 취소", description = "user 토큰으로 신고내역 삭제")
    @DeleteMapping("/v1/report")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully deleted report")
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<?> deleteReport() {
        // 현재 토큰을 사용중인 유저 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        reportService.reportDelete(useremail);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
