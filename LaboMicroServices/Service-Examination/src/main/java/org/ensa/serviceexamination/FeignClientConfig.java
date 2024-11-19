package org.ensa.serviceexamination;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication);
            if (authentication != null) {
                String token = (String) authentication.getCredentials();
                if (token != null && !token.isEmpty()) {
                    // Add the token to the Authorization header
                    requestTemplate.header("Authorization", "Bearer " + token);
                } else {
                    System.out.println("No token found in authentication credentials.");
                }
            } else {
                System.out.println("No authentication found in the security context.");
            }
        };
    }
}
