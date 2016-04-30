package com.igniteuhc.api.backend.resource;

import com.igniteuhc.pregenner.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;

public class ResourceLoader {
    private final String internalfile;
    private YamlConfiguration configuration;

    public ResourceLoader(String internalfile) {
        this.internalfile = internalfile;
        InputStream stream = Main.getPlugin(Main.class).getResource(internalfile);
        try{
            configuration = YamlConfiguration.loadConfiguration(stream);
        }
        finally {
            if(stream != null){
                try{
                    stream.close();
                }
                catch (Exception ex){
                }
            }
        }
        load(configuration);
    }

    public void load(YamlConfiguration configuration){
    }
}
