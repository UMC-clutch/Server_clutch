package clutch.clutchserver.building.controller;


import clutch.clutchserver.building.dto.BuildingPriceRequestDto;
import clutch.clutchserver.building.service.BuildingService;
import clutch.clutchserver.calculate.dto.CalculateRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class BuildingController {

    private final BuildingService buildingService;

    // 사기 위험성 계산
    @Operation(summary = "건물 저장, 시세 조회 API")
    @PostMapping("/v1/buildingPrice")
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<?> saveBuildingAndGetMarketPrice(
            @Valid @RequestBody BuildingPriceRequestDto buildingPriceReq
            ) throws JsonProcessingException, UnsupportedEncodingException, ParseException, InterruptedException {
        // 현재 토큰을 사용중인 유저 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        return buildingService.getMarketPrice(useremail, buildingPriceReq);
    }

}
