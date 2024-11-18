package coduo.compilerserver.domain.executor.process.language;

import static coduo.compilerserver.global.utils.Constants.*;

import coduo.compilerserver.domain.LanguageVersion;
import coduo.compilerserver.domain.Project;
import coduo.compilerserver.domain.executionresult.ExecutionResult;
import coduo.compilerserver.domain.executor.process.AbstractProcessLanguageExecutor;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CppProcessExecutor extends AbstractProcessLanguageExecutor {

    @Override
    public ExecutionResult execute(Project project, String executionId) {
        Path workingDirectory = createDirectory(project.languageVersion());
        saveFiles(project, workingDirectory);

        // compile
        ExecutionResult compileResult = compile(project, workingDirectory, executionId);
        if (!compileResult.isSuccess()) {
            return compileResult;
        }

        // chmod
        ExecutionResult chmodResult = chmod(workingDirectory, executionId);
        if (!chmodResult.isSuccess()) {
            return chmodResult;
        }

        // run executeFile
        return run(workingDirectory, executionId);
    }

    @Override
    public boolean supports(LanguageVersion languageVersion) {
        return languageVersion.equals(LanguageVersion.CPP_V14) ||
            languageVersion.equals(LanguageVersion.CPP_V17);
    }

    private ExecutionResult compile(Project project, Path workingDirectory, String executionId) {
        String compileCommand = getCompileCommand(project, workingDirectory);

        return runProcess(compileCommand, workingDirectory, executionId);
    }

    private String getCompileCommand(Project project, Path workingDirectory) {
        String compileFilePaths = getCompileFilePaths(project, workingDirectory);
        String executionFilePath = getExecutionFilePath(workingDirectory);

        return String.format(
            CPP_COMPILE_COMMAND,
            compileFilePaths,
            executionFilePath
        );
    }

    private String getCompileFilePaths(Project project, Path workingDirectory) {
        StringBuilder filePaths = new StringBuilder();
        for (Project.SourceFile sourceFile : project.files()) {
            if (sourceFile.path().endsWith(CPP_EXTENSION)) {
                filePaths
                    .append(workingDirectory.resolve(sourceFile.path()))
                    .append(SPACE);
            }
        }

        return filePaths.toString().trim();
    }

    private ExecutionResult chmod(Path workingDirectory, String executionId) {
        String executionFilePath = getExecutionFilePath(workingDirectory);
        String chmodCommand = String.format(CPP_CHMOD_COMMAND, executionFilePath);
        return runProcess(chmodCommand, workingDirectory, executionId);
    }

    private ExecutionResult run(Path workingDirectory, String executionId) {
        String runCommand = String.format(CPP_RUN_COMMAND, getExecutionFilePath(workingDirectory));
        return runProcess(runCommand, workingDirectory, executionId);
    }


    private String getExecutionFilePath(Path workingDirectory) {
        return workingDirectory
            .resolve(CPP_EXECUTION_FILE_NAME)
            .toString();
    }
}
