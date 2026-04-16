package samuelvalentini.u5d9ex.exception;

import java.util.List;

public class BadRequestException extends RuntimeException {
    private final List<String> messages;

    public BadRequestException(String message) {
        super(message);
        this.messages = List.of(message);
    }

    public BadRequestException(List<String> messageList) {
        super(String.join(". ", messageList));
        this.messages = messageList;
    }

    public List<String> getMessages() {
        return messages;
    }
}
