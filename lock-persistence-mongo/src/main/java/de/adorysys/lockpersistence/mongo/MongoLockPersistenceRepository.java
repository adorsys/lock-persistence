package de.adorysys.lockpersistence.mongo;

import de.adorsys.lockpersistence.model.Lock;
import de.adorsys.lockpersistence.service.LockPersistenceRepository;
import de.adorysys.lockpersistence.mongo.entity.LockEntity;
import de.adorysys.lockpersistence.mongo.mapper.LockEntityMapper;
import de.adorysys.lockpersistence.mongo.repository.MongoLockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MongoLockPersistenceRepository implements LockPersistenceRepository {

    private final MongoLockRepository repository;
    private final LockEntityMapper entityMapper;

    @Autowired
    public MongoLockPersistenceRepository(
            MongoLockRepository repository,
            LockEntityMapper entityMapper
    ) {
        this.repository = repository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Iterable<Lock> getAll() {
        List<LockEntity> allFound = repository.findAll();
        return entityMapper.mapFromEntities(allFound);
    }

    @Override
    public Optional<Lock> get(String name) {
        LockEntity foundLock = repository.findByName(name);

        if(foundLock == null) {
            return Optional.empty();
        }

        return Optional.of(entityMapper.mapFromEntity(foundLock));
    }

    @Override
    public void create(Lock lock) {
        repository.save(entityMapper.mapToEntity(lock));
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
