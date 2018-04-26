package de.adorsys.lockpersistence.jpa.transaction;

import de.adorsys.lockpersistence.exception.LockExistsException;
import de.adorsys.lockpersistence.exception.LockNotHeldException;
import de.adorsys.lockpersistence.model.Lock;
import de.adorsys.lockpersistence.service.LockPersistenceRepository;
import de.adorsys.lockpersistence.service.LockServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalLockService extends LockServiceTemplate {

    @Autowired
    public TransactionalLockService(
            LockPersistenceRepository repository,
            @Value("${lock-persistence.lockExpiry:30000}") Long expiryInterval
    ) {
        super(repository, expiryInterval);
    }

    @Override
    public Iterable<Lock> findAll() {
        return super.findAll();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Lock create(String name) throws LockExistsException {
        return super.create(name);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void release(String name, String value) throws LockNotHeldException {
        super.release(name, value);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Lock refresh(String name, String value) throws LockNotHeldException {
        return super.refresh(name, value);
    }
}
