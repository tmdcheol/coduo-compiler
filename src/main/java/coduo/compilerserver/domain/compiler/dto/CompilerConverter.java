package coduo.compilerserver.domain.compiler.dto;

import coduo.compilerserver.domain.Project;
import coduo.compilerserver.domain.LanguageVersion;
import coduo.compilerserver.domain.Project.SourceFile;
import coduo.compilerserver.domain.compiler.dto.CompilerRequest.FileInfo;
import coduo.compilerserver.domain.executionresult.ExecutionResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CompilerConverter {

    public static Project to(CompilerRequest.CompileRequest request) {
        CompilerRequest.Project projectRequest = request.project();

        LanguageVersion languageVersion = LanguageVersion.from(
            request.language(),
            request.version()
        );

        List<Project.SourceFile> sourceFiles = projectRequest.fileInfos()
            .stream()
            .map(fileInfo -> new Project.SourceFile(fileInfo.path(), fileInfo.content()))
            .toList();

        return new Project(
            languageVersion,
            sourceFiles,
            projectRequest.entryPoint()
        );
    }

    public static CompilerResponse.ExecutionResult to(ExecutionResult executionResult) {
        return new CompilerResponse.ExecutionResult(
            executionResult.getExitCode(),
            executionResult.getOutput(),
            executionResult.getErrorMessage()
        );
    }
}