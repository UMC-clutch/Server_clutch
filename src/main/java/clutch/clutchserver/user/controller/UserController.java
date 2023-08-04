package clutch.clutchserver.user.controller;

import clutch.clutchserver.global.common.enums.Reason;
import clutch.clutchserver.user.dto.FindUserResponseDto;
import clutch.clutchserver.user.dto.PhoneNumberRequestDto;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import clutch.clutchserver.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@Tag(name = "User", description = "유저와관련된 API")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    // 회원탈퇴
    @DeleteMapping("/v1/users")
    @Operation(summary = "회원 탈퇴하기")
    @ApiResponse(responseCode = "200", description = "Successfully deleted user")
    @SecurityRequirement(name = "access-token")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "not_comfort/solved/security/not_target 중 하나")
    public ResponseEntity<?> deleteUser(@RequestBody String request) {
        // 현재 토큰을 사용중인 유저 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        // String을 enum형으로 변환
        Reason reason = Reason.from(request);

        userService.userDelete(useremail, reason);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //유저 조회
    @GetMapping("/v1/users")
    @SecurityRequirement(name = "access-token")
    @Operation(summary = "유저 조회", description = "현재 유저의 정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FindUserResponseDto.class)))
    public ResponseEntity<?> findUser() {
        // 현재 토큰을 사용중인 유저 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        return userService.findUser(useremail);
    }

    @PostMapping("/v1/phone-number")
    @SecurityRequirement(name="access-token")
    @Operation(summary = "전화번호 추가", description = "유저의 전화번호를 저장합니다")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    public ResponseEntity<String> addPhoneNumber(@RequestBody PhoneNumberRequestDto request) {
        // 요청에서 전화번호를 가져옵니다.
        String phoneNumber = request.getPhonenumber();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();

        User user = userRepository.findByEmail(useremail).orElse(null);

        // 유효성 검사를 진행 (생략)

        // 사용자 정보를 업데이트합니다.
        try {
           user.updatePhoneNum(phoneNumber);
            return ResponseEntity.ok("전화번호가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            // 예외 처리 (생략)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("전화번호 업데이트 중 오류가 발생하였습니다.");
        }
    }
}
