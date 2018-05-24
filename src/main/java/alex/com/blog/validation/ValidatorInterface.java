package alex.com.blog.validation;

import javax.servlet.http.HttpServletRequest;

public interface ValidatorInterface {
    Object validate(Object o, HttpServletRequest request);
}
