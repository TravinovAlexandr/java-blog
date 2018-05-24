package alex.com.blog.configuration;

import alex.com.blog.filter.UsernamePasswordAuthFilter;
import alex.com.blog.handler.BruteForceAuthFailureHandler;
import alex.com.blog.handler.LogOutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.Filter;

@Configuration @EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    private BruteForceAuthFailureHandler bruteForceAuthFailureHandler;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/confirm.html").permitAll()
                .mvcMatchers(HttpMethod.POST,"/search/*").permitAll()
                .mvcMatchers(HttpMethod.GET, "/article/*").permitAll()
                .antMatchers("/article").permitAll()
                .antMatchers("/getLastAddedArticles").permitAll()
                .antMatchers("/addArticle").authenticated()
                .antMatchers("/getPageableArticleLinks").authenticated()
                .mvcMatchers(HttpMethod.POST, "/getArticle/*").permitAll()
                .antMatchers("/getAllComments/*").permitAll()
                .antMatchers("/addComment/*").authenticated()
                .antMatchers(HttpMethod.GET,"/person/*").permitAll()
                .antMatchers("/person").authenticated()
                .antMatchers(HttpMethod.POST,"/commonPerson/*").permitAll()
                .antMatchers("/commonPerson").permitAll()
                .antMatchers("/getAvatar").permitAll()
                .antMatchers("/deleteAvatar").authenticated()
                .antMatchers("/changeAvatar").authenticated()
                .antMatchers("/changeInformation").authenticated()

                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/",false)
                .failureUrl("/login?error")
                .passwordParameter("password").usernameParameter("username")
                .permitAll()

                .and().logout()
                .logoutSuccessUrl("/")

                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("rmbmt")
                .tokenValiditySeconds(86400)

                .and().csrf()

                .and().addFilterAt(usernamePasswordAuthFilterBean(), UsernamePasswordAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bcryptPassEncoder());
    }

    @Bean
    public Filter usernamePasswordAuthFilterBean() throws Exception {
        UsernamePasswordAuthFilter authFilter = new UsernamePasswordAuthFilter();
        authFilter.setAuthenticationManager(authenticationManagerBean());
        authFilter.setAuthenticationFailureHandler(bruteForceAuthFailureHandler);
        return authFilter;
    }

    @Bean
    public CookieCsrfTokenRepository cookieCsrfTokenRepository() {
        return new CookieCsrfTokenRepository();
    }

    @Bean
    public PasswordEncoder bcryptPassEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    public void setBruteForceAuthFailureHandler(BruteForceAuthFailureHandler bruteForceAuthFailureHandler) {
        this.bruteForceAuthFailureHandler = bruteForceAuthFailureHandler;
    }

}
