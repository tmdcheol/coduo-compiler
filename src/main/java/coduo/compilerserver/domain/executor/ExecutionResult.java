package coduo.compilerserver.domain.executor;

import static coduo.compilerserver.global.utils.Constants.SUCCESS_EXIT_CODE;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ExecutionResult {

    private final String executionId;
    private final Integer exitCode;
    private final String output;
    private final String errorMessage;

    @Builder
    private ExecutionResult(
            String executionId,
            Integer exitCode,
            String output,
            String errorMessage
    ) {
        this.executionId = executionId;
        this.exitCode = exitCode;
        this.output = output;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return exitCode.equals(SUCCESS_EXIT_CODE);
    }

}
