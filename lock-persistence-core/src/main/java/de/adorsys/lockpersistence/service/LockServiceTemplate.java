package de.adorsys.lockpersistence.service;

import de.adorsys.lockpersistence.exception.LockExistsException;
import de.adorsys.lockpersistence.exception.LockNotHeldException;
import de.adorsys.lockpersistence.model.Lock;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;


public class LockServiceTemplate implements LockService {

    private final LockPersistenceRepository repository;
    private final Long expiryInterval;

    public LockServiceTemplate(LockPersistenceRepository repository, Long expiryInterval) {
        this.repository = repository;
        this.expiryInterval = expiryInterval;
    }

    @Override
    public Iterable<Lock> findAll() {
        return repository.getAll();
    }

    @Override
    public Lock create(String name) throws LockExistsException {
        Optional<Lock> foundLock = repository.get(name);

        if (foundLock.isPresent()) {
            Lock lock = foundLock.get();

            if (lock.isExpired()) {
                repository.delete(name);
            } else {
                throw new LockExistsException();
            }
        }

        Lock newLock = new Lock(name, UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis() + expiryInterval));

        repository.create(newLock);

        return newLock;
    }

    @Override
    public void release(String name, String value) throws LockNotHeldException {
        Optional<Lock> foundLock = repository.get(name);

        if (foundLock.isPresent()) {
            Lock lock = foundLock.get();

            if (!lock.getValue().equals(value)) {
                throw new LockNotHeldException();
            }
            if (lock.isExpired()) {
                throw new LockNotHeldException();
            }

            repository.delete(name);
        }
    }

    @Override
    public Lock refresh(String name, String value) throws LockNotHeldException {
        Optional<Lock> foundLock = repository.get(name);

        if (foundLock.isPresent()) {
            Lock lock = foundLock.get();

            if (!lock.getValue().equals(value)) {
                throw new LockNotHeldException();
            }
            if (lock.isExpired()) {
                throw new LockNotHeldException();
            }

            lock.setExpires(new Date(System.currentTimeMillis() + expiryInterval));

            repository.update(lock);
            return lock;
        }

        throw new LockNotHeldException();
    }
}
