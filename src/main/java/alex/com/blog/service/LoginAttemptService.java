package alex.com.blog.service;

public interface LoginAttemptService {

    void loginFailed(String ip);
    void loginSucceeded(String ip);
    boolean isBlocked(String ip);
}
