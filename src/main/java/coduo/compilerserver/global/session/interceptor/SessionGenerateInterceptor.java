package coduo.compilerserver.global.session.interceptor;

import static coduo.compilerserver.global.utils.Constants.EXECUTION_KEY;

import coduo.compilerserver.global.session.executionidgenerator.ExecutionIdGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class SessionGenerateInterceptor implements HandlerInterceptor {

    private final ExecutionIdGenerator executionIdGenerator;

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        HttpSession session = request.getSession(true);
        String executionId = executionIdGenerator.generateExecutionId();
        log.debug("key:{}, executionId:{}", EXECUTION_KEY, executionId);
        session.setAttribute(EXECUTION_KEY, executionId);

        return true;
    }

}


