package clutch.clutchserver.contract.service;

import clutch.clutchserver.contract.S3.S3Service;
import clutch.clutchserver.contract.dto.ContractRequestDto;
import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.contract.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final S3Service s3UploadService;



    public void saveContract(ContractRequestDto requestDto) throws IOException {
        // ContractRequestDto에서 필요한 데이터 추출
        Boolean hasLived = requestDto.getHas_lived();
        LocalDateTime transportReportDate = requestDto.getTransport_report_date();
        LocalDateTime confirmationDate = requestDto.getConfirmation_date();
        Boolean hasLandlordIntervene = requestDto.getHas_landlord_intervene();
        Boolean hasAppliedDividend = requestDto.getHas_applied_dividend();
        Integer deposit = requestDto.getDeposit();
        MultipartFile contractImg = requestDto.getContract_img();

        // S3에 파일 업로드 및 업로드된 파일의 URL 생성
        String s3FileUrl = s3UploadService.uploadFile(contractImg);

        // Contract 엔티티로 변환하여 데이터 저장
        Contract contract = Contract.builder()
                .has_lived(hasLived)
                .transport_report_date(transportReportDate)
                .confirmation_date(confirmationDate)
                .has_landlord_intervene(hasLandlordIntervene)
                .has_applied_dividend(hasAppliedDividend)
                .deposit(deposit)
                .contract_img(s3FileUrl)
                // 여기에 필요한 데이터들 추가
                .build();

        // Contract 엔티티를 ContractRepository를 사용하여 저장
        contractRepository.save(contract);
    }
}