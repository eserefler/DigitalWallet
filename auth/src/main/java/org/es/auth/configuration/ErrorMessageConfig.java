package org.es.auth.configuration;


import org.es.auth.domain.enums.ErrorMessages;
import org.es.auth.errorhandler.ErrorMessagesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ErrorMessageConfig {
    @Bean
    public ErrorMessagesProvider errorMessagesProvider() {
        return code ->
            Arrays.stream(ErrorMessages.values())
                    .filter(m -> m.getCODE().equals(code))
                    .findFirst()
                    .map(ErrorMessages::getMESSAGE)
                    .orElse("Unknown error code");
    }
}
