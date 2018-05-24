package alex.com.blog.exception;

public class ControllerException extends RuntimeException {
    public ControllerException() { super(); }
    public ControllerException(String e) { super(e); }
    public ControllerException(Throwable cause) { super(cause); }
    public ControllerException(String e, Throwable cause) { super(e, cause); }
}
