package clutch.clutchserver.building.dto;

import clutch.clutchserver.global.common.enums.LogicType;
import clutch.clutchserver.global.common.enums.Type;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class BuildingInfoDto {

    //건물 id
    private Long buildingId;

    //건물명
    private String buildingName;

    //건물 시세
    private int price;

    //건물 유형
    private Type type;

    //근저당 설정 기준일
    private LocalDateTime collateralDate;

    //접수 유형(속성)
    private LogicType logicType;

    //근저당액
    private int collateralMoney;

    //평형 수
    private String area;
}
