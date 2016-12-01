package com.sthn.service;


import com.sthn.config.RouteConfig;
import com.sthn.service.impl.LogMessageServiceImpl;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class LogMessageServiceImplTest {

    private static final String MOCK_DIRECT_PUBLISH = "mock:" + RouteConfig.DIRECT_PUBLISH;
    private static final String MOCK_DIRECT_STATUS = "mock:" + RouteConfig.DIRECT_STATUS;

    @Autowired
    LogMessageServiceImpl logMessageService;
    @Autowired
    ModelCamelContext modelCamelContext;

    @EndpointInject(uri = MOCK_DIRECT_PUBLISH)
    MockEndpoint mockPublishRoute;

    @EndpointInject(uri = MOCK_DIRECT_STATUS)
    MockEndpoint mockStatusRoute;

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
    public void publishMethodShouldEnterTheDataToPublishRoute() throws InterruptedException {
        logMessageService.publish("Publish Sample Log Data");
        mockPublishRoute.setExpectedMessageCount(1);
        mockPublishRoute.assertIsSatisfied();
    }

    @Test
    public void statusMethodShouldEnterTheDataToStatusRoute() throws InterruptedException {
        logMessageService.status("Status Sample Log Data");
        mockStatusRoute.setExpectedMessageCount(1);
        mockStatusRoute.assertIsSatisfied();
    }
}
