package coduo.compilerserver.global.response.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "세션이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // execution result
    EXECUTION_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "EXECUTIONRESULT400", "실행결과가 조회되지 않습니다."),

    // language version
    LANGUAGE_VERSION_NOT_FOUND(HttpStatus.NOT_FOUND, "LANGUAGEVERSION400", "지원하지 않는 언어 및 버전입니다."),

    // process execution
    CREATE_DIRECTORY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PROCESS500", "디렉토리 생성 중 에러가 발생하였습니다"),
    SAVE_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PROCESS501", "파일 저장 중 에러가 발생하였습니다"),
    PROCESS_EXECUTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PROCESS502", "새로운 프로세스 생성 및 실행 중 에러가 발생하였습니다."),
    FILE_EXTENSION_ERROR(HttpStatus.BAD_REQUEST, "PROCESS503", "파일 확장자 형식이 맞지 않습니다."),
    PROCESS_TIMEOUT_ERROR(HttpStatus.GATEWAY_TIMEOUT, "PROCESS504", "프로세스 실행 시간이 초과되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}