package alex.com.blog.util;

import alex.com.blog.domaine.User;
import alex.com.blog.exception.UtilException;
import alex.com.blog.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindByNickOrEmail {

    private UserService userService;

    public User getUser(final String userData) {
        if(userData.contains("@")) {
            return userService.findByEmail(userData);
        } else {
            return userService.findByNick(userData);
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
