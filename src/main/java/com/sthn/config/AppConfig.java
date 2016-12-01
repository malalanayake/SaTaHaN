package com.sthn.config;


import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    CamelContext camelContext;

    @Bean
    public Exchange getExchange() {
        return new DefaultExchange(camelContext);
    }

    @Bean
    public Message getMessage() {
        return new DefaultMessage();
    }
}
