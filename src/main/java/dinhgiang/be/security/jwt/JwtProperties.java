package dinhgiang.be.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
@Configuration
public class JwtProperties {

    @Value("${app.jwt.secret-key}")
    private String secretKey;
    @Value("${app.jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${app.jwt.refresh-key}")
    private String refreshKey;
    @Value("${app.jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @Value("${app.jwt.issuer}")
    private String issuer;
}
