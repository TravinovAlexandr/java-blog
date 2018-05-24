package alex.com.blog.event;

import alex.com.blog.domaine.User;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class RegistrationEvent extends ApplicationEvent {

    private final User user;
    private final Locale locale;
    private final HttpServletRequest request;

    public RegistrationEvent(final User user, final  Locale locale, final HttpServletRequest request) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.request = request;
    }

    public User getUser() {
        return user;
    }
    public Locale getLocale() {
        return locale;
    }
    public HttpServletRequest getRequest() {
        return request;
    }
}
