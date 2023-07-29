package clutch.clutchserver.report.controller;

import clutch.clutchserver.building.dto.BuildingRequestDto;
import clutch.clutchserver.building.service.BuildingService;
import clutch.clutchserver.report.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.codef.api.EasyCodef;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final BuildingService buildingService;

//    @Operation(description = "신고 접수 - 건물 정보 입력 받는 기능")
//    @PostMapping(value = "/auth/report")
//    public ResponseEntity<?> receiveReport(@RequestBody BuildingRequestDto buildingRequestDto){
//        buildingService.saveBuilding(buildingRequestDto);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }





}
