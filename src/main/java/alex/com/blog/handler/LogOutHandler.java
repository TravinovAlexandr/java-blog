package alex.com.blog.handler;

import alex.com.blog.exception.HandlerException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LogOutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

        if(httpServletRequest.getMethod().equals("POST")
                && authentication.isAuthenticated()
                && httpServletRequest.getRequestURI().equals("/logout")) {

            Cookie[] cookies = httpServletRequest.getCookies();

            for (Cookie cooka : cookies) {
                if(cooka.getName().equals("jus-id")) cooka.setMaxAge(0);
                if(cooka.getName().equals("rmbmt")) cooka.setMaxAge(0);
            }

            httpServletRequest.getSession().invalidate();
            authentication.setAuthenticated(false);

        } else {
            throw new HandlerException();
        }
    }
}
