package de.adorsys.lockpersistence.jpa.mapper;

import de.adorsys.lockpersistence.jpa.entity.LockEntity;
import de.adorsys.lockpersistence.model.Lock;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class LockEntityMapper {

    public Iterable<Lock> mapFromEntities(Iterable<LockEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::mapFromEntity)
                .collect(Collectors.toList());
    }

    public LockEntity mapToEntity(Lock lock) {
        LockEntity entity = new LockEntity();

        mapIntoEntity(lock, entity);

        return entity;
    }

    public void mapIntoEntity(Lock lock, LockEntity entity) {
        entity.setName(lock.getName());
        entity.setExpires(lock.getExpires());
        entity.setValue(lock.getValue());
    }

    public Lock mapFromEntity(LockEntity entity) {
        Lock lock = new Lock();

        lock.setName(entity.getName());
        lock.setValue(entity.getValue());
        lock.setExpires(entity.getExpires());

        return lock;
    }
}
