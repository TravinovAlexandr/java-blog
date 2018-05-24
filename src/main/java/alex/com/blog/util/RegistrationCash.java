package alex.com.blog.util;

public interface RegistrationCash {

    void push(final String ip);
    boolean isBlocked(final String ip);

}
