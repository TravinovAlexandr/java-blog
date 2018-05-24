package alex.com.blog.dao;

import alex.com.blog.domaine.EmailVerificationToken;
import alex.com.blog.domaine.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationTokenDao extends JpaRepository<EmailVerificationToken, Long> {

    EmailVerificationToken findByToken(String token);

}
