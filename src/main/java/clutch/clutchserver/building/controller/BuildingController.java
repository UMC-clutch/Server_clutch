package clutch.clutchserver.building.controller;

import clutch.clutchserver.building.dto.BuildingInfoDto;
import clutch.clutchserver.building.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class BuildingController {

    private final BuildingService buildingService;


    //건물 정보 입력
    @PostMapping("/v1/report")
    public ResponseEntity<?> saveBuilding(@RequestParam BuildingInfoDto buildingRequestDto,
                                       @PathVariable String address,
                                       @PathVariable String dong,
                                       @PathVariable String ho
    )
    {
        buildingService.saveBuilding(buildingRequestDto, address, dong, ho);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
