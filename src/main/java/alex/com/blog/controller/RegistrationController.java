package alex.com.blog.controller;


import alex.com.blog.annotations.Log;
import alex.com.blog.domaine.EmailVerificationToken;
import alex.com.blog.event.RegistrationEvent;
import alex.com.blog.domaine.User;
import alex.com.blog.exception.HandlerException;
import alex.com.blog.exception.ListenerException;
import alex.com.blog.exception.ServiceException;
import alex.com.blog.service.EmailVerificationTokenService;
import alex.com.blog.service.UserService;
import alex.com.blog.util.Logger;
import alex.com.blog.util.UidGenerator;
import alex.com.blog.util.ValidationKit;
import alex.com.blog.dto.RegistrationRestValidDto;
import alex.com.blog.validation.ValidatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;


@Controller
public class RegistrationController {

    @Log
    private Logger LOGGER;
    private UserService userService;
    private ApplicationEventPublisher applicationEventPublisher;
    private EmailVerificationTokenService tokenService;
    private MessageSource cMessageSource;
    private ValidatorInterface validatorInterface;
    private UidGenerator userUidGenerator;
    private TaskExecutor taskExecutor;

    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher applicationEventPublisher,
                                  @Qualifier("registrationValidator")ValidatorInterface validatorInterface,
                                  MessageSource cMessageSource, EmailVerificationTokenService tokenService, UidGenerator userUidGenerator,
                                  TaskExecutor taskExecutor) {

        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.validatorInterface = validatorInterface;
        this.tokenService = tokenService;
        this.cMessageSource = cMessageSource;
        this.userUidGenerator = userUidGenerator;
        this.taskExecutor = taskExecutor;
    }

    @GetMapping("/registration")
    public String registration() { return "registration"; }

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> registration(@ModelAttribute("userForm") final User userForm,
                                          final BindingResult br, final HttpServletRequest request) {

        if(userForm == null)
            return new ResponseEntity<>(cMessageSource.getMessage("server.error",null, request.getLocale()), HttpStatus.BAD_REQUEST);

        RegistrationRestValidDto validResult = (RegistrationRestValidDto) validatorInterface.validate(userForm, request);
        if(br.hasErrors() ||  validResult.isFlag()) {
            validResult.setMessage(cMessageSource.getMessage("registration.error", null, request.getLocale()));
            return new ResponseEntity<>(validResult, HttpStatus.BAD_REQUEST);
        } else {
            try {
                userForm.setUid(userUidGenerator.generateUid(20));
                userService.registerNewUserAccount(userForm);

                taskExecutor.execute(() -> applicationEventPublisher.publishEvent(new RegistrationEvent(userForm, request.getLocale(), request)));

                validResult.setMessage(cMessageSource.getMessage("registration.confirmation.successes", null, request.getLocale()));
                return new ResponseEntity<>(validResult, HttpStatus.OK);
            } catch (ServiceException | ListenerException e) {
                LOGGER.debug(e.getMessage());
                if (e.getMessage().equals("Only allow 1 registration per day period."))
                    return new ResponseEntity<>(cMessageSource.getMessage("registration.allow", null, request.getLocale()), HttpStatus.FORBIDDEN);
                return new ResponseEntity<>(cMessageSource.getMessage("server.error", null, request.getLocale()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping(value = "/confirm.html")
    public ModelAndView registrationConfirmation(@RequestParam(required = false) final String token, final HttpServletRequest request) {

        ModelAndView model = new ModelAndView("confirm.html");
        if(token == null) return model.addObject("message", cMessageSource.getMessage("link.error", null, request.getLocale()));
        final ValidationKit kit = new ValidationKit();
        if(!kit.isUID(token)) return model.addObject("message", cMessageSource.getMessage("link.error", null, request.getLocale()));
        try {
            final EmailVerificationToken emailToken = tokenService.findByToken(token);
            User user = userService.findByToken(emailToken);
            if (emailToken.getExpireDate().isEqual(LocalDate.now()) || emailToken.getExpireDate().isEqual(LocalDate.now().minusDays(1))) {
                user.setEnable(true);

                taskExecutor.execute(() -> userService.save(user));

                tokenService.deleteToken(token);
                return model.addObject("message", cMessageSource.getMessage("registration.successes", null, request.getLocale()));
            } else {

                taskExecutor.execute(() -> userService.deleteByUser(user));
                tokenService.deleteToken(token);
                return model.addObject("message", cMessageSource.getMessage("registration.token.expired", null, request.getLocale()));
            }
        } catch (ServiceException e) {
            LOGGER.debug(e.getMessage());
            return model.addObject("message", cMessageSource.getMessage("registration.error", null, request.getLocale()));
        }
    }

}
