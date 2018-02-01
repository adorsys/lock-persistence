package de.adorsys.lockpersistence.service;

import de.adorsys.lockpersistence.model.Lock;
import java.util.Optional;

public interface LockPersistenceRepository {

    Iterable<Lock> getAll();

    Optional<Lock> get(String name);

    void create(Lock lock);

    void delete(String name);

    void update(Lock lock);
}
