package clutch.clutchserver.global.common.enums;

import lombok.Getter;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_GUEST("게스트"), ROLE_USER("사용자");
    private String description;
    Role(String description){
        this.description=description;
    }
}