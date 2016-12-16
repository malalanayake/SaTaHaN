package com.sthn.controller;

import com.google.gson.Gson;
import com.sthn.SaTaHaNServerStart;
import com.sthn.config.RESTAPIConfig;
import com.sthn.config.RouteConfig;
import com.sthn.config.SpringSecurityWebAppConfig;
import com.sthn.model.LogMessage;
import com.sthn.model.Waki;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {SaTaHaNServerStart.class})
@DirtiesContext
public class LogMessageAPITest {

    private static final String MOCK_DIRECT_PUBLISH = "mock:" + RouteConfig.DIRECT_PUBLISH;
    private static final String MOCK_DIRECT_STATUS = "mock:" + RouteConfig.DIRECT_STATUS;
    private static String ACCESS_TOKEN;

    @Autowired
    ModelCamelContext modelCamelContext;
    @EndpointInject(uri = MOCK_DIRECT_PUBLISH)
    MockEndpoint mockPublishRoute;
    @EndpointInject(uri = MOCK_DIRECT_STATUS)
    MockEndpoint mockStatusRoute;
    @Autowired
    private MockMvc mockMvc;

    public LogMessageAPITest() {
        SpringSecurityWebAppConfig.initializationStormpath();
        ACCESS_TOKEN = System.getenv().get("STORMPATH_ACCESS_TOKEN");
    }

    @PostConstruct
    public void init() throws Exception {
        modelCamelContext.getRouteDefinition(RouteConfig.ROUTE_NAME_PUBLISH).adviceWith(modelCamelContext, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint(RouteConfig.DIRECT_PUBLISH).skipSendToOriginalEndpoint().to(MOCK_DIRECT_PUBLISH);
            }
        });

        modelCamelContext.getRouteDefinition(RouteConfig.ROUTE_NAME_STATUS).adviceWith(modelCamelContext, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint(RouteConfig.DIRECT_STATUS).skipSendToOriginalEndpoint().to(MOCK_DIRECT_STATUS);
            }
        });

    }


    @Test
    public void statusOfLogMessageTest() throws Exception {

        this.mockMvc.perform(get("/" + RESTAPIConfig.LOG_MESSAGE_API + "/status")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN)
                .param("id", "300")
        ).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("You have 300 log messages"));

        mockStatusRoute.setExpectedMessageCount(1);
        mockStatusRoute.assertIsSatisfied();
    }

    @Test
    public void publishLogMessageTest() throws Exception {
        Waki waki = new Waki();
        LogMessage logMessage = new LogMessage(1l, "Log test");
        waki.setLogMessage(logMessage);

        Gson json = new Gson();
        String message = json.toJson(waki);
        System.out.println("======" + message + "======");
        this.mockMvc.perform(post("/" + RESTAPIConfig.LOG_MESSAGE_API + "/publish")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN)
                .content(message).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("LogMessage: Log test!"));
        mockPublishRoute.setExpectedMessageCount(1);
        mockPublishRoute.assertIsSatisfied();
    }
}
