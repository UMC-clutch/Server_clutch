package clutch.clutchserver.contract.controller;


import clutch.clutchserver.contract.S3.S3Service;
import clutch.clutchserver.contract.dto.ContractRequestDto;
import clutch.clutchserver.contract.service.ContractService;
import clutch.clutchserver.report.dto.ReportResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ContractController {

    private final ContractService contractService;
    private final S3Service s3Service;

    @PostMapping("/contract/{id}")
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<ReportResponseDto> uploadFile(@PathVariable Long id, @RequestBody ContractRequestDto requestDto) {
        try {
            System.out.println(requestDto.toString());
            // ContractService를 사용하여 Contract 데이터 저장
            ReportResponseDto reportResponseDto = contractService.saveContract(requestDto, id);
            return ResponseEntity.ok(reportResponseDto);
        } catch (IOException e) {
            // 파일 업로드 실패 시 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/contract/image/{buildingId}")
    public ResponseEntity<String> uploadImage(@PathVariable Long buildingId, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = contractService.uploadImage(buildingId, file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

}
