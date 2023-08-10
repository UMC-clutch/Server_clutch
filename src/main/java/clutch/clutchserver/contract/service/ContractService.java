package clutch.clutchserver.contract.service;

import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.repository.BuildingRepository;
import clutch.clutchserver.contract.S3.S3Service;
import clutch.clutchserver.contract.dto.ContractRequestDto;
import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.contract.repository.ContractRepository;
import clutch.clutchserver.global.common.enums.ReportStatus;
import clutch.clutchserver.global.payload.ApiResponse;
import clutch.clutchserver.image.entity.Image;
import clutch.clutchserver.image.repository.ImageRepository;
import clutch.clutchserver.report.dto.ReportResponseDto;
import clutch.clutchserver.report.service.ReportService;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final S3Service s3UploadService;
    private final BuildingRepository buildingRepository;

    private final ImageRepository imageRepository;
    private final ReportService reportService;
    private final UserRepository userRepository;



    public ResponseEntity<?> saveContract(ContractRequestDto requestDto, Long buildingId, User user, List<String> imageList) throws IOException {
        // ContractRequestDto에서 필요한 데이터 추출
        Boolean reqHasLived = requestDto.getHasLived();
        LocalDate reqTransportReportDate = requestDto.getTransportReportDate();
        LocalDate reqConfirmationDate = requestDto.getConfirmationDate();
        Boolean reqHasLandlordIntervene = requestDto.getHasLandlordIntervene();
        Boolean reqHasAppliedDividend = requestDto.getHasAppliedDividend();
        BigInteger reqDeposit = requestDto.getDeposit();

        Optional<Building> buildingOptional = buildingRepository.findByBuildingId(buildingId);
        Building building = buildingOptional.orElseThrow(() -> new IllegalArgumentException("Invalid building ID"));
        System.out.println(building.getAddress());

        // Contract 엔티티로 변환하여 데이터 저장
        Contract contract = Contract.builder()
                .hasLived(reqHasLived)
                .transportReportDate(reqTransportReportDate)
                .confirmationDate(reqConfirmationDate)
                .hasLandlordIntervene(reqHasLandlordIntervene)
                .hasAppliedDividend(reqHasAppliedDividend)
                .deposit(reqDeposit)
                .building(building)
                // 여기에 필요한 데이터들 추가
                .user(user)
                .build();


        // Contract 엔티티를 ContractRepository를 사용하여 저장
        contractRepository.save(contract);
        ReportResponseDto response = createReportResponse(contract, building);
        System.out.println(imageList);

        //User userEntity =user.get();
        //if(userEntity.getContract()==null){
        //    user.get().updateContract(contract);
        //    userRepository.save(userEntity);
        //}
        reportService.saveReport(user.getId(),contract,building);

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    public ReportResponseDto createReportResponse(Contract contract, Building building) throws IOException {
        // ReportResponseDto 생성
        ReportResponseDto reportResponseDto = ReportResponseDto.builder()
                .reportStatus(ReportStatus.DECISIONING) // 예시로 상태를 PENDING으로 설정
                .reportedAt(LocalDateTime.now()) // 현재 시간으로 설정
                .reportId(contract.getId()) // Contract의 id를 신고 접수 id로 설정
                .buildingName(building != null ? building.getBuildingName() : null)
                .collateralDate(building != null ? building.getCollateralDate() : null)
                .address(building != null ? String.valueOf(building.getAddress()) : null)
                .dong(building != null ? building.getDong() : null)
                .ho(building != null ? building.getHo() : null)
                .buildingType(building != null ? building.getType() : null)
                .hasLandlordIntervene(contract.getHasLandlordIntervene())
                .hasAppliedDividend(contract.getHasAppliedDividend())
                .deposit(contract.getDeposit())
                .hasLived(contract.getHasLived())
                .transportReportDate(contract.getTransportReportDate())
                .confirmationDate(contract.getConfirmationDate())
                .build();

        return reportResponseDto;
    }

    public String uploadImage(Long buildingId, MultipartFile file, Long userId) throws IOException {
        // S3에 이미지 파일 업로드 및 업로드된 파일의 URL 생성
        String imageUrl = s3UploadService.uploadFile(file);

        // Building 엔티티 가져오기
        Building building = buildingRepository.findByBuildingId(buildingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid building ID"));

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid building ID"));

        Image image = Image.builder()
                .url(imageUrl)
                .building(building)
                .user(user)
                .build();

        // Image 엔티티를 저장
        imageRepository.save(image);

        // 업로드된 이미지 URL 반환
        return imageUrl;
    }
}