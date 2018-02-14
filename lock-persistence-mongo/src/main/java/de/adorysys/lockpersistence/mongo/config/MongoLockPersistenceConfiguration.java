package de.adorysys.lockpersistence.mongo.config;

import de.adorsys.lockpersistence.jpa.common.TransactionalLockPersistenceConfig;
import de.adorysys.lockpersistence.mongo.repository.MongoLockRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = {
        "de.adorysys.lockpersistence.mongo",
})
@EnableMongoRepositories(basePackageClasses = MongoLockRepository.class)
@Import(TransactionalLockPersistenceConfig.class)
public class MongoLockPersistenceConfiguration {
}
