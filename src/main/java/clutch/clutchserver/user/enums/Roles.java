package clutch.clutchserver.user.enums;

import lombok.Getter;

@Getter
public enum Roles {
    ROLE_GUEST("게스트"), ROLE_USER("사용자");
    private String description;
    Roles(String description){
        this.description=description;
    }
}
