package alex.com.blog.configuration;


import alex.com.blog.util.*;
import alex.com.blog.util.image.imageload.ImageLoadResize;
import alex.com.blog.util.image.imageload.ImageLoadResizeImpl;
import alex.com.blog.validation.ValidatorInterface;
import alex.com.blog.validation.RegistrationValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.context.request.RequestContextListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@ComponentScan({"alex.com.blog.configuration", "alex.com.blog.controller",
        "alex.com.blog.service", "alex.com.blog.filter", "alex.com.blog.listener","alex.com.blog.handler" ,"alex.com.blog.validation",
        "alex.com.blog.exception", "alex.com.blog.util", "alex.com.blog.interceptors"})
@EnableJpaRepositories("alex.com.blog.dao")
@EntityScan("alex.com.blog.domaine")
@EnableAsync
@EnableScheduling
public class AppContext {

    public static void main(String[] args) {
        SpringApplication.run(AppContext.class);
    }

    @Bean public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean("cMessageSource")
    public MessageSource cMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/common","classpath:/token_confirm", "classpath:/reg_validation",
                "classpath:/mail_confirm", "classpath:/filter_info", "classpath:/validation", "classpath:/main");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public ValidatorInterface registrationValidator() {
        return new RegistrationValidator();
    }

    @Bean
    public FindByNickOrEmail findByNickOrEmail() {
        return new FindByNickOrEmail();
    }

    @Bean
    public UidGenerator userUidGenerator() {
        return new UserUid();
    }

    @Bean
    public UidGenerator articleUidGenerator() {
        return new ArticleUid();
    }

    @Bean
    public ImageLoadResize imageResize() {
        return new ImageLoadResizeImpl();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("--ThreadPoolTaskExecutor");
        executor.initialize();
        return executor;
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "--ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

    @Bean
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(50);
    }

}