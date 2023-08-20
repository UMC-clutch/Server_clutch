package clutch.clutchserver.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteUserRequestDto {

    @Schema(description = "탈퇴 사유 (not_comfort/solved/security/not_target)", example = "solved")
    private String reason;
}
