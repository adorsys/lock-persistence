# lock-persistence

## Usage in spring boot

1. Add maven dependency:

```
    <dependency>
        <groupId>de.adorsys.lock-persistence</groupId>
        <artifactId>lock-persistence-jpa</artifactId>
        <version>${lock-persistence.version}</version>
    </dependency>
    <dependency>
        <groupId>de.adorsys.lock-persistence</groupId>
        <artifactId>lock-persistence-core</artifactId>
        <version>${lock-persistence.version}</version>
    </dependency>
```

2. Add `@EnableJpaLockPersistence` annotation to your spring boot configuration class

3. Make sure your database contains following table definition named `lock_persistence` within default schema:

```
CREATE TABLE lock_persistence (
  id      SERIAL PRIMARY KEY,
  name    VARCHAR(256),
  value   VARCHAR(36),
  expires TIMESTAMP
);
```

(example for postgres)

4. Make sure you're providing a Bean for a `LockClient` instance:

```
@Bean
LockClient lockClient(LockService lockService) {
    return new SimpleLockClient("<your application's lock name>", lockService);
}
```

5. Use the injected `LockClient` instance in your code:

```
lockClient.executeIfOwned("<your concrete lock name>", new Runnable() {
    @Override
    public void run() {
        ... your stuff to be locked for concurrent access ...
    }
});
```
