package clutch.clutchserver.report.service;

import clutch.clutchserver.address.entity.Address;
import clutch.clutchserver.address.repository.AddressRepository;
import clutch.clutchserver.building.dto.BuildingRequestDto;
import clutch.clutchserver.building.dto.BuildingResponseDto;
import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.repository.BuildingRepository;
import clutch.clutchserver.building.service.BuildingService;
import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.global.common.enums.Type;
import clutch.clutchserver.report.dto.ReportResponseDto;
import clutch.clutchserver.report.entity.Report;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final BuildingService buildingService;
    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;
    private final AddressRepository addressRepository;

    //건물 정보 입력 반환
    public BuildingResponseDto getBuildingResDto(BuildingRequestDto buildingRequestDto){

        // db 에서 건물 찾아보기.
        // 건물 테이블에 addressId 가 저장되기 때문에 addressId 를 이용해서 db 에서 건물 비교 후 가져올 수 있음.
        Optional<Building> building = buildingRepository.findAllByBuildingNameAndAddressAndLogicTypeAndArea(
                buildingRequestDto.getBuildingName(),
                buildingRequestDto.getAddress(),
                buildingRequestDto.getLogicType(),
                buildingRequestDto.getArea()
        );

        //만약 db에 건물이 없다면,
        if(building.isEmpty()){

            Building newBuilding = Building.builder()
                    .buildingName(buildingRequestDto.getBuildingName())
                    .collateralDate(buildingRequestDto.getCollateralDate())
                    .address(buildingRequestDto.getAddress())
                    .type(buildingRequestDto.getType())
                    .build();

            // 새로운 건물 db 에 저장.
            buildingService.saveBuilding(buildingRequestDto);

            BuildingResponseDto buildingResponseDto = BuildingResponseDto.builder()
                    .buildingId(newBuilding.getBuildingId())
                    .buildingName(newBuilding.getBuildingName())
                    .type(newBuilding.getType())
                    .build();

            return buildingResponseDto;

        }
        //만약 건물이 db에 있다면
        else{

            BuildingResponseDto buildingResponseDto = BuildingResponseDto.builder()
                    .buildingId(building.get().getBuildingId())
                    .buildingName(building.get().getBuildingName())
                    .address(building.get().getAddress())
                    .type(building.get().getType())
                    .build();

            return buildingResponseDto;
        }
    }



    // 완성된 신고 접수 가져오기
    public ReportResponseDto getCompReport(String useremail) {
        User findUser = userRepository.findByEmail(useremail).get();
        Contract findContract = findUser.getContract();
        Report findReport = findContract.getReport();
        Building findBuilding = findContract.getBuilding();

        return ReportResponseDto.builder()
                .reportStatus(findReport.getStatus())
                .reportedAt(findReport.getCreatedAt())
                .buildingName(findBuilding.getBuildingName())
                .collateralDate(findBuilding.getCollateralDate())
                .address(findBuilding.getAddress())
                .dong(findBuilding.getDong())
                .ho(findBuilding.getHo())
                .buildingType(findBuilding.getType())
                .has_lived(findContract.getHas_lived())
                .transport_report_date(findContract.getTransport_report_date())
                .confirmation_date(findContract.getConfirmation_date())
                .has_landlord_intervene(findContract.getHas_landlord_intervene())
                .has_applied_dividend(findContract.getHas_applied_dividend())
                .deposit(findContract.getDeposit())
                .build();
    }
}


