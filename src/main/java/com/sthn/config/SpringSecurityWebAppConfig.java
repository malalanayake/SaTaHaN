package com.sthn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.stormpath.spring.config.StormpathWebSecurityConfigurer.stormpath;

@Configuration
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {

    public static final String APP_HREF = "STORMPATH_APP_HREF";
    public static final String API_KEY_ID = "STORMPATH_API_KEY_ID";
    public static final String API_KEY_SECRET = "STORMPATH_API_KEY_SECRET";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(stormpath());

    }

    public static final void initializationStormpath() {
        System.getProperties().put("stormpath.application.href", System.getenv().get(APP_HREF));
        System.getProperties().put("stormpath.client.apiKey.id", System.getenv().get(API_KEY_ID));
        System.getProperties().put("stormpath.client.apiKey.secret", System.getenv().get(API_KEY_SECRET));
    }
}
