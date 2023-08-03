package clutch.clutchserver.building.dto;

import clutch.clutchserver.global.common.enums.LogicType;
import clutch.clutchserver.global.common.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class BuildingPriceResponseDto {

    @Schema(description="빌딩 ID",example= "1")
    private Long buildingId;

    @Schema(description="건물 시세",example= "10000000")
    private BigInteger price;

    @Schema(description="건물명",example= "커피빈 상수역점")
    private String buildingName;

    @Schema(description="지번 주소",example= "서울 마포구 와우산로10길 3")
    private String address;

    @Schema(description="동",example= "1동")
    private String dong;

    @Schema(description="호",example= "101호")
    private String ho;

    @Schema(description="접수 유형",example= "CALCULATE")
    private LogicType logicType;

    @Schema(description="건물 유형",example= "APARTMENT")
    private Type type;

    @Schema(description="평형",example= "33")
    private String area;

    @Builder
    public BuildingPriceResponseDto(Long buildingId, BigInteger price, String buildingName, String address, String dong, String ho, LogicType logicType, Type type, String area) {
        this.buildingId = buildingId;
        this.price = price;
        this.buildingName = buildingName;
        this.address = address;
        this.dong = dong;
        this.ho = ho;
        this.logicType = logicType;
        this.type = type;
        this.area = area;
    }
}