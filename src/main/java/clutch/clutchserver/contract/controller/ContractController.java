package clutch.clutchserver.contract.controller;


import clutch.clutchserver.building.dto.BuildingPriceResponseDto;
import clutch.clutchserver.contract.S3.S3Service;
import clutch.clutchserver.contract.dto.ContractRequestDto;
import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.contract.repository.ContractRepository;
import clutch.clutchserver.contract.service.ContractService;
import clutch.clutchserver.global.payload.ApiResponse;
import clutch.clutchserver.image.entity.Image;
import clutch.clutchserver.report.dto.ReportResponseDto;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Contract", description = "계약과 관련된 API")
public class ContractController {

    private final ContractService contractService;
    private final S3Service s3Service;

    private final UserRepository userRepository;
    private final ContractRepository contractRepository;

    @Operation(summary = "계약 생성", description = "계약에 대한 정보를 입력합니다.")
    @PostMapping("/contract/{id}")
    @SecurityRequirement(name = "access-token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReportResponseDto.class)))
    public ResponseEntity<?> uploadFile(@PathVariable Long id, @RequestBody ContractRequestDto requestDto) {
        try {
            System.out.println(requestDto.toString());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String useremail = authentication.getName();

            Optional<User> user1 =userRepository.findByEmail(useremail);
            Long userId = user1.get().getId();
            User user = user1.get();

            Contract contractEntity = null;
            contractEntity = contractRepository.findByUserId(userId);


            if (contractEntity != null) {
                // 이미 계약이 있는 경우에 대한 로직 처리
                // ...
                throw new ResponseStatusException(HttpStatus.CONFLICT, "계약이 이미 존재합니다.");
            } else {
                // 계약이 없는 경우에 대한 로직 처리
                // ...
                return contractService.saveContract(requestDto, id, user);
            }

            // ContractService를 사용하여 Contract 데이터 저장

        } catch (IOException e) {
            // 파일 업로드 실패 시 예외 처리
            ApiResponse apiResponse = ApiResponse.builder()
                    .check(true)
                    .information(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null))
                    .build();

            return ResponseEntity.ok(apiResponse);
        }
    }

    @Operation(summary = "계약서 이미지", description = "계약서 이미지들을 넣습니다.")
    @PostMapping("/contract/image/{buildingId}")
    public ResponseEntity<String> uploadImage(@PathVariable Long buildingId, @RequestParam("file") MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String useremail = authentication.getName();

            Optional<User> user =userRepository.findByEmail(useremail);
            Long userId = user.get().getId();
            String imageUrl = contractService.uploadImage(buildingId, file,userId);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

}
