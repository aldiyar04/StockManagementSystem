package kz.iitu.itse1910.variant2issenbayev.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
@Data
public class JwtConfigProps {
    private final String secret;
    private final Long expirationMs;
}
