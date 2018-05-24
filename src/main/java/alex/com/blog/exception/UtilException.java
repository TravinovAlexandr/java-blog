package alex.com.blog.exception;

public class UtilException extends RuntimeException {
    public UtilException() { super(); }
    public UtilException(String e) { super(e); }
    public UtilException(Throwable cause) { super(cause); }
    public UtilException(String e, Throwable cause) { super(e, cause); }
}