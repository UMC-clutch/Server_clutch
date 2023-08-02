package clutch.clutchserver.report.service;

import clutch.clutchserver.address.entity.Address;
import clutch.clutchserver.building.dto.BuildingRequestDto;
import clutch.clutchserver.building.dto.BuildingResponseDto;
import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.service.BuildingService;
import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.report.dto.ReportResponseDto;
import clutch.clutchserver.report.entity.Report;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final BuildingService buildingService;
    private final UserRepository userRepository;

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

    public ReportResponseDto getCompReport(String useremail) {
        User findUser = userRepository.findByEmail(useremail).get();
        Contract findContract = findUser.getContract();
        Report findReport = findContract.getReport();
        Building findBuilding = findContract.getBuilding();
        Address findAddress = findBuilding.getAddress();

        return ReportResponseDto.builder()
                .reportStatus(findReport.getStatus())
                .reportedAt(findReport.getCreatedAt())
                .buildingName(findBuilding.getBuildingName())
                .collateralDate(findBuilding.getCollateralDate())
                .address(findAddress.getAddress())
                .dong(findAddress.getDong())
                .ho(findAddress.getHo())
                .buildingType(findBuilding.getType())
                .has_lived(findContract.getHas_lived())
                .transport_report_date(findContract.getTransport_report_date())
                .confirmation_date(findContract.getConfirmation_date())
                .has_landlord_intervene(findContract.getHas_landlord_intervene())
                .has_applied_dividend(findContract.getHas_applied_dividend())
                .deposit(findContract.getDeposit())
                .build();
    }

//    public ResponseEntity<?> saveReport(Long contractId) {
//        //대항력 여부
//
//
//        //소액임차인 여부
//
//
//        //최우선 변제금
//    }
}


