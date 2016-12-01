package com.sthn.config;

import com.sthn.messaging.IMessageHandler;
import com.sthn.messaging.IMessageReceiver;
import com.sthn.messaging.impl.APIMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RESTAPIConfig {
    public static final String LOG_MESSAGE_API = "log-api";
    public static final String PUBLISH_API_MESSAGE_HANDLER = "publishAPIMessageHandler";

    @Autowired
    IMessageReceiver messageReceiver;

    @Bean(name = PUBLISH_API_MESSAGE_HANDLER)
    public IMessageHandler getMessageHandlerForPublishAPI() {
        IMessageHandler messageHandler = new APIMessageHandler();
        messageHandler.subscribe(messageReceiver);
        return messageHandler;
    }
}
