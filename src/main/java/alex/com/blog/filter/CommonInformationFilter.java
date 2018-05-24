package alex.com.blog.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@WebFilter(filterName = "CommonInformationFilter", urlPatterns = "/*")
public class CommonInformationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;

        String logForm ="javascript:;";
        String person = "<li><a href=\"/person\">Home</a></li>";
        String onClick = "onclick=\"document.querySelector('.Log').submit();\"";

        if(!auth.getName().equals("anonymousUser")) {
            httpServletRequest.setAttribute("log", "/logout");
            httpServletRequest.setAttribute("method", "post");
            httpServletRequest.setAttribute("enter", "  Выход");
            httpServletRequest.setAttribute("logLink", logForm);
            httpServletRequest.setAttribute("person", person);
            httpServletRequest.setAttribute("onClick", onClick);
        }
        else {
            httpServletRequest.setAttribute("log", "/login");
            httpServletRequest.setAttribute("method", "get");
            httpServletRequest.setAttribute("enter", " Вход");
            httpServletRequest.setAttribute("logLink", "/login");
            httpServletRequest.setAttribute("person", "");
            httpServletRequest.setAttribute("onClick", "");
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {}

}
