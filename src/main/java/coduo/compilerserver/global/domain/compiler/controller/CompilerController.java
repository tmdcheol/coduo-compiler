package coduo.compilerserver.global.domain.compiler.controller;

import coduo.compilerserver.global.domain.compiler.dto.CompilerRequest;
import coduo.compilerserver.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class CompilerController {

    @PostMapping("/compile")
    public ApiResponse<Void> compileCode(
        @RequestBody CompilerRequest.Code code
    ) {
        log.info("compile request: {}", code);
        return ApiResponse.OK;
    }

}
