package coduo.compilerserver.global.config;

import coduo.compilerserver.global.session.executionidgenerator.ExecutionIdGenerator;
import coduo.compilerserver.global.session.executionidgenerator.UUIDExecutionIdGenerator;
import coduo.compilerserver.global.session.interceptor.SessionCheckInterceptor;
import coduo.compilerserver.global.session.interceptor.SessionGenerateInterceptor;
import coduo.compilerserver.global.session.argumentresolver.ExecutionIdArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*
     * interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /*
         * - /api/compile 경로에서만 세션을 생성 또는 유지
         * - executionId를 부여
         */
        registry.addInterceptor(sessionGenerateInterceptor())
            .order(1)
            .addPathPatterns("/api/compile");

        /*
         * 모든 경로에서 세션이 있는지 확인
         */
        registry.addInterceptor(sessionCheckInterceptor())
            .order(2)
            .addPathPatterns("/**");
    }

    @Bean
    public SessionGenerateInterceptor sessionGenerateInterceptor() {
        return new SessionGenerateInterceptor(executionIdGenerator());
    }

    @Bean
    public SessionCheckInterceptor sessionCheckInterceptor() {
        return new SessionCheckInterceptor();
    }

    @Bean
    public ExecutionIdGenerator executionIdGenerator() {
        return new UUIDExecutionIdGenerator();
    }


    /*
     * session argumentResolver
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(executionIdArgumentResolver());
    }

    @Bean
    public ExecutionIdArgumentResolver executionIdArgumentResolver() {
        return new ExecutionIdArgumentResolver();
    }

}
