package threadPool;

/**
 * @author livejq
 * @since 2019/9/1
 */
public class RunnableDenyException extends RuntimeException {
    public RunnableDenyException (String message) {
        super(message);
    }
}
