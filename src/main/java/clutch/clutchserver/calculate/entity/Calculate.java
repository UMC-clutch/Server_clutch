package clutch.clutchserver.calculate.entity;

import clutch.clutchserver.global.common.BaseDateEntity;
import clutch.clutchserver.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "calculate")
public class Calculate extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "building_id")
//    private Building building;

    private int deposit; // 보증금
    private boolean hasDanger; // 위험여부


}
