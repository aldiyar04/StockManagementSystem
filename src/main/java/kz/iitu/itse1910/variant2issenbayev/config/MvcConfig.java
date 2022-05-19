package kz.iitu.itse1910.variant2issenbayev.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    // This is somehow an MVC related bean, so if placed in SecurityConfig,
    // it forces Web MVC config to initialize too early,
    // when no servlet context was set, causing exception "No ServletContext set".
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // remove the ROLE_ prefix
    }
}
