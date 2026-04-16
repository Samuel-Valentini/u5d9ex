package samuelvalentini.u5d9ex.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super("Elemento non presente nel database: " + message);
    }
}
