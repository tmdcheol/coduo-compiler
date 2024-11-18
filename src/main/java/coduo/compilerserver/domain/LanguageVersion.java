package coduo.compilerserver.domain;

import coduo.compilerserver.global.response.exception.GeneralException;
import coduo.compilerserver.global.response.status.ErrorStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
public enum LanguageVersion {

    // javascript
    JAVASCRIPT_V14("javascript", "14"),
    JAVASCRIPT_V16("javascript", "16"),

    // cpp
    CPP_V14("cpp", "14"),
    CPP_V17("cpp", "17");


    private final String language;
    private final String version;

    public static LanguageVersion from(String language, String version) {
        for (LanguageVersion lv : LanguageVersion.values()) {
            if (lv.getLanguage().equalsIgnoreCase(language) &&
                lv.getVersion().equalsIgnoreCase(version)
            ) {
                return lv;
            }
        }

        log.error("지원하지 않는 언어 및 버전입니다. : {}, {}", language, version);
        throw new GeneralException(ErrorStatus.LANGUAGE_VERSION_NOT_FOUND);
    }
}
