package clutch.clutchserver;

import clutch.clutchserver.user.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class HelloController {
    private final UserService userService;


    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, World!";
    }

    //유저 조회
    @GetMapping("/api/users")
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<?> findUser() {
        // 현재 토큰을 사용중인 유저 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();    // 해당 유저의 email 조회(getName()은 이메일 조회 의미)

        return userService.findUser(useremail);
    }
}
