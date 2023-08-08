package clutch.clutchserver.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class FindUserResponseDto {

    @Schema(description="유저 ID",example= "1")
    private Long id;

    @Schema(description="이름",example= "홍길동")
    private String name;

    @Schema(description="이메일",example= "hong@gmail.com")
    private String email;

    @Schema(description="전화번호",example= "010-1234-1234")
    private String phonenumber;

    @Builder
    public FindUserResponseDto(Long id, String name, String email, String phonenumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
    }
}