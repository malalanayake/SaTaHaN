package com.sthn.model;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Waki {
    private LocalDateTime dateTime;
    private LogMessage logMessage;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }
}
