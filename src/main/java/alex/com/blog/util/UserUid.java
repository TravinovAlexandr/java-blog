package alex.com.blog.util;

import alex.com.blog.annotations.Log;
import alex.com.blog.exception.UtilException;
import alex.com.blog.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class UserUid implements UidGenerator {

    @Log
    private Logger LOGGER;
    private UserService userService;
    private static final ThreadLocal<Integer> count = new ThreadLocal<>();

    @Override
    public String generateUid(int attempts) {
        count.set(attempts);
            String uid = UUID.randomUUID().toString().substring(0, 8) + RandomStringUtils.randomAlphabetic(4);
            if (count.get() <= 1) {
                LOGGER.info("--Uid wasn't generated.");
                throw new UtilException("--Uid wasn't generated. 20 attempts is max");
            } else if (userService.uidExist(uid)) {
                generateUid(count.get() - 1);
            }
            return uid;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
