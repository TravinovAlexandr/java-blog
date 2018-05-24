package alex.com.blog.controller;
import alex.com.blog.annotations.Log;
import alex.com.blog.domaine.Avatar;
import alex.com.blog.domaine.User;
import alex.com.blog.dto.*;
import alex.com.blog.exception.DtoException;
import alex.com.blog.exception.ServiceException;
import alex.com.blog.exception.UtilException;
import alex.com.blog.service.UserService;
import alex.com.blog.util.Logger;
import alex.com.blog.util.image.imagesave.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.UUID;

@Controller
public class PersonController {

    @Log
    private Logger LOGGER;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private MessageSource cMessageSource;
    private TaskExecutor taskExecutor;

    @Autowired
    public PersonController(MessageSource cMessageSource, UserService userService,
                            PasswordEncoder passwordEncoder, TaskExecutor taskExecutor) {

        this.cMessageSource = cMessageSource;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.taskExecutor = taskExecutor;
    }

    public PersonController() {}

    @GetMapping(value = {"/person/{uid}", "/person"})
    public String getPersonPage(@PathVariable(required = false) final String uid) {
        return "person";
    }

    @PostMapping(value ="/commonPerson", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAccountInformation(final HttpServletRequest request) {
        try {
            final User user = userService.findByNick(request.getUserPrincipal().getName());
            return new ResponseEntity<>(new CommonAccountUserDto().convertToDto(user), HttpStatus.OK);
        } catch (ServiceException e) {
            LOGGER.debug(e.getMessage());
            return new ResponseEntity<>(cMessageSource.getMessage("server.error",null, request.getLocale()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DtoException e) {
            LOGGER.debug(e.getMessage());
            return new ResponseEntity<>(cMessageSource.getMessage("user.exist.error",null, request.getLocale()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/commonPerson/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAccountInformation(@PathVariable(required = false) final String uid, final HttpServletRequest request) {
        if(uid == null)
            return new ResponseEntity<>(cMessageSource.getMessage("request.error",null, request.getLocale()), HttpStatus.BAD_REQUEST);
        if(uid.length() == 12) {
            try {
                final User user = userService.findByUid(uid);
                return new ResponseEntity<>(new CommonAccountUserDto().convertToDto(user), HttpStatus.OK);
            }catch (ServiceException e) {
                LOGGER.debug(e.getMessage());
                return new ResponseEntity<>(cMessageSource.getMessage("server.error",null, request.getLocale()), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (DtoException e) {
                LOGGER.debug(e.getMessage());
                return new ResponseEntity<>(cMessageSource.getMessage("user.exist.error",null, request.getLocale()), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(cMessageSource.getMessage("request.error",null, request.getLocale()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/changeAvatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> uploadImage(@RequestPart("image") final MultipartFile image,
                                                         final HttpServletRequest request){
        try {
            final User user = userService.findByNick(request.getUserPrincipal().getName());
            final ImageFactory imageFactory = new ScarlFactory();
            final MultiPartConverter multiPartConverter = imageFactory.getConverter();
            Avatar avatar = new Avatar();
            avatar.setUrl(UUID.randomUUID().toString() + ".png");
            avatar.setImage(multiPartConverter.convert(image, 150));
            userService.changeAvatar(user, avatar);
        } catch (ServiceException | UtilException e) {
            LOGGER.debug(e.getMessage());
            return new ResponseEntity<>(cMessageSource
                    .getMessage("avatar.save.error", null, request.getLocale()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cMessageSource
                .getMessage("avatar.save.success", null, request.getLocale()),HttpStatus.OK);
    }

    @PostMapping(value = "/deleteAvatar", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> deleteAvatar(final HttpServletRequest request) {
        try {
            final User user = userService.findByNick(request.getUserPrincipal().getName());
            userService.deleteAvatar(user);
            return new ResponseEntity<>(cMessageSource
                    .getMessage("avatar.delete", null, request.getLocale()), HttpStatus.OK);
        } catch(ServiceException e) {
            LOGGER.debug(e.getMessage());
            return new ResponseEntity<>(cMessageSource.getMessage("server.error",null, request.getLocale()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/changeInformation", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> changeNick(@ModelAttribute @Valid ChangeUserInformationDto changeUserInformationDto,
                                                   final BindingResult br, final HttpServletRequest request) {
        try {
            if (br.hasErrors()) {
                return new ResponseEntity<>(cMessageSource.getMessage("all_fields.error",null, request.getLocale())
                        , HttpStatus.OK);
            }
            final User user = userService.findByNick(request.getUserPrincipal().getName());
            if (user == null) {
                return new ResponseEntity<>(cMessageSource.getMessage("authorization.error",null, request.getLocale()), HttpStatus.UNAUTHORIZED);
            }
            if (!passwordEncoder.matches(changeUserInformationDto.getPassword(), user.getPassword())) {
                return new ResponseEntity<>(cMessageSource.getMessage("information.change.error",null, request.getLocale()), HttpStatus.OK);
            }

            taskExecutor.execute(() -> userService.save(changeUserInformationDto.change(user)));

            return new ResponseEntity<>(cMessageSource.getMessage("information.change.successes",null, request.getLocale()), HttpStatus.OK);
        } catch (ServiceException e) {
            LOGGER.debug(e.getMessage());
            return new ResponseEntity<>(cMessageSource.getMessage("server.error",null, request.getLocale()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
