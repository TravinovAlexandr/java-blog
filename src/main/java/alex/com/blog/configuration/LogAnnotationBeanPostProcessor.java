package alex.com.blog.configuration;

import alex.com.blog.annotations.Log;
import alex.com.blog.util.Logger;
import alex.com.blog.util.LoggerImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;

@Component
public class LogAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Field[] fields = o.getClass().getDeclaredFields();
        if(fields.length > 0) {
            for(Field field : fields) {
                if(field.isAnnotationPresent(Log.class) && field.getType() == Logger.class) {
                    try {
                        field.setAccessible(true);
                        Logger logger = new LoggerImpl();
                        logger.setLogger(o.getClass().getSimpleName());
                        field.set(o, logger);
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
