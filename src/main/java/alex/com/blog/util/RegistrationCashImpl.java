package alex.com.blog.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class RegistrationCashImpl implements RegistrationCash {

    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private LoadingCache<String, Integer> attemptsCache;
    private static final int MAX_ATTEMPT = 1;

    public RegistrationCashImpl() {
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            public Integer load(String ip) {
                return 0;
            }
        });
    }

    @Override
    public void push(final String ip) {
        readWriteLock.writeLock().lock();
        int attempts = 0;
        try {
            attempts = attemptsCache.get(ip);
            attempts++;
            attemptsCache.put(ip, attempts);
        } catch (ExecutionException e) {
            attempts = 0;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean isBlocked(final String ip) {
        readWriteLock.readLock().lock();
        try {
            return attemptsCache.get(ip) > MAX_ATTEMPT;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return false;
    }

}
