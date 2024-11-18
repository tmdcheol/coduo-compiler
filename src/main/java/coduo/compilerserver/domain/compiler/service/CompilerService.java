package coduo.compilerserver.domain.compiler.service;

import coduo.compilerserver.domain.Project;
import coduo.compilerserver.domain.compiler.dto.CompilerConverter;
import coduo.compilerserver.domain.compiler.dto.CompilerRequest;
import coduo.compilerserver.domain.compiler.dto.CompilerResponse;
import coduo.compilerserver.domain.executionresult.ExecutionResult;
import coduo.compilerserver.domain.executionresult.repository.ExecutionResultRepository;
import coduo.compilerserver.domain.executor.CodeExecutor;
import coduo.compilerserver.global.response.exception.GeneralException;
import coduo.compilerserver.global.response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilerService {

    private final CodeExecutor codeExecutor;
    private final ExecutionResultRepository executionResultRepository;

    @Transactional
    public CompilerResponse.ExecutionResult run(
        CompilerRequest.CompileRequest request,
        String executionId
    ) {
        Project project = CompilerConverter.to(request);
        ExecutionResult executionResult = codeExecutor.execute(project, executionId);
        log.debug("executionResult : {}", executionResult);

        executionResultRepository.save(executionResult);
        return CompilerConverter.to(executionResult);
    }

    public CompilerResponse.ExecutionResult getExecutionStatus(String executionId) {
        ExecutionResult executionResult = executionResultRepository.findByExecutionId(executionId)
            .orElseThrow(() -> new GeneralException(ErrorStatus.EXECUTION_RESULT_NOT_FOUND));

        log.debug("executionResult:{}", executionResult);
        return CompilerConverter.to(executionResult);
    }

}
