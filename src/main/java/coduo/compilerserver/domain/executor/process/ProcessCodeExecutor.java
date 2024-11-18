package coduo.compilerserver.domain.executor.process;

import coduo.compilerserver.domain.Project;
import coduo.compilerserver.domain.executor.ExecutionResult;
import coduo.compilerserver.domain.executor.CodeExecutor;
import coduo.compilerserver.global.response.exception.GeneralException;
import coduo.compilerserver.global.response.status.ErrorStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessCodeExecutor implements CodeExecutor {

    private final List<AbstractProcessLanguageExecutor> executors;

    @Override
    public ExecutionResult execute(Project project, String executionId) {
        for (AbstractProcessLanguageExecutor executor : executors) {
            if (executor.supports(project.languageVersion())) {
                return executor.execute(project, executionId);
            }
        }

        throw new GeneralException(ErrorStatus.LANGUAGE_VERSION_NOT_FOUND, project);
    }
}
