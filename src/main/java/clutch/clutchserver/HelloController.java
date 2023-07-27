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
    @GetMapping
    @SecurityRequirement(name = "access-token")
    public ResponseEntity<?> findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();

        return userService.findUser(useremail);
    }

//    @GetMapping(value = "/api/v1/test")
//    @SecurityRequirement(name = "access-token")
//    public String test(HttpServletRequest request) throws Exception{
//        // 인증된 사용자의 정보 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        // 인증된 사용자의 정보를 기반으로 추가적인 작업 수행 가능
//        // (예: 사용자 정보를 활용한 비즈니스 로직 등)
//
//        return "Hello " + username + "! Success";
//    }
}
