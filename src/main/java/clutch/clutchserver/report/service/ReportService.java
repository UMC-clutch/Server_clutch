package clutch.clutchserver.report.service;

import clutch.clutchserver.address.entity.Address;
import clutch.clutchserver.building.dto.BuildingRequestDto;
import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.repository.BuildingRepository;
import clutch.clutchserver.building.service.BuildingService;
import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.global.DefaultAssert;
import clutch.clutchserver.report.dto.ReportResponseDto;
import clutch.clutchserver.report.entity.Report;
import clutch.clutchserver.report.repository.ReportRepository;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final BuildingRepository buildingRepository;
    private final BuildingService buildingService;

    //건물 정보 입력 반환
    public ReportResponseDto getReportResDto(BuildingRequestDto buildingRequestDto) {

        Optional<Building> optionalBuilding = buildingRepository.findByBuildingId(buildingRequestDto.getBuildingId());

//        // 건물 정보가 DB 에 저장되어 있지 않을 때
//        if (optionalBuilding.isEmpty()) {
//            Building building = buildingService.saveBuilding(buildingRequestDto);
//
//            Report report = Report.builder().build();
//
//            reportRepository.save(report);
//
//            ReportResponseDto reportResponseDto = ReportResponseDto.builder()
//                    .
//
//            ReportResponseDto reportResponseDto = ReportResponseDto.builder()
//                    .reportId(report.getId())
//                    .buildingName(buildingRequestDto.getBuildingName())
//                    .collateralDate(buildingRequestDto.getCollateralDate())
//                    .address(buildingRequestDto.getAddress())
//                    .dong(buildingRequestDto.getDong())
//                    .ho(buildingRequestDto.getHo())
//                    .buildingType(buildingRequestDto.getType())
//                    .build();
//
//            return reportResponseDto;
//        }

        // 건물 정보가 DB 에 저장되어 있을 때
        Report report = Report.builder().build();

        reportRepository.save(report);

        return ReportResponseDto.builder()
                .reportId(report.getId())
                .buildingName(buildingRequestDto.getBuildingName())
                .collateralDate(buildingRequestDto.getCollateralDate())
                .address(buildingRequestDto.getAddress())
                .dong(buildingRequestDto.getDong())
                .ho(buildingRequestDto.getHo())
                .buildingType(buildingRequestDto.getType())
                .build();

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



}
