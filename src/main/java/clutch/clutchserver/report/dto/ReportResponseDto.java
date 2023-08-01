package clutch.clutchserver.report.dto;

import clutch.clutchserver.global.common.enums.ReportStatus;
import clutch.clutchserver.global.common.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReportResponseDto {

    @Schema(description = "접수 상태")
    private ReportStatus reportStatus;

    @Schema(description = "신고 날짜")
    private LocalDateTime reportedAt;

    @Schema(description = "신고 접수 id")
    private Long reportId; //신고접수 id

    @Schema(description = "건물 이름", defaultValue = "OO아파트")
    private String buildingName; //건물 이름

    @Schema(description = "근저당 설정 기준일", defaultValue = "20230723")
    private LocalDateTime collateralDate; //근저당 설정 기준일

    @Schema(description = "지번 주소", defaultValue = "서울특별시 OO구 OO동 123-4")
    private String address; //지번 주소

    @Schema(description = "동")
    private String dong; //동

    @Schema(description = "호")
    private String ho; //호

    @Schema(description = "건물 유형", defaultValue = "APARTMENT")
    private Type buildingType; //건물 유형

    @Schema(description = "채권 개입 여부")
    private Boolean has_landlord_intervene; //채권 개입 여부

    @Schema(description = "배당 신청 여부")
    private Boolean has_applied_dividend; //배당 신청 여부

    @Schema(description = "보증금")
    private int deposit; //보증금

    @Schema(description = "실거주 여부")
    private Boolean has_lived; //실거주 여부

    @Schema(description = "전입 신고 날짜")
    private LocalDateTime transport_report_date; //전입 신고 날짜

    @Schema(description = "확정 일자")
    private LocalDateTime confirmation_date; //확정 일자
}
