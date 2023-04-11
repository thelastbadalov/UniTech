package az.company.beans;

import az.company.exception.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RequiredBeans {

    public RequiredBeans(RestTemplateResponseErrorHandler responseErrorHandler) {
        this.responseErrorHandler = responseErrorHandler;
    }

    private final RestTemplateResponseErrorHandler responseErrorHandler;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().errorHandler(responseErrorHandler).build();
    }
}
