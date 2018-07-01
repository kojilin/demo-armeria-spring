package com.example.demo;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.PathMapping;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.HttpServiceRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoConfiguration {
    @Bean
    HttpServiceRegistrationBean hello() {
        return new HttpServiceRegistrationBean()
                .setServiceName("okService")
                .setService((ctx, req) ->
                        HttpResponse.from(req.aggregate()
                                .handle((aggregatedHttpMessage, throwable) -> {
                                    if (throwable != null) {
                                        return HttpResponse.ofFailure(throwable);
                                    }
                                    return HttpResponse.of(aggregatedHttpMessage.content().toStringUtf8());
                                })))
                .setPathMapping(PathMapping.ofExact("/ok"))
                .setDecorators(LoggingService.newDecorator());
    }
}
