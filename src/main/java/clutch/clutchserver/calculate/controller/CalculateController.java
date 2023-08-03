package clutch.clutchserver.calculate.controller;

import clutch.clutchserver.calculate.dto.CalculateRequestDto;
import clutch.clutchserver.calculate.dto.CalculateResponseDto;
import clutch.clutchserver.calculate.dto.FindCalculateResponseDto;
import clutch.clutchserver.calculate.service.CalculateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Calculate", description = "계산과 관련된 API")
public class CalculateController {

    private final CalculateService calculateService;

    // 사기 위험성 계산
    @Operation(summary = "사기 위험성 계산", description = "사기 위험성을 계산하고 저장합니다.")
    @PostMapping ("/v1/calculate")
    @SecurityRequirement(name = "access-token")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalculateResponseDto.class)))
    public ResponseEntity<?> calculateRisk(
            @Valid @RequestBody CalculateRequestDto calculateReq
    ) throws JsonProcessingException {
        // 현재 토큰을 사용중인 유저 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        return calculateService.calculateRisk(useremail, calculateReq);
    }

    // 사기 위험성 계산 내역 조회(전체)
    @Operation(summary = "사기 위험성 계산 내역 조회", description = "해당 유저의 모든 계산 내역을 조회합니다.")
    @GetMapping("/v1/calculate")
    @SecurityRequirement(name = "access-token")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindCalculateResponseDto.class)))
    public ResponseEntity<?> findCalculation() throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        return calculateService.findCalculation(userEmail);
    }

    // 사기 위험성 계산 내역 조회(계산ID로 하나만)
    @Operation(summary = "사기 위험성 계산 내역 조회(ID)", description = "파라미터로 받은 ID로 하나의 계산 내역을 조회합니다.")
    @GetMapping("/v1/calculate/{calculateId}")
    @SecurityRequirement(name = "access-token")
    @Parameter(name = "calculateId", description = "조회할 계산 ID")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindCalculateResponseDto.class)))
    public ResponseEntity<?> findOneCalculation(@RequestParam Long calculateId) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        return calculateService.findOneCalculation(userEmail, calculateId);
    }

}