package com.igniteuhc.api.backend.backends.mongo;

import com.google.common.collect.Iterables;
import com.igniteuhc.api.backend.AbstractBackend;
import com.igniteuhc.api.backend.data.DataValueType;
import com.igniteuhc.api.backend.data.IData;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.bukkit.configuration.file.YamlConfiguration;

public class MongoBackend extends AbstractBackend{
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> documentCollection;

    public MongoBackend() {
        super("mongo.yml");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public MongoCollection<Document> getDocumentCollection() {
        return documentCollection;
    }

    @Override
    public void load(YamlConfiguration configuration){
        //No USR/PASS?
        String username = configuration.getString("user.name");
        String password = configuration.getString("user.pass");
        String host = configuration.getString("database.host");
        int port = configuration.getInt("host.port");
        String database = configuration.getString("database.name");
        String prefix = configuration.getString("database.tableprefix");
        mongoClient = new MongoClient(host, port);
        mongoDatabase = mongoClient.getDatabase(database);
        MongoIterable<String> iterable = mongoDatabase.listCollectionNames();
        if(!Iterables.contains(iterable, prefix + "_players")){
            mongoDatabase.createCollection(prefix + "_players");
        }
        documentCollection = mongoDatabase.getCollection(prefix + "_players");
    }

    @Override
    public IData load(DataValueType type, Object o) {
        return new MongoData(this, o);
    }

    public void close(){
        getMongoClient().close();
    }
}
