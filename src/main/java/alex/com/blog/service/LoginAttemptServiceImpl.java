package alex.com.blog.service;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private static final int MAX_ATTEMPT = 10;
    private LoadingCache<String, Integer> attemptsCache;
    private static final Lock lock = new ReentrantLock();

    public LoginAttemptServiceImpl() {
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            public Integer load(String id) {
                return 0;
            }
        });
    }


    @Override
    public void loginFailed(final String ip) {
        lock.lock();
        int attempts = 0;
        try {
            attempts = attemptsCache.get(ip);
            attempts++;
            attemptsCache.put(ip, attempts);
        } catch (ExecutionException e) {
            attempts = 0;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void loginSucceeded(final String ip) {
        lock.lock();
        try {
            attemptsCache.invalidate(ip);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isBlocked(String key) {
        lock.lock();
        try {
            return attemptsCache.get(key) > MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        } finally {
            lock.unlock();
        }
    }
}
