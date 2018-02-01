package de.adorsys.lockpersistence.client;

public interface LockClient {

	void executeIfOwned(Runnable runnable);
	
	void executeIfOwned(String name, Runnable runnable);
}
