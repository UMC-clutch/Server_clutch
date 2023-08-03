package clutch.clutchserver.building.controller;


import clutch.clutchserver.building.dto.BuildingPriceRequestDto;
import clutch.clutchserver.building.dto.BuildingPriceResponseDto;
import clutch.clutchserver.building.service.BuildingService;
import clutch.clutchserver.calculate.dto.CalculateRequestDto;
import clutch.clutchserver.calculate.dto.FindCalculateResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Building", description = "빌딩과 관련된 API")
public class BuildingController {

    private final BuildingService buildingService;

    // 사기 위험성 계산
<<<<<<< HEAD
    @Operation(summary = "건물 저장, 시세 조회 API")
    @PostMapping("/v1/buildingPrcie")
=======
    @Operation(summary = "시세 조회", description = "건물을 저장하고 시세를 반환합니다.")
    @PostMapping("/v1/buildingPrice")
>>>>>>> 4fc5db61bc792814542780709d24c8478d4948d2
    @SecurityRequirement(name = "access-token")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BuildingPriceResponseDto.class)))
    public ResponseEntity<?> saveBuildingAndGetMarketPrice(
            @Valid @RequestBody BuildingPriceRequestDto buildingPriceReq
            ) throws JsonProcessingException, UnsupportedEncodingException, ParseException, InterruptedException {
        // 현재 토큰을 사용중인 유저 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        return buildingService.getMarketPrice(useremail, buildingPriceReq);
    }

}
