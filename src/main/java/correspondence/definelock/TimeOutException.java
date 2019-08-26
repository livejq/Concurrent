package correspondence.definelock;

/**
 * @author livejq
 * @since 19-8-25
 **/
public class TimeOutException extends RuntimeException {

    public TimeOutException() {
        super();
    }
    public TimeOutException(String message) {
        super(message);
    }
}
