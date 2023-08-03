package clutch.clutchserver.building.entity;

import clutch.clutchserver.address.entity.Address;
import clutch.clutchserver.global.common.enums.LogicType;
import clutch.clutchserver.global.common.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "building")
public class Building {

    //건물 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private Long buildingId;

    //건물 주소
    private String address;

    //동
    private String dong;

    //호
    private String ho;

    //건물명
    private String buildingName;

    //건물 시세
    private String price;

    //건물 유형
    @Enumerated(EnumType.STRING)
    private Type type;

    //근저당 설정 기준일
    private LocalDateTime collateralDate;

    //접수 유형(속성)
    @Enumerated(EnumType.STRING)
    private LogicType logicType;

    //근저당액
    private int collateralMoney;

    //평형 수
    private String area;

    @Builder
    public Building(Long buildingId, String address, String buildingName, String price, Type type, LocalDateTime collateralDate, LogicType logicType, int collateralMoney, String area) {
        this.buildingId = buildingId;
        this.address = address;
        this.buildingName = buildingName;
        this.price = price;
        this.type = type;
        this.collateralDate = collateralDate;
        this.logicType = logicType;
        this.collateralMoney = collateralMoney;
        this.area = area;
    }
}
