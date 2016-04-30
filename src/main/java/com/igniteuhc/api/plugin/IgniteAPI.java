package com.igniteuhc.api.plugin;

import lombok.Getter;

import com.igniteuhc.api.managers.BackendManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class IgniteAPI<IgnitePlugin> extends JavaPlugin {

    @Getter
    private static IgniteAPI<?> instance;

    @Getter
    private BackendManager backendManager;

    public IgniteAPI() {
        instance = this;
    }

    public final void onEnable() {

        //setup backend
        backendManager = new BackendManager();

        

    }

    public final void onDisable() {

    }

    public abstract void onIgniteEnable();

    public abstract void onIgniteDisable();

    public abstract void onIgniteLoad();
}
