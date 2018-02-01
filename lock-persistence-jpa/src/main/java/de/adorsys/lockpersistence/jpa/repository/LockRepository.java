package de.adorsys.lockpersistence.jpa.repository;

import de.adorsys.lockpersistence.jpa.entity.LockEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockRepository extends CrudRepository<LockEntity, Long> {

    LockEntity findByName(String name);

    LockEntity findByNameAndValue(String name, String value);

    void deleteByName(String name);
}
