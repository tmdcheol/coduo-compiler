package coduo.compilerserver.global.session.interceptor;

import coduo.compilerserver.global.response.exception.GeneralException;
import coduo.compilerserver.global.response.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class SessionCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        return true;
    }
}


