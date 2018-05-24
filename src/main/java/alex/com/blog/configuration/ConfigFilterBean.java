package alex.com.blog.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import javax.servlet.Filter;

@Configuration
public class ConfigFilterBean {

    @Bean
    public Filter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public FilterRegistrationBean hiddenHttpMethodFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(hiddenHttpMethodFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

}
