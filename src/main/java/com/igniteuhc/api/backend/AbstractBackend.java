package com.igniteuhc.api.backend;

import com.igniteuhc.api.backend.data.DataValueType;
import com.igniteuhc.api.backend.data.IData;
import com.igniteuhc.pregenner.Main;
import com.igniteuhc.api.backend.resource.ResourceLoader;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;

public abstract class AbstractBackend extends ResourceLoader{
    public AbstractBackend(String internalfile) {
        super(internalfile);
    }

    public abstract IData load(DataValueType type,Object o);

    public void close(){

    }

}
