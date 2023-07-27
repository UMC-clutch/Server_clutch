package clutch.clutchserver.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TokenInfo{

        @Schema(description="헤더",example="Bearer")
        private String grantType;

        @Schema(description="accesstoken",example="asdfsjfsdjfasdjfas....")
        private String accessToken;

        @Schema(description="accesstoken만료시간",example="30000")
        private Long accessTokenExpirationTime;
        @Schema(description="refreshtoken",example="asdfsjfsdjfasdjfas....")
        private String refreshToken;

        @Schema(description="refreshtoken만료시간",example="30000")
        private Long refreshTokenExpirationTime;

    }
}