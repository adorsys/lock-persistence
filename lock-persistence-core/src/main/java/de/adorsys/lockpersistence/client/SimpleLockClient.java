package de.adorsys.lockpersistence.client;

import de.adorsys.lockpersistence.model.Lock;
import de.adorsys.lockpersistence.service.LockService;

public class SimpleLockClient extends LockClientTemplate {

    private final LockService lockService;

    public SimpleLockClient(
            String applicationName,
            LockService lockService
    ) {
        super(applicationName);
        this.lockService = lockService;
    }

    @Override
    protected Lock create(String name) {
        return lockService.create(name);
    }

    @Override
    protected Iterable<Lock> locks() {
        return lockService.findAll();
    }

    @Override
    protected Lock refresh(String name, String value) {
        return lockService.refresh(name, value);
    }

    @Override
    protected void release(String name, String value) {
        lockService.release(name, value);
    }
}
