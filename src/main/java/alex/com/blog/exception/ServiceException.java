package alex.com.blog.exception;

public class ServiceException extends RuntimeException {
    public ServiceException() { super(); }
    public ServiceException(String e) { super(e); }
    public ServiceException(Throwable cause) { super(cause); }
    public ServiceException(String e, Throwable cause) { super(e, cause); }
}
