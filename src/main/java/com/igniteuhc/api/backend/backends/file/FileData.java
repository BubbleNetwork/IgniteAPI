package com.igniteuhc.api.backend.backends.file;

import com.igniteuhc.api.backend.data.DataValue;
import com.igniteuhc.api.backend.data.IData;
import com.igniteuhc.api.plugin.IgniteAPI;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileData implements IData {
    private static File DIR = IgniteAPI.getInstance().getDataFolder();

    private final File file;
    private String s;
    private YamlConfiguration loaded = new YamlConfiguration();
    private boolean cachedsync = true;

    public FileData(String s){
        this.s = s;
        file = new File(DIR,s + ".yml");
        if(file.exists()){
            try {
                loaded.load(file);
            } catch (Exception e) {
            }
        }
    }

    public String getProfile() {
        return s;
    }

    public void store(DataValue dataValue, Object o) {
        loaded.set(dataValue.getName(), o);
        cachedsync = false;
    }

    public void remove(DataValue value){
        if(loaded.contains(value.getName())){
            loaded.set(value.getName(), null);
            cachedsync = false;
        }
    }

    public Object get(DataValue search) {
        return loaded.get(search.getName(), search.getDefaultvalue());
    }

    public void saveCache() {
        if(!cachedsync) {
            if (!DIR.exists())
                DIR.mkdir();
            try {
                loaded.save(file);
                cachedsync = true;
            } catch (IOException e) {
            }
        }
    }

    public YamlConfiguration getLoaded() {
        return loaded;
    }
}
