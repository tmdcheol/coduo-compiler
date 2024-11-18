package coduo.compilerserver.domain.compiler.service;

import coduo.compilerserver.domain.Project;
import coduo.compilerserver.domain.compiler.dto.CompilerConverter;
import coduo.compilerserver.domain.compiler.dto.CompilerRequest;
import coduo.compilerserver.domain.compiler.dto.CompilerResponse;
import coduo.compilerserver.domain.executor.ExecutionResult;
import coduo.compilerserver.domain.executor.CodeExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilerService {

    private final CodeExecutor codeExecutor;

    public CompilerResponse.ExecutionResult run(
            CompilerRequest.CompileRequest request,
            String executionId
    ) {
        Project project = CompilerConverter.to(request);
        ExecutionResult executionResult = codeExecutor.execute(project, executionId);
        log.debug("executionResult : {}", executionResult);

        return CompilerConverter.to(executionResult);
    }

}
