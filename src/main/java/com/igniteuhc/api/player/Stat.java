package com.igniteuhc.api.player;

import com.igniteuhc.api.backend.data.DataValue;
import com.igniteuhc.api.backend.data.DataValueType;

/**
 * Copyright Statement
 * ----------------------
 * Copyright (C) IgniteUHC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Class information
 * ---------------------
 * Package: com.igniteuhc.uhc
 * Project: IgniteUHC
 *
 */
public enum Stat {

    WINS("Wins"),
    GAMESPLAYED("Games played"),
    KILLS("Kills"),
    DEATHS("Deaths"),
    DAMAGETAKEN("Damage taken"),
    ARENAKILLS("Arena Kills"),
    ARENADEATHS("Arena Deaths"),
    KILLSTREAK("Highest Killstreak"),
    ARENAKILLSTREAK("Highest Arena Killstreak"),
    GOLDENAPPLESEATEN("Golden Apples Eaten"),
    GOLDENHEADSEATEN("Golden Heads Eaten"),
    HORSESTAMED("Horses Tamed"),
    WOLVESTAMED("Wolves Tamed"),
    POTIONS("Potions Drunk"),
    NETHER("Went to Nether"),
    END("Went to The End"),
    DIAMONDS("Mined diamonds"),
    GOLD("Mined gold"),
    HOSTILEMOBKILLS("Killed a monster"),
    ANIMALKILLS("Killed an animal"),
    LONGESTSHOT("Longest Shot"),
    LEVELS("Levels Earned");

    private String name;
    private final DataValue type;

    private Stat(final String name) {
        this.name = name;
        type = new DataValue("stat." + getName().toLowerCase(), 0.0D, DataValueType.DOUBLE);
    }

    /**
     * Get the name of the stat.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    public Stat getStat(final String stat) {
        try {
            return valueOf(stat);
        } catch (Exception e) {
            for (Stat stats : values()) {
                if (stats.getName().startsWith(stat)) {
                    return stats;
                }
            }
        }

        return null;
    }

    public DataValue getType(){
        return type;
    }

}
