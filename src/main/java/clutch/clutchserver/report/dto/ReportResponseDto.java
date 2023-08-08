package clutch.clutchserver.report.dto;

import clutch.clutchserver.global.common.enums.ReportStatus;
import clutch.clutchserver.global.common.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReportResponseDto {

    @Schema(description = "접수 상태", example = "COMP")
    private ReportStatus reportStatus;

    @Schema(description = "신고 날짜", example = "2023-08-08T07:30:51.181Z")
    private LocalDateTime reportedAt;

    @Schema(description = "신고 접수 ID", example = "1")
    private Long reportId;

    @Schema(description = "건물 이름", example = "서서울삼성")
    private String buildingName;

    @Schema(description = "근저당 설정 기준일", example = "2023-08-01T07:30:51.181Z")
    private LocalDate collateralDate;

    @Schema(description = "지번 주소", example = "서울특별시 강남구 대치동 985")
    private String address;

    @Schema(description = "동", example = "101")
    private String dong;

    @Schema(description = "호", example = "101")
    private String ho;

    @Schema(description = "건물 유형", defaultValue = "APARTMENT")
    private Type buildingType;

    @Schema(description = "채권 개입 여부", example = "false")
    private Boolean has_landlord_intervene;

    @Schema(description = "배당 신청 여부", example = "true")
    private Boolean has_applied_dividend;

    @Schema(description = "보증금", example = "2000000000")
    private BigInteger deposit;

    @Schema(description = "실거주 여부", example = "true")
    private Boolean has_lived;

    @Schema(description = "전입 신고 날짜", example = "2023-08-01T07:30:51.181Z")
    private LocalDate transport_report_date;

    @Schema(description = "확정 일자", example = "2023-08-01T07:30:51.181Z")
    private LocalDate confirmation_date;
}
