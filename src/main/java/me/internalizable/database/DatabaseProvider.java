package me.internalizable.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class DatabaseProvider {
    private final Datastore datastore;

    public DatabaseProvider() {
        datastore = Morphia.createDatastore(MongoClients.create());
    }

    public Datastore getDatastore() {
        return datastore;
    }
}