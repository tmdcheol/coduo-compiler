package coduo.compilerserver.global.session.argumentresolver;

import static coduo.compilerserver.global.utils.Constants.EXECUTION_KEY;

import coduo.compilerserver.global.session.ExecutionId;
import coduo.compilerserver.global.response.exception.GeneralException;
import coduo.compilerserver.global.response.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class ExecutionIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ExecutionId.class) != null
            && parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            log.error("session에 executionId가 존재하지 않습니다.");
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        return session.getAttribute(EXECUTION_KEY);
    }

}
