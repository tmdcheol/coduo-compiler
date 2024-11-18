package coduo.compilerserver.domain.compiler.dto;

public abstract class CompilerResponse {

    public record ExecutionResult(
        Integer exitCode,
        String output,
        String errorMessage
    ) {

    }

}
