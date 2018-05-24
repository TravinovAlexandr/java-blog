package alex.com.blog.service;

import alex.com.blog.domaine.*;
import alex.com.blog.exception.ServiceException;
import alex.com.blog.dao.UserDao;
import alex.com.blog.util.RegistrationCash;
import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getSimpleName());
    private UserDao userDao;
    private RoleService roleService;
    private EmailVerificationTokenService emailVerificationTokenService;
    private PasswordEncoder passwordEncoder;
    private AvatarService avatarService;
    private RegistrationCash registrationRegistrationCash;
    private HttpServletRequest httpServletRequest;

    public UserServiceImpl() {}

    @Override @Transactional(readOnly = true)
    public User findByNick(final String nick) {
        if(nick == null) {throw new ServiceException(new NullPointerException()); }
        try {
            return userDao.findByNick(nick);
        }catch (RuntimeException e) {throw new ServiceException( e.getCause());}
    }

    @Override @Transactional(readOnly = true)
    public User findByEmail(final String email) {
        if(email == null) {
            throw new ServiceException(new NullPointerException());
        }
        try {
            return userDao.findByEmail(email);
        }catch (RuntimeException e) {throw new ServiceException(e.getCause());}
    }

    @Override @Transactional(readOnly = true)
    public User findByUid(final String uid) {
        if (uid == null) {
            throw new ServiceException(new NullPointerException());
        }
        try {
            return userDao.findByUid(uid);
        } catch (RuntimeException e) { throw new ServiceException(e.getCause());}
    }

    @Override @Transactional
    public void save(final User user) {
        if (user == null) { throw new ServiceException(new NullPointerException()); }
        try {
            userDao.save(user);
        } catch (RuntimeException e) {throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional
    public void deleteById(final Long id) {
        if (id == null) { throw new ServiceException(new NullPointerException()); }
        try {
            userDao.delete(id);
        } catch (RuntimeException e) {throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public User findByToken(final EmailVerificationToken token) {
        if (token == null) { throw new ServiceException(new NullPointerException()); }
        try {
            return userDao.findByEmailVerificationToken(token);
        } catch (RuntimeException e) {throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional
    public void deleteByUser(final User user) {
        if (user == null) { throw new ServiceException(new NullPointerException()); }
        try {
            userDao.delete(user);
        } catch (RuntimeException e) {throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional
    public void deleteByToken(final EmailVerificationToken token) {
        if (token == null) { throw new ServiceException(new NullPointerException()); }
        try {
            userDao.deleteByEmailVerificationToken(token);
        } catch (RuntimeException e) {throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public boolean emailExist(final String email) {
        if (email == null) { throw new ServiceException(new NullPointerException()); }
        try {
            return userDao.findByEmail(email) != null;
        } catch (RuntimeException e) {throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public boolean nickExist(final String nick) {
        if (nick == null) { throw new ServiceException(new NullPointerException()); }
        try {
            return userDao.findByNick(nick) != null;
        } catch (RuntimeException e) {throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public boolean uidExist(final String uid) {
        if (uid == null) { throw new ServiceException(new NullPointerException()); }
        try {
            return userDao.findByUid(uid) != null;
        } catch (RuntimeException e) {throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional
    public void changeAvatar(User user, final Avatar avatar) {
        if (avatar == null || user == null) {
            LOGGER.debug("Parameter User || Avatar is null");
            throw new ServiceException( new NullPointerException());
        }
        try {
            avatarService.addAvatar(avatar);
            List<Avatar> changedAvatars = new ArrayList<>();
            changedAvatars.add(avatarService.findByUrl(avatar.getUrl()));
            changedAvatars.add(user.getAvatars().stream().filter(it -> it.getId() == 1L).findFirst().get());
            user.setAvatars(changedAvatars);
            userDao.save(user);
        } catch (RuntimeException e) {
            throw new ServiceException(e.getCause());
        }
    }

    @Override @Transactional
    public void deleteAvatar(final User user) {
        if (user == null) throw new ServiceException("--User is null.", new NullPointerException());
        try {
            if (user.getAvatars().size() > 1)
                avatarService.deleteAvatar(user.getAvatars().stream().filter(it -> it.getId() != 1L).findFirst().get());

            user.setAvatars(user.getAvatars().stream().filter(it -> it.getId() == 1L).collect(Collectors.toList()));
            userDao.save(user);
        } catch (RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        try {
            return userDao.findAll();
        } catch (RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public List<User> findByEnableFalse() {
        try {
        return userDao.findByEnableFalse();
        } catch (RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional
    public void registerNewUserAccount(User user) {
        if(user == null) {
            LOGGER.debug("Parameter User is null");
            throw new ServiceException(new NullPointerException());
        }

        registrationRegistrationCash.push(getClientIP());
        if(registrationRegistrationCash.isBlocked(getClientIP())) throw new ServiceException("Only allow 1 registration per day period.");

        if(emailExist(user.getEmail())) {
            throw new ServiceException("Email is already exist.");
        } else if(nickExist(user.getNick())) {
            throw new ServiceException("Nick is already exist.");
        } else {
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRoles(roleService.findAll().stream()
                        .filter(role -> role.getName().equals("ROLE_USER")).collect(ImmutableList.toImmutableList()));

                user.setEnable(false);
                user.setCreationDate(LocalDate.now());
                user.setCreationTime(LocalTime.now());
                user.setAvatars(Collections.singletonList(avatarService.getAvatarById(1L)));
                userDao.save(user);
            } catch(RuntimeException e) { throw new ServiceException(e.getCause());}
        }
    }

    @Override @Transactional
    public void createVerificationToken(final User user, final String token) {
        if(user == null || token == null) { throw new ServiceException(new NullPointerException()); }
        try {
            final EmailVerificationToken myToken = new EmailVerificationToken(user, token);
            emailVerificationTokenService.save(myToken);
        } catch(RuntimeException e) { throw new ServiceException(e.getCause());}
    }

    private String getClientIP() {
        String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return httpServletRequest.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setEmailVerificationTokenService(EmailVerificationTokenService emailVerificationTokenService) {
        this.emailVerificationTokenService = emailVerificationTokenService;
    }

    @Autowired
    public void setAvatarService(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRegistrationRegistrationCash(RegistrationCash registrationRegistrationCash) {
        this.registrationRegistrationCash = registrationRegistrationCash;
    }

    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
}

