package clutch.clutchserver.report.service;

import clutch.clutchserver.address.entity.Address;
import clutch.clutchserver.building.dto.BuildingRequestDto;
import clutch.clutchserver.building.dto.BuildingResponseDto;
import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.service.BuildingService;
import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.global.common.CalculateDeposit;
import clutch.clutchserver.global.common.enums.ReportStatus;
import clutch.clutchserver.image.entity.Image;
import clutch.clutchserver.image.repository.ImageRepository;
import clutch.clutchserver.report.dto.ReportResponseDto;
import clutch.clutchserver.report.entity.Report;
import clutch.clutchserver.report.repository.ReportRepository;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final BuildingService buildingService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private final CalculateDeposit calculateDeposit;
    private final ReportRepository reportRepository;

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

    public void saveReport(Long userId,Contract contract,Building building,Address address) {
        Boolean resist = false;
        Boolean has_image = false;
        Boolean has_low_income =false;
        int repayment = 0;
        ReportStatus report_status = ReportStatus.DECISIONING;
        //대항력 여부
        if(contract.getHas_applied_dividend() && contract.getHas_lived() && !contract.getHas_landlord_intervene()){
            if((contract.getTransport_report_date()!=null)&&(contract.getConfirmation_date()!=null)){
                resist = true;
            }else{
                resist =false;
            }
        }else {
            resist =false;
        }
        //소액임차인 여부
        int calculateResult = calculateDeposit.calculate(contract.getDeposit(),address.getAddress(),contract.getConfirmation_date());
        if(calculateResult!=0){
            has_low_income =true;
        }


        //user에 image존재?
        Optional<Image> image = imageRepository.findById(userId);
        Image imageEntity = image.get();
        if(imageEntity!= null){
            has_image = true;
        }else{
            has_image = false;
        }
        //최우선 변제금
        if(resist && has_image && has_low_income){
            repayment = calculateResult;
        }





        //STATUS 부여해주기
        if(resist && has_image && has_low_income&&(repayment!=0)){
            report_status = ReportStatus.COMP;
        }else{
            report_status = ReportStatus.DECISIONING;
        }

        //report객체 생성 후 repository에 저장하기
        Report report= Report.builder()
                .hasLowIncome(has_low_income)
                .hasRepayment(has_low_income)
                .hasResistance(resist)
                .repayment(repayment)
                .contract(contract)
                .status(report_status)
                .hasSubmittedContract(has_image)
                .build();

        reportRepository.save(report);
    }


}


