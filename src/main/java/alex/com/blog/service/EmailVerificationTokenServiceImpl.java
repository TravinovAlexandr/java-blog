package alex.com.blog.service;

import alex.com.blog.dao.EmailVerificationTokenDao;
import alex.com.blog.domaine.EmailVerificationToken;
import alex.com.blog.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private static final Logger LOGGER = Logger.getLogger(EmailVerificationTokenServiceImpl.class.getSimpleName());
    private EmailVerificationTokenDao emailVerificationTokenDao;

    public EmailVerificationTokenServiceImpl() {}

    @Override @Transactional
    public void deleteToken(final String token) {
        if(token == null) {
            LOGGER.debug("Parameter String is null");
            throw new ServiceException(new NullPointerException());
        }
        try {
            emailVerificationTokenDao.delete(emailVerificationTokenDao.findByToken(token));
        } catch (RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public EmailVerificationToken findByToken(String token) {
        if(token == null) {
            LOGGER.debug("Parameter String is null");
            throw new ServiceException(new NullPointerException());
        }
        try {
            return emailVerificationTokenDao.findByToken(token);
        } catch (RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

//    @Async
    @Override @Transactional
    public void save(EmailVerificationToken token) {
        if(token == null) {
            LOGGER.debug("Parameter EmailVerificationToken is null");
            throw new ServiceException(new NullPointerException());
        }
        try {
            emailVerificationTokenDao.save(token);
        } catch (RuntimeException e) { throw new ServiceException(e.getCause()); }

    }

    @Override @Transactional(readOnly = true)
    public List<EmailVerificationToken> findAllTokens() {
        try {
            return emailVerificationTokenDao.findAll();
        } catch (RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Autowired
    public void setEmailVerificationTokenDao(EmailVerificationTokenDao emailVerificationTokenDao) {
            this.emailVerificationTokenDao = emailVerificationTokenDao;
    }

}
