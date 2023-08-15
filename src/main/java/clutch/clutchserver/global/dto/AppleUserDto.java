package clutch.clutchserver.global.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AppleUserDto {

    private String oauthId;
    private String name;
    private String email;
    private String phoneNumber;
}
