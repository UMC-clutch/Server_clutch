package clutch.clutchserver.contract.controller;


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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Contract", description = "계약과 관련된 API")
public class ContractController {

    private final ContractService contractService;
    private final S3Service s3Service;

    private final UserRepository userRepository;
    private final ContractRepository contractRepository;

    @Operation(description="계약 데이터와 이미지를 함께 업로드합니다. 데이터는 하나의 FormData로 전송되며 이미지를 제외한 계약 데이터는 JSON 객체로 바꿔서 전달해주세요.")
    @PostMapping(value="/contract/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<?> uploadFile(@PathVariable Long id, @RequestPart ContractRequestDto requestDto,
                                        @RequestPart("files") MultipartFile[] files) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String useremail = authentication.getName();
            Optional<User> user1 = userRepository.findByEmail(useremail);
            Long userId = user1.get().getId();
            User user = user1.get();
            System.out.println(requestDto.getHas_lived());



            // 파일 업로드 로직
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                String imageUrl = contractService.uploadImage(id, file, userId);
                imageUrls.add(imageUrl);
            }

            // 계약 데이터 저장 로직
            Contract contractEntity = contractRepository.findByUserId(userId);
            if (contractEntity != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "계약이 이미 존재합니다.");
            } else {
                // ContractService를 사용하여 Contract 데이터 저장
                contractService.saveContract(requestDto, id, user,imageUrls);
                // 이미지 URL들을 contractEntity에 추
                //contractRepository.save(contractEntity);
            }

            return ResponseEntity.ok("Contract data and images saved successfully!");
        } catch (IOException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .check(true)
                    .information(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null))
                    .build();

            return ResponseEntity.ok(apiResponse);
        }
    }

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

