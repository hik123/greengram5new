package com.green.greengram4.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public final class AppProperties {

    private final Jwt jwt = new Jwt();
    private final Oauth2 oauth2 = new Oauth2();

    @Getter
    @Setter
    public static class Jwt {
        private String secret;
        private String headerSchemeName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
        private int refreshTokenCookieMaxAge;

        public void setRefreshTokenExpiry(long refreshTokenExpiry) {
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenCookieMaxAge =
                    (int) (refreshTokenExpiry * 0.001);
        }
    }

    @Getter
    public static final class Oauth2 {
        private List<String> authorizedRedirectUris = new ArrayList();
    }
}
