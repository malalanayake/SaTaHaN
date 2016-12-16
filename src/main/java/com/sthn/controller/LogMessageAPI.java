package com.sthn.controller;

import com.sthn.config.RESTAPIConfig;
import com.sthn.messaging.IMessageHandler;
import com.sthn.model.LogMessage;
import com.sthn.model.Waki;
import com.sthn.service.ILogMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(RESTAPIConfig.LOG_MESSAGE_API)
public class LogMessageAPI {

    private static final String template_post = "LogMessage: %s!";
    private static final String template_get = "You have %s log messages";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    ILogMessageService iLogMessageService;

    @Autowired
    @Qualifier(RESTAPIConfig.PUBLISH_API_MESSAGE_HANDLER)
    IMessageHandler publishAPIMessageHandler;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public LogMessage status(@RequestParam(value = "id", required = true) String id) {
        iLogMessageService.status(id);
        return new LogMessage(Long.getLong(id),
                String.format(template_get, id));
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LogMessage publish(@RequestBody Waki waki) {
        publishAPIMessageHandler.handOver(waki);
        return new LogMessage(counter.incrementAndGet(),
                String.format(template_post, waki.getLogMessage().getMessage()));
    }

}
