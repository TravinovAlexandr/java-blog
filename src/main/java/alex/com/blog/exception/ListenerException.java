package alex.com.blog.exception;

public class ListenerException extends RuntimeException {
    public ListenerException() { super(); }
    public ListenerException(String e) { super(e); }
    public ListenerException(Throwable cause) { super(cause); }
    public ListenerException(String e, Throwable cause) { super(e, cause); }
}