package de.adorsys.lockpersistence.jpa.transaction;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {
        "de.adorsys.lockpersistence.jpa.common",
})
@EnableTransactionManagement
public class TransactionalLockPersistenceConfig {
}
