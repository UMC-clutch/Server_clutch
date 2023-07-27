package clutch.clutchserver.user.controller;

import clutch.clutchserver.global.common.enums.Reason;
import clutch.clutchserver.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // 회원탈퇴
    @DeleteMapping("/api/v1/users")
    @Operation(summary = "회원 탈퇴하기")
    @SecurityRequirement(name = "access-token")
    public void deleteUser(Reason reason) {
        // 현재 토큰을 사용중인 유저 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        userService.userDelete(useremail, reason);
    }
}
