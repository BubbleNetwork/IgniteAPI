package com.igniteuhc.api.backend.backends.mongo;

import com.igniteuhc.api.backend.data.DataValue;
import com.igniteuhc.api.backend.data.IData;
import com.mongodb.BasicDBObject;

public class MongoData implements IData{
    private MongoBackend backend;
    private BasicDBObject object;

    public MongoData(MongoBackend backend, Object o){
        this.backend = backend;
        object = new BasicDBObject("key",o);
    }

    public Object getProfile() {
        return object.getString("key");
    }

    public void store(DataValue dataValue, Object o) {
        object.put(dataValue.getName(), o);
    }

    public Object get(DataValue search) {
        return object.containsKey(search.getName()) ? object.get(search.getName()) : search.getDefaultvalue();
    }

    public void remove(DataValue value) {
        object.remove(value.getName());
    }

    public void saveCache() {
        backend.getDocumentCollection().findOneAndUpdate(object, object);
    }
}
