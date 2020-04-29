package com.shiningstar.twitter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix= Twitter4jProperties.TWITTER4J_PREFIX)
public class Twitter4jProperties {
    public static final String TWITTER4J_PREFIX = "twitter4j";
    private Boolean debug = false;

    @NestedConfigurationProperty
    private OAuth oauth = new OAuth();

    @Data
    public static class OAuth {
        private String consumerKey;
        private String consumerSecret;
        private String accessToken;
        private String accessTokenSecret;
    }
}
