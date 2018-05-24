package alex.com.blog.service;


import alex.com.blog.domaine.EmailVerificationToken;
import alex.com.blog.domaine.User;

import java.util.List;

public interface EmailVerificationTokenService {
    void deleteToken(String token);
    EmailVerificationToken findByToken(String token);
    void save(EmailVerificationToken emailVerificationTokenService);
    List<EmailVerificationToken> findAllTokens();
}
