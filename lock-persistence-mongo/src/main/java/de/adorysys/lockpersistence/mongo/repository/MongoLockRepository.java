package de.adorysys.lockpersistence.mongo.repository;

import de.adorysys.lockpersistence.mongo.entity.LockEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoLockRepository extends MongoRepository<LockEntity, String> {
    LockEntity findByName(String name);

    LockEntity findByNameAndValue(String name, String value);

    void deleteByName(String name);
}
