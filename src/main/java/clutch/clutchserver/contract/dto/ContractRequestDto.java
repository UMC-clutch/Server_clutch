package clutch.clutchserver.contract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Builder
@Getter
public class ContractRequestDto {

    @Schema(description = "거주 여부")
    private Boolean has_lived;

    @Schema(description = "운송 보고 날짜")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime transport_report_date;

    @Schema(description = "확인 날짜")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime confirmation_date;

    @Schema(description = "집주인 개입 여부")
    private Boolean has_landlord_intervene;

    @Schema(description = "배당 신청 여부")
    private Boolean has_applied_dividend;

    @Schema(description = "보증금")
    private Integer deposit;

    @Schema(description = "계약 이미지")
    private MultipartFile contract_img;
}
