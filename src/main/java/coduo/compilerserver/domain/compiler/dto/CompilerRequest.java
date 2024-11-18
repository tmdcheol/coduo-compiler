package coduo.compilerserver.domain.compiler.dto;

import java.util.List;

public abstract class CompilerRequest {

    public record CompileRequest(
            String language,
            String version,
            Project project
    ) {

    }

    public record Project(
            String entryPoint,
            List<FileInfo> fileInfos
    ) {

    }

    public record FileInfo(
            String path,
            String content
    ) {

    }

}
