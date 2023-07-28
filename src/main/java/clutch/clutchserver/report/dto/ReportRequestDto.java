package clutch.clutchserver.report.dto;

import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.global.common.enums.LogicType;
import clutch.clutchserver.global.common.enums.ReportStatus;
import clutch.clutchserver.global.common.enums.Type;
import clutch.clutchserver.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
public class ReportRequestDto {

    private Long contractId;

    private Long userId;

    private Long buildingId;

    private ReportStatus reportStatus;

    private String buildingName;

    private Type buildingType;

    private LocalDateTime mortgageDate;

    private String address;

    private String dong;

    private String ho;

    private String area;

    private LogicType logicType;

}
