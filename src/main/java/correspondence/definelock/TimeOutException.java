package correspondence.definelock;

/**
 * @author livejq
 * @since 19-8-25
 **/
class TimeOutException extends Exception {

    private String message;
    public TimeOutException() {
        super();
    }
    public TimeOutException(String message) {
        super(message);
        this.message = message;
    }

    // 由于构造函数调用super（message）,所以不用重写此方法
    /*@Override
    public String getMessage() {
        return message;
    }*/
}
