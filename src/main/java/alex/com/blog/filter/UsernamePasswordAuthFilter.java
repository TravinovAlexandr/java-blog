package alex.com.blog.filter;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            return super.attemptAuthentication(request, response);
        } catch(final InternalAuthenticationServiceException e) {
            if(e.getMessage().equals("block")) {
                try {
                    super.unsuccessfulAuthentication(request, response, new InternalAuthenticationServiceException(e.getMessage()));
                } catch (IOException | ServletException e1) {
                    e1.printStackTrace();
                }
            }
        return null;
        }
    }
}
