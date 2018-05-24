package alex.com.blog.service;

import alex.com.blog.exception.ServiceException;
import alex.com.blog.util.FindByNickOrEmail;
import alex.com.blog.domaine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private FindByNickOrEmail findByNickOrEmail;
    private LoginAttemptService loginAttemptService;
    private HttpServletRequest httpServletRequest;

    @Override @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String var) throws UsernameNotFoundException {
        String s = getClientIP();
        if(loginAttemptService.isBlocked(s)) {
            throw new ServiceException("block");
        }
            final boolean accountNonExpired = true;
            final boolean credentialsNonExpired = true;
            final boolean accountNonLocked = true;

        try {
            final User user = findByNickOrEmail.getUser(var);
            List<GrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new org.springframework.security.core.userdetails.User(
                    user.getNick(),
                    user.getPassword(),
                    user.isEnable(),
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    authorities
            );
        } catch (final RuntimeException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    private String getClientIP() {
        String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return httpServletRequest.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Autowired
    public void setLoginAttemptService(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }
    @Autowired
    public void setFindByNickOrEmail(FindByNickOrEmail findByNickOrEmail) {
        this.findByNickOrEmail = findByNickOrEmail;
    }
}
