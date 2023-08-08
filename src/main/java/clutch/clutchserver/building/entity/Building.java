package clutch.clutchserver.building.entity;

import clutch.clutchserver.global.common.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "building")
public class Building {

    //건물 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private Long buildingId;

    //주소
    private String address;

    //동
    private String dong;

    //호
    private String ho;

    //건물명
    private String buildingName;

    //건물 시세
    private BigInteger price;

    //건물 유형
    @Enumerated(EnumType.STRING)
    private Type type;

    //근저당 설정 기준일
    private LocalDate collateralDate;

    //근저당액
    private BigInteger collateralMoney;

    //평형 수
    private String area;
}