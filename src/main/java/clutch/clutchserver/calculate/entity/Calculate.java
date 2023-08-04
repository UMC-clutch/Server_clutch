package clutch.clutchserver.calculate.entity;

import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.global.common.BaseDateEntity;
import clutch.clutchserver.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Entity
@Table(name = "calculate")
@Getter
@RequiredArgsConstructor
public class Calculate extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    private BigInteger deposit; // 보증금
    private boolean hasDanger; // 위험여부

    @Builder
    public Calculate(Long id, User user, Building building, BigInteger deposit, boolean hasDanger) {
        this.id = id;
        this.user = user;
        this.building = building;
        this.deposit = deposit;
        this.hasDanger = hasDanger;
    }

}