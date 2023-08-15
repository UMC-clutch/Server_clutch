package clutch.clutchserver.contract.entity;

import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.global.common.BaseDateEntity;
import clutch.clutchserver.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "contract")
public class Contract extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;

    private Boolean hasLived;

    private LocalDate transportReportDate;
    private LocalDate confirmationDate;
    private Boolean hasLandlordIntervene;
    private Boolean hasAppliedDividend;
    private BigInteger deposit;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "building_id")
    private Building building;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;




}

