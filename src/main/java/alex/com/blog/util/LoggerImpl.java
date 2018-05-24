package alex.com.blog.util;

import org.apache.log4j.Logger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoggerImpl implements alex.com.blog.util.Logger {

    private static final Lock lock = new ReentrantLock();
    private Logger LOGGER;


    @Override
    public void setLogger(final String className) {
        try {
            lock.lock();
            LOGGER = Logger.getLogger(className);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void debug(final String msg) {
        if(LOGGER != null) {
            LOGGER.debug(msg);
        }
    }

    @Override
    public void info(final String msg) {
        if(LOGGER != null) {
            LOGGER.info(msg);
        }
    }
}
