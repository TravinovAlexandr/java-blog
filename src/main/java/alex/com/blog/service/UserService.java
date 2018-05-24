package alex.com.blog.service;

import alex.com.blog.domaine.*;

import java.util.List;

public interface UserService {
    void registerNewUserAccount(User user);
    void createVerificationToken(User user, String token);
    User findByNick(String nick);
    User findByEmail(String email);
    User findByToken(EmailVerificationToken token);
    User findByUid(String uid);
    void save(User user);
    void deleteById(Long id);
    void deleteByUser(User user);
    void deleteByToken(EmailVerificationToken emailVerificationToken);
    boolean emailExist(String email);
    boolean nickExist(String nick);
    boolean uidExist(String uid);
    void changeAvatar(User user, Avatar avatar);
    void deleteAvatar(User user);
    List<User> findAllUsers();
    List<User> findByEnableFalse();

}
