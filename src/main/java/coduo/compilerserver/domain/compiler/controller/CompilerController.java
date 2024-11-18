package coduo.compilerserver.domain.compiler.controller;

import coduo.compilerserver.domain.compiler.dto.CompilerRequest;
import coduo.compilerserver.domain.compiler.dto.CompilerResponse;
import coduo.compilerserver.domain.compiler.dto.CompilerResponse.ExecutionResult;
import coduo.compilerserver.domain.compiler.service.CompilerService;
import coduo.compilerserver.global.session.ExecutionId;
import coduo.compilerserver.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CompilerController {

    private final CompilerService service;

    @PostMapping("/compile")
    public ApiResponse<CompilerResponse.ExecutionResult> compileProject(
        @RequestBody CompilerRequest.CompileRequest request,
        @ExecutionId String executionId
    ) {
        CompilerResponse.ExecutionResult response = service.run(request, executionId);
        return ApiResponse.onSuccess(response);
    }

    @Deprecated
    @GetMapping("/execution-status")
    public ApiResponse<CompilerResponse.ExecutionResult> getExecutionStatus(
        @ExecutionId String executionId
    ) {
        CompilerResponse.ExecutionResult response = service.getExecutionStatus(executionId);
        return ApiResponse.onSuccess(response);
    }

}
