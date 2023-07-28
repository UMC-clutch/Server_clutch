package clutch.clutchserver.report.dto;

import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.global.common.enums.LogicType;
import clutch.clutchserver.global.common.enums.ReportStatus;
import clutch.clutchserver.global.common.enums.Type;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
public class ReportResponseDto {

    private Long reportId;

    private ReportStatus reportStatus; //신고 접수 상태

    private int buildingPrice; //건물 시세

    private int repayment; // 최우선 변제금
    private boolean hasLowIncome; // 소액임차인 여부
    private boolean hasSubmittedContract; // 계약서 제출 여부
    private boolean hasResistance; // 대항력 여부
    private boolean hasRepayment; // 최우선 변제금 가능여부

}
