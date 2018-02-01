package de.adorsys.lockpersistence.jpa;

import de.adorsys.lockpersistence.jpa.entity.LockEntity;
import de.adorsys.lockpersistence.jpa.mapper.LockEntityMapper;
import de.adorsys.lockpersistence.jpa.repository.LockRepository;
import de.adorsys.lockpersistence.model.Lock;
import de.adorsys.lockpersistence.service.LockPersistenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DatabaseLockPersistenceRepository implements LockPersistenceRepository {

    private final LockRepository repository;
    private final LockEntityMapper mapper;

    @Autowired
    public DatabaseLockPersistenceRepository(
            LockRepository repository,
            LockEntityMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Iterable<Lock> getAll() {
        Iterable<LockEntity> all = repository.findAll();
        return mapper.mapFromEntities(all);
    }

    @Override
    public Optional<Lock> get(String name) {
        LockEntity foundLock = repository.findByName(name);

        if(foundLock != null) {
            Lock lock = mapper.mapFromEntity(foundLock);
            return Optional.of(lock);
        }

        return Optional.empty();
    }

    @Override
    public void create(Lock lock) {
        LockEntity lockToCreate = mapper.mapToEntity(lock);
        repository.save(lockToCreate);
    }

    @Override
    public void delete(String name) {
        repository.deleteByName(name);
    }

    @Override
    public void update(Lock lock) {
        LockEntity foundLock = repository.findByNameAndValue(lock.getName(), lock.getValue());

        foundLock.setExpires(lock.getExpires());
        repository.save(foundLock);
    }
}
