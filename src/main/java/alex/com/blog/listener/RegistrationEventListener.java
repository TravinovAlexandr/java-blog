package alex.com.blog.listener;

import alex.com.blog.annotations.Log;
import alex.com.blog.event.RegistrationEvent;
import alex.com.blog.exception.ListenerException;
import alex.com.blog.service.UserService;
import alex.com.blog.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;


@Component
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent>{

    @Log
    private Logger LOGGER;
    private JavaMailSender javaMailSender;
    private SimpleMailMessage simpleMailMessage;
    private UserService userService;
    private MessageSource cMessageSource;

    @Override @Transactional
    public void onApplicationEvent(final RegistrationEvent event) {

        if(event == null) throw new ListenerException();

        final String token = UUID.randomUUID().toString();
        final String path = getUrl(event.getRequest()) + "/confirm.html?token=" + token;

        try {
            simpleMailMessage.setSubject(cMessageSource.getMessage("registration.confirmation", null, event.getLocale()));
            simpleMailMessage.setSentDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));
            simpleMailMessage.setText(cMessageSource.getMessage("registration.last.step", null, event.getLocale()) + "\n\n" + path);
            simpleMailMessage.setTo(event.getUser().getEmail());
            javaMailSender.send(simpleMailMessage);

            userService.createVerificationToken(event.getUser(), token);

        } catch (org.springframework.mail.MailException e) {
            LOGGER.debug(e.getMessage());
            throw new ListenerException("Email wasn't sent.");
        } catch (RuntimeException e) {
            LOGGER.debug(e.getMessage());
            throw new ListenerException("Persisting of VerificationToken caused Exception.");
        }
    }

    private String getUrl(final HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @Autowired @Qualifier("javaMailService")
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMessage(MessageSource cMessageSource) {
        this.cMessageSource = cMessageSource;
    }

}
