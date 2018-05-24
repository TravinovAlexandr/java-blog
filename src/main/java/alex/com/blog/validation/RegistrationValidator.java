package alex.com.blog.validation;

import alex.com.blog.domaine.User;
import alex.com.blog.dto.RegistrationRestValidDto;
import alex.com.blog.service.UserService;
import alex.com.blog.util.ValidationKit;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Component
public class RegistrationValidator implements ValidatorInterface {

    private UserService userService;
    private MessageSource cMessageSource;

    @Override
    public Object validate(final Object o, final HttpServletRequest request) {
        Objects.requireNonNull(o);

        RegistrationRestValidDto dto = new RegistrationRestValidDto();
        final User user = (User) o;
        final ValidationKit kit = new ValidationKit();

        if(kit.containsIllegals(user.getNick())) {
            dto.setNick(cMessageSource.getMessage("valid.violation", null, request.getLocale()));
            dto.setFlag(true);
        }
        if(kit.containsIllegals(user.getEmail())) {
            dto.setEmail(cMessageSource.getMessage("valid.violation", null, request.getLocale()));
            dto.setFlag(true);
        }
        if(kit.containsIllegals(user.getPassword())) {
            dto.setPassword(cMessageSource.getMessage("valid.violation", null, request.getLocale()));
            dto.setFlag(true);
        }
        if(userService.findByEmail(user.getEmail()) != null) {
            dto.setEmail(cMessageSource.getMessage("valid.email.exist", null, request.getLocale()));
            dto.setFlag(true);
        }
        if(userService.findByNick(user.getNick()) != null) {
            dto.setNick(cMessageSource.getMessage("valid.nick.exist", null, request.getLocale()));
            dto.setFlag(true);
        }
        if(user.getNick().length() < 3 || user.getNick().length() > 18) {
            dto.setNick(cMessageSource.getMessage("valid.nick.size", null, request.getLocale()));
            dto.setFlag(true);
        }
        if(user.getPassword().length() < 3 || user.getPassword().length() > 50) {
            dto.setPassword(cMessageSource.getMessage("valid.password.size", null, request.getLocale()));
            dto.setFlag(true);
        }
        if(kit.containsDog(user.getEmail()) && (user.getEmail().length() < 3 || user.getEmail().length() > 320)) {
            dto.setEmail(cMessageSource.getMessage("valid.email.size", null, request.getLocale()));
            dto.setFlag(true);
        }
        if(user.getNick() == null) {
            dto.setNick(cMessageSource.getMessage("valid.required", null, request.getLocale()));
            dto.setFlag(true);
        }
        if(user.getEmail() == null) {
            dto.setEmail(cMessageSource.getMessage("valid.required", null, request.getLocale()));
            dto.setFlag(true);
        }
        if(user.getPassword() == null) {
            dto.setPassword(cMessageSource.getMessage("valid.required", null, request.getLocale()));
            dto.setFlag(true);
        }
        List<Character> houndCheck = user.getNick().chars().mapToObj(ch -> (char)ch).collect(ImmutableList.toImmutableList());
        if(houndCheck.stream().filter(ch -> ch.equals('@')).count() > 1) {
            dto.setEmail(cMessageSource.getMessage("valid.email.dogs", null, request.getLocale()));
            dto.setFlag(true);
        }
        return dto;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMessageSource(MessageSource cMessageSource) {
        this.cMessageSource = cMessageSource;
    }
}
