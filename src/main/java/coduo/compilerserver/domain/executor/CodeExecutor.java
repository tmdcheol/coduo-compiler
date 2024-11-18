package coduo.compilerserver.domain.executor;

import coduo.compilerserver.domain.executionresult.ExecutionResult;
import coduo.compilerserver.domain.Project;

public interface CodeExecutor {

    ExecutionResult execute(Project project, String executionId);

}
