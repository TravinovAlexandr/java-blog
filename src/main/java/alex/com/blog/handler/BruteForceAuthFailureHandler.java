package alex.com.blog.handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("authenticationFailureHandler")
public class BruteForceAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private MessageSource cMessageSource;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final  HttpServletResponse response, final  AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error=true");
        super.onAuthenticationFailure(request, response, exception);
        if(exception.getMessage().equals("block")) {
            request.getSession().setAttribute(WebAttributes.ACCESS_DENIED_403, cMessageSource.getMessage("authorization.block", null, request.getLocale()));
        }
    }

    @Autowired
    public void setMessageSource(MessageSource cMessageSource) {
        this.cMessageSource = cMessageSource;
    }

}
