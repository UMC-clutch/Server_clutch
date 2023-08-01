package clutch.clutchserver.global.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum Reason {
    NOT_COMFORT("서비스 이용이 불편함"),
    SOLVED("문제가 해결되어서 이용의사가 없어서"),
    SECURITY("개인정보 및 보안 우려"),
    NOT_TARGET("서비스 대상이 아님");

    private final String description;

    Reason(String description) {
        this.description = description;
    }

    @JsonCreator
    public static Reason from(String s) {
        return Reason.valueOf(s.toUpperCase());
    }
}
