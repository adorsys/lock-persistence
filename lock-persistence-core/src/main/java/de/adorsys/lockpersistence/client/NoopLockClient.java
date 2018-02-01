package de.adorsys.lockpersistence.client;

public class NoopLockClient implements LockClient {

    @Override
    public void executeIfOwned(Runnable runnable) {
        runnable.run();
    }

    @Override
    public void executeIfOwned(String name, Runnable runnable) {
        runnable.run();
    }
}
