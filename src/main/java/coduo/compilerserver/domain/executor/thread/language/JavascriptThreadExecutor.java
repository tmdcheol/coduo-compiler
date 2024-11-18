package coduo.compilerserver.domain.executor.thread.language;

import coduo.compilerserver.domain.LanguageVersion;
import coduo.compilerserver.domain.Project;
import coduo.compilerserver.domain.executor.ExecutionResult;
import coduo.compilerserver.domain.executor.LanguageExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavascriptThreadExecutor implements LanguageExecutor {

    @Override
    public ExecutionResult execute(Project project, String executionId) {
        return null;
    }

    @Override
    public boolean supports(LanguageVersion languageVersion) {
        return false;
    }
}
