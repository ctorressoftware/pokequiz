package com.ctorres.pokequiz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private int accessMins;
    private String issuer;
    private int refreshDays;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getAccessMins() {
        return accessMins;
    }

    public void setAccessMins(int accessMins) {
        this.accessMins = accessMins;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public int getRefreshDays() {
        return refreshDays;
    }

    public void setRefreshDays(int refreshDays) {
        this.refreshDays = refreshDays;
    }
}
