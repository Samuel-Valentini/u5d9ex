package samuelvalentini.u5d9ex.dto;

import java.time.LocalDateTime;

public class ErrorPayload {
    private String message;
    private LocalDateTime timeStamp;

    public ErrorPayload(String message, LocalDateTime timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
