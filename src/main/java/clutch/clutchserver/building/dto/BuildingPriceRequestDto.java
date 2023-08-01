package clutch.clutchserver.building.dto;

import clutch.clutchserver.global.common.enums.Type;
import lombok.Getter;

@Getter
public class BuildingPriceRequestDto {

    //건물명
    private String buildingName;

    //지번 주소
    private String address;

    //동
    private String dong;

    //호
    private String ho;

    // 건물 유형
    private Type type;

    // 평형 수
    private String area;

}
