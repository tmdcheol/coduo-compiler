package coduo.compilerserver.domain.executor.process.language;

import static coduo.compilerserver.global.utils.Constants.*;

import coduo.compilerserver.domain.LanguageVersion;
import coduo.compilerserver.domain.Project;
import coduo.compilerserver.domain.executionresult.ExecutionResult;
import coduo.compilerserver.domain.executor.process.AbstractProcessLanguageExecutor;
import coduo.compilerserver.global.response.exception.GeneralException;
import coduo.compilerserver.global.response.status.ErrorStatus;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JavascriptProcessExecutor extends AbstractProcessLanguageExecutor {

    @Override
    public ExecutionResult execute(Project project, String executionId) {
        Path workingDirectory = createDirectory(project.languageVersion());
        log.debug("working directory:{}", workingDirectory);
        saveFiles(project, workingDirectory);

        String entryPointPath = workingDirectory.resolve(project.entryPoint()).toString();
        if (!entryPointPath.endsWith(JAVASCRIPT_EXTENSION)) {
            throw new GeneralException(ErrorStatus.FILE_EXTENSION_ERROR, entryPointPath);
        }

        String command = String.format(JAVASCRIPT_COMMAND, entryPointPath);
        return runProcess(command, workingDirectory, executionId);
    }

    @Override
    public boolean supports(LanguageVersion languageVersion) {
        return languageVersion.equals(LanguageVersion.JAVASCRIPT_V14) ||
            languageVersion.equals(LanguageVersion.JAVASCRIPT_V16);
    }
}
