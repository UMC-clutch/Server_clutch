package clutch.clutchserver.contract.controller;


import clutch.clutchserver.contract.dto.ContractRequestDto;
import clutch.clutchserver.contract.service.ContractService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ContractController {

    private final ContractService contractService;

    @PostMapping("/contract")
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<String> uploadFile(@ModelAttribute ContractRequestDto requestDto) {
        try {
            // ContractService를 사용하여 Contract 데이터 저장
            contractService.saveContract(requestDto);
            return ResponseEntity.ok("Contract data saved successfully!");
        } catch (IOException e) {
            // 파일 업로드 실패 시 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }


}
