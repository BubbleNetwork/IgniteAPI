package com.igniteuhc.api.backend.data;

import org.bukkit.Location;

import java.util.List;

public enum  DataValueType {
    BOOLEAN(boolean.class),STRING(String.class),INT(int.class),DOUBLE(double.class),FLOAT(float.class),LONG(long.class),LOCATION(Location.class),STRINGLIST(List.class);

    private Class<?> c;

    DataValueType(Class<?> c){
        this.c = c;
    }

    public Class getDataClass(){
        return c;
    }
}
