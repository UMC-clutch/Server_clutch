package clutch.clutchserver.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PhoneNumberRequestDto {

    @Schema(description = "전화번호", example = "010-1234-1234")
    private String phonenumber;
}
