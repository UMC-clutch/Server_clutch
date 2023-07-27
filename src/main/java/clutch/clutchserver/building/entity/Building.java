package clutch.clutchserver.building.entity;

import clutch.clutchserver.address.entity.Address;
import clutch.clutchserver.calculate.entity.Calculate;
import clutch.clutchserver.contract.entity.Contract;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "building")
public class Building {

    //건물 id
    @Id
    @GeneratedValue
    @Column(name = "building_id")
    private Long buildingId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    //건물명
    private String name;

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
