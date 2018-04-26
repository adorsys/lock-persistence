package de.adorsys.lockpersistence.jpa.config;

import de.adorsys.lockpersistence.jpa.transaction.TransactionalLockPersistenceConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {
        "de.adorsys.lockpersistence.jpa",
})
@EnableTransactionManagement
@EnableJpaRepositories("de.adorsys.lockpersistence.jpa.repository")
@EntityScan(
        basePackages = "de.adorsys.lockpersistence.jpa.entity",
        basePackageClasses = {Jsr310JpaConverters.class}
)
@Import(TransactionalLockPersistenceConfig.class)
public class JpaLockPersistenceConfiguration {
}
