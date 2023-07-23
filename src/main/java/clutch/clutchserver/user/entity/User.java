package clutch.clutchserver.user.entity;

import clutch.clutchserver.global.common.BaseDateEntity;
import clutch.clutchserver.global.common.enums.AuthProvider;
import clutch.clutchserver.global.common.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String email;

    private String name;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Nullable
    private String phonenumber;


}