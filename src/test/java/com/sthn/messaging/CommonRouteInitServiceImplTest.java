package com.sthn.messaging;


import com.sthn.config.RouteConfig;
import com.sthn.messaging.impl.CommonRouteInitServiceImpl;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class CommonRouteInitServiceImplTest {

    private static final String MOCK_DIRECT_PUBLISH = "mock:" + RouteConfig.DIRECT_PUBLISH;

    @Autowired
    CommonRouteInitServiceImpl commonRouteInitService;
    @Autowired
    ModelCamelContext modelCamelContext;

    @EndpointInject(uri = MOCK_DIRECT_PUBLISH)
    MockEndpoint mockPublishRoute;


    @PostConstruct
    public void init() throws Exception {
        modelCamelContext.getRouteDefinition(RouteConfig.ROUTE_NAME_PUBLISH).adviceWith(modelCamelContext, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint(RouteConfig.DIRECT_PUBLISH).skipSendToOriginalEndpoint().to(MOCK_DIRECT_PUBLISH);
            }
        });

    }

    @Test
    public void exchangeShouldContainTheGivenMessageAndEnterToTheGivenRoute() throws InterruptedException {
        Exchange exAfter = commonRouteInitService.enter(RouteConfig.DIRECT_PUBLISH, "Sample Log Data");
        assertEquals(exAfter.getIn().getBody().toString(), "Sample Log Data");

        mockPublishRoute.setExpectedMessageCount(1);
        mockPublishRoute.assertIsSatisfied();
    }

}
