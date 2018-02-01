package de.adorsys.lockpersistence.client;

import de.adorsys.lockpersistence.model.Lock;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class LockClientTemplate implements LockClient {
    protected Logger logger = Logger.getLogger(getClass().getName());

    private String applicationName;

    private Map<String, String> ownedLocks = new ConcurrentHashMap<>();

    public LockClientTemplate(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public void executeIfOwned(Runnable runnable) {
        executeIfOwned(applicationName, runnable);
    }

    @Override
    public void executeIfOwned(String lockKey, Runnable runnable) {
        try {
            Lock lock = lookupLock(lockKey);
            if (isLockOwned(lock))
                runnable.run();
        } catch (Exception ex) {
            // TODO - Proper error logging based on exception type.
            logger.log(Level.ALL, "Error while refreshing lock " + lockKey + ". Message : "
                    + ex.getMessage());
        }
    }

    protected abstract Iterable<Lock> locks();

    private synchronized Lock lookupLock(String lockKey) {
        Iterator<Lock> iterator = locks().iterator();
        while (iterator.hasNext()) {
            Lock lock = iterator.next();

            if (lock.getName().equals(lockKey)) {
                if (lock.getExpires().getTime() - new Date().getTime() < 0)
                    releaseAndDestoreLock(lock);
                else if (isLockOwned(lock))
                    return refreshAndStoreLock(lock);
                else
                    return lock;
            }
        }

        return createAndStoreLock(lockKey);
    }

    protected abstract Lock create(String name);

    private Lock createAndStoreLock(String name) {
        Lock lock = create(name);

        ownedLocks.put(lock.getName(), lock.getValue());

        return lock;
    }

    private boolean isLockOwned(Lock lock) {
        String ownedLockValue = ownedLocks.get(lock.getName());

        return ownedLockValue != null && ownedLockValue.equals(lock.getValue());
    }

    protected abstract Lock refresh(String name, String value);

    private Lock refreshAndStoreLock(Lock lock) {
        Lock refreshedLock = refresh(lock.getName(), lock.getValue());

        ownedLocks.put(lock.getName(), lock.getValue());

        return refreshedLock;
    }

    protected abstract void release(String name, String value);

    private void releaseAndDestoreLock(Lock lock) {
        ownedLocks.remove(lock.getName());

        try {
            release(lock.getName(), lock.getValue());
        } catch (Exception ex) {
            // TODO - Proper error logging based on exception type.
            logger.log(Level.ALL, "Error while release lock " + lock.getName() + ". Message : "
                    + ex.getMessage());
        }
    }
}
