package coduo.compilerserver.global.domain.compiler.dto;

public abstract class CompilerRequest {

    public record Code(
        String language,
        String code
    ) {

    }
}
