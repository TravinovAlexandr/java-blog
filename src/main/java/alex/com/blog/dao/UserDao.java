package alex.com.blog.dao;

import alex.com.blog.domaine.Article;
import alex.com.blog.domaine.Comment;
import alex.com.blog.domaine.EmailVerificationToken;
import alex.com.blog.domaine.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByNick(String nick);
    User findByEmail(String email);
    User findByUid(String uid);
    User findByEmailVerificationToken(EmailVerificationToken token);
    void deleteByEmailVerificationToken(EmailVerificationToken emailVerificationToken);
    List<User> findByEnableFalse();
}
