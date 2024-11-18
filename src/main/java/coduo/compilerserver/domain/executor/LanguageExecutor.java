package coduo.compilerserver.domain.executor;

import coduo.compilerserver.domain.Project;
import coduo.compilerserver.domain.executionresult.ExecutionResult;
import coduo.compilerserver.domain.LanguageVersion;

public interface LanguageExecutor {

    ExecutionResult execute(Project project, String executionId);

    boolean supports(LanguageVersion languageVersion);
}