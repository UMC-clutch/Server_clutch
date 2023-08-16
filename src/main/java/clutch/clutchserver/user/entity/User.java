package clutch.clutchserver.user.entity;

import clutch.clutchserver.contract.entity.Contract;
import clutch.clutchserver.global.common.BaseDateEntity;
import clutch.clutchserver.global.common.enums.AuthProvider;
import clutch.clutchserver.global.common.enums.Role;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    @Column(name = "oauth2_id", length = 1000)
    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Nullable
    @Column(name = "phone_number")
    private String phoneNumber;

    public void updatePhoneNum(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
