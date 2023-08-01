package clutch.clutchserver.report.service;

import clutch.clutchserver.building.dto.BuildingRequestDto;
import clutch.clutchserver.building.dto.BuildingResponseDto;
import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.repository.BuildingRepository;
import clutch.clutchserver.building.service.BuildingService;
import clutch.clutchserver.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final BuildingRepository buildingRepository;
    private final BuildingService buildingService;

    //건물 정보 입력 반환
    public BuildingResponseDto getBuildingResDto(BuildingRequestDto buildingRequestDto){


            Building building = buildingService.saveBuilding(buildingRequestDto);

        BuildingResponseDto buildingResponseDto = BuildingResponseDto.builder()
                .buildingId(building.getBuildingId())
                .buildingName(building.getBuildingName())
                .address(building.getAddress())
                .type(building.getType())
                .build();

            return buildingResponseDto;
        }

    }


