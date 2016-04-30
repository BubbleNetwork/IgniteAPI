package com.igniteuhc.api.backend.data;

import java.util.ArrayList;

public class DataValue {
    public static final DataValue
            UUID = new DataValue("uuid",null,DataValueType.STRING),
            //DEATHLOCATION = new DataValue("locs.death", IgniteUHC.getPlugin(IgniteUHC.class).getSpawn(), DataValueType.LOCATION),
            //LASTLOCATION = new DataValue("locs.last", IgniteUHC.getPlugin(IgniteUHC.class).getSpawn(), DataValueType.LOCATION),
            RANK = new DataValue("rank", "default", DataValueType.STRING),
            MUTED = new DataValue("muted.status", false, DataValueType.BOOLEAN),
            MUTEREASON = new DataValue("muted.reason", "The ducktape has spoken!", DataValueType.STRING),
            MUTEDBY = new DataValue("muted.by","Console", DataValueType.STRING),
            MUTETIME = new DataValue("muted.time", -1, DataValueType.LONG),
            BANNED = new DataValue("banned.status", false, DataValueType.BOOLEAN),
            BANREASON = new DataValue("banned.reason", "The ban hammer has spoken!", DataValueType.STRING),
            BANNEDBY = new DataValue("banned.by","Console",DataValueType.STRING),
            BANTIME = new DataValue("banned.time",-1, DataValueType.LONG),
            IGNORELIST = new DataValue("ignorelist", new ArrayList<>(), DataValueType.STRINGLIST),
            NAME = new DataValue("username", null, DataValueType.STRING),
            IP = new DataValue("ip", null, DataValueType.STRING),
            FIRSTJOINED = new DataValue("firstjoined", null, DataValueType.LONG),
            LASTLOGIN = new DataValue("lastlogin", null, DataValueType.LONG),
            LASTLOGOUT = new DataValue("lastlogout", -1L, DataValueType.LONG)
    ;

    private final String name;
    private final Object defaultvalue;
    private final DataValueType type;

    public DataValue(String name, Object defaultvalue, DataValueType type) {
        this.name = name;
        this.defaultvalue = defaultvalue;
        this.type = type;
        if(defaultvalue != null && !getType().getDataClass().isInstance(defaultvalue))throw new IllegalArgumentException("Default value not of type");
    }

    public String getName() {
        return name;
    }

    public DataValueType getType() {
        return type;
    }

    public Object getDefaultvalue() {
        return defaultvalue;
    }
}
