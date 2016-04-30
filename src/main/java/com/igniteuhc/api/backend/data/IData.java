package com.igniteuhc.api.backend.data;

public interface IData {
    Object getProfile();
    void store(DataValue dataValue, Object o);
    Object get(DataValue search);
    void remove(DataValue value);
    void saveCache();
}
