package clutch.clutchserver.report.controller;

import clutch.clutchserver.building.dto.BuildingRequestDto;
import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.repository.BuildingRepository;
import clutch.clutchserver.building.service.BuildingService;
import clutch.clutchserver.global.DefaultAssert;
import clutch.clutchserver.global.error.DefaultException;
import clutch.clutchserver.global.payload.ApiResponse;
import clutch.clutchserver.report.dto.ReportResponseDto;
import clutch.clutchserver.report.entity.Report;
import clutch.clutchserver.report.repository.ReportRepository;
import clutch.clutchserver.report.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.MessageUtil;
import org.json.simple.parser.ParseException;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final BuildingService buildingService;
    private final BuildingRepository buildingRepository;
    private final ReportRepository reportRepository;

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

        ReportResponseDto reportResponseDto = reportService.getReportResDto(buildingRequestDto);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(reportResponseDto)
                .build();

        return ResponseEntity.ok(apiResponse);

    }







}
