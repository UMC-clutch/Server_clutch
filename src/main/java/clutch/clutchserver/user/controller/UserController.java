package clutch.clutchserver.user.controller;

import clutch.clutchserver.global.common.enums.Reason;
import clutch.clutchserver.user.dto.FindUserResponseDto;
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
}
