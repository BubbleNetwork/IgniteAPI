package com.igniteuhc.api.managers;

import com.igniteuhc.api.backend.AbstractBackend;
import com.igniteuhc.api.backend.backends.file.FileBackend;
import com.igniteuhc.api.backend.data.DataValueType;
import com.igniteuhc.api.backend.data.IData;
import org.bukkit.configuration.file.YamlConfiguration;

public class BackendManager extends AbstractBackend{
    private AbstractBackend chosen;

    public BackendManager() {
        super("backendconfig.yml");
    }

    public IData load(DataValueType type,Object id) {
        return chosen.load(type, id);
    }

    public void load(YamlConfiguration configuration) {
        String backend = configuration.getString("backend");
        if(backend.equalsIgnoreCase("file")){
            chosen = new FileBackend();
        }
        throw new IllegalArgumentException("Could not find the backend \'" + backend + "\'");
    }

    public void close(){
        chosen.close();
    }
}
