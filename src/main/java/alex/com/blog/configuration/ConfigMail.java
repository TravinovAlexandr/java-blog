package alex.com.blog.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;

@Configuration
@PropertySource("classpath:/mail_config.properties")
public class ConfigMail {

        @Value("${mail.protocol}") private String protocol;
        @Value("${mail.host}") private String host;
        @Value("${mail.port}") private int port;
        @Value("${mail.smtp.auth}") private boolean auth;
        @Value("${mail.smtp.starttls.enable}") private boolean starttls;
        @Value("${mail.debug}") private boolean debug;
        @Value("${mail.from}") private String from;
        @Value("${mail.username}") private String username;
        @Value("${mail.password}") private String password;

        @Bean
        public JavaMailSender javaMailService() {
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

            Properties mailProperties = new Properties();
            mailProperties.put("mail.smtp.auth", auth);
            mailProperties.put("mail.smtp.starttls.enable", starttls);
            mailProperties.put("mail.debug", debug);

            javaMailSender.setJavaMailProperties(mailProperties);
            javaMailSender.setHost(host);
            javaMailSender.setPort(port);
            javaMailSender.setUsername(username);
            javaMailSender.setPassword(password);
            javaMailSender.setProtocol(protocol);
            javaMailSender.setDefaultEncoding("UTF-8");
            return javaMailSender;
        }

        @Bean
        public SimpleMailMessage simpleMailMessage() {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            return simpleMailMessage;
        }
    }

