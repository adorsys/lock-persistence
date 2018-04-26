package de.adorysys.lockpersistence.mongo.config;

import de.adorsys.lockpersistence.service.LockPersistenceRepository;
import de.adorsys.lockpersistence.service.LockService;
import de.adorsys.lockpersistence.service.LockServiceTemplate;
import de.adorysys.lockpersistence.mongo.repository.MongoLockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = {
        "de.adorysys.lockpersistence.mongo",
})
@EnableMongoRepositories(basePackageClasses = MongoLockRepository.class)
public class MongoLockPersistenceConfiguration {

    @Bean
    LockService lockService(
            LockPersistenceRepository lockPersistenceRepository,
            @Value("${lock-persistence.lockExpiry:30000}") Long expiryInterval
    ) {
        return new LockServiceTemplate(lockPersistenceRepository, expiryInterval);
    }
}
