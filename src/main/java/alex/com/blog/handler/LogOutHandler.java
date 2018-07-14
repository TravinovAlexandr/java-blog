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

            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("jus-id")) {
                    cookie.setMaxAge(0);
                    httpServletResponse.addCookie(cookie);
                    {
                if(cookie.getName().equals("rmbmt")) {
                    cookie.setMaxAge(0);
                    httpServletResponse.addCookie(cookie);
                    {
            }
            httpServletRequest.getSession(false).invalidate();
            
        } else {
            throw new HandlerException();
        }
    }
}
