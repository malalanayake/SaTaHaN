package com.sthn.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RESTAPIConfigTest {

    @Test
    public void logMessageAPINameShouldBe() {
        assertEquals(RESTAPIConfig.LOG_MESSAGE_API, "log-api");
    }
}
