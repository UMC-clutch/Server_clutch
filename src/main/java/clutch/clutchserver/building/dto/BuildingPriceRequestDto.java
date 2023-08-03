package clutch.clutchserver.building.dto;

import clutch.clutchserver.global.common.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "BuildingPriceRequest")
public class BuildingPriceRequestDto {

    @Schema(description = "건물명", example = "서서울삼성")
    private String buildingName;

    @Schema(description = "지번 주소", example = "서울특별시 강남구 대치동 985")
    private String address;

    @Schema(description = "동", example = "101")
    private String dong;

    @Schema(description = "호", example = "101")
    private String ho;

    @Schema(description = "건물 유형", example = "APARTMENT", allowableValues = {"APARTMENT", "MULTI" ,"VILLA"})
    private Type type;

    @Schema(description = "평형 수", example = "142A")
    private String area;

}
