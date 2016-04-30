package com.igniteuhc.api.player;
 
import java.util.*;

import com.igniteuhc.api.plugin.IgniteAPI;
import com.igniteuhc.api.backend.data.DataValue;
import com.igniteuhc.api.backend.data.DataValueType;
import com.igniteuhc.api.backend.data.IData;
import com.igniteuhc.api.game.GameManager;
import com.igniteuhc.api.game.GameState;
import com.igniteuhc.api.utils.DateUtils;
import com.igniteuhc.api.utils.PlayerUtils;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import com.igniteuhc.api.gui.GUIManager;
import com.igniteuhc.api.gui.guis.GameInfoGUI;
import com.igniteuhc.api.managers.PermissionsManager;

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
public class IgnitePlayer {
	private final Player player;

	private final IgniteAPI api;
	private final IData data;
	
	/**
	 * Constuctor for player data.
	 * <p>
	 * This will set up the data for the player and create missing data.
	 */
	protected IgnitePlayer(IgniteAPI api, GameManager game, GUIManager gui, PermissionsManager perm, Player player, IData data) {
		this.data = data;
        this.player = player;

		this.api = api;
		this.game = game;
        
		this.gui = gui;
		this.perm = perm;
	}
    
    private boolean creating = false;
	
	/**
	 * Get the given player's ping.
	 * 
	 * @return the players ping
	 */
	public int getPing() {
		final CraftPlayer craft = (CraftPlayer) player;
		
		return craft.getHandle().ping;
	} 
	
	/**
	 * Check if the user hasn't been welcomed to the server.
	 * 
	 * @return True if he hasn't, false otherwise.
	 */
	public boolean isNew() {
		return creating;
	}
	
	/**
	 * Get the player class for the user.
	 * 
	 * @return The player class.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Set the rank for the player.
	 * 
	 * @param rank The new rank.
	 */
	public void setRank(Rank rank) {
		getData().store(DataValue.RANK, rank.name());
		
		GameInfoGUI info = gui.getGUI(GameInfoGUI.class);
		
		info.updateStaff();
		
		if (player != null) {
			perm.removePermissions(player);
			perm.addPermissions(player);
		}
	}

	/**
	 * Get the rank of the player.
	 * 
	 * @return the rank.
	 */
	public Rank getRank() {
		Rank rank;
		
		try {
			rank = Rank.valueOf((String)getData().get(DataValue.RANK));
		} catch (Exception e) {
			rank = Rank.DEFAULT;
		}
		
		return rank;
	}

	/**
	 * Get the color of the rank the player has.
	 * 
	 * @return The rank color.
	 */
	public String getRankColor() {
		return getBukkitRankColor().toString();
	}

	public ChatColor getBukkitRankColor(){
		switch (getRank()){
			case DONATOR:
				return ChatColor.GREEN;
			case HOST:
			case OWNER:
				return ChatColor.DARK_RED;
			case TRIAL:
				return ChatColor.DARK_AQUA;
			case STAFF:
				return ChatColor.RED;
			default:
				return ChatColor.GRAY;
		}
	}

	public UUID getUniqueId(){
		return UUID.fromString((String)getData().get(DataValue.UUID));
	}
	
	/**
	 * Get all possible alt accounts of the user.
	 * <p>
	 * These are colored: Red = Banned, Green = Online and Gray = Offline.
	 * 
	 * @return A list of alt accounts.
	 */
	public Set<String> getAlts() {
		Set<String> altList = new HashSet<String>();

		//TODO - Global IData set
		/*
		String thisName = config.getString("username", "none1");
		String thisIP = config.getString("ip", "none1");
		
		BanList banlist = Bukkit.getBanList(Type.NAME);
		
		for (FileConfiguration file : FileUtils.getUserFiles()) {
			String name = file.getString("username", "none2");
			String IP = file.getString("ip", "none2");
			
			if (!thisIP.equals(IP)) {
				continue;
			}
			
			if (thisName.equals(name)) {
				continue;
			}
			
			Player check = Bukkit.getPlayerExact(name);
			
			if (banlist.getBanEntry(player.getName()) != null) {
				altList.add("§4" + name + "§8");
			}
			else if (check != null) {
				altList.add("§a" + name + "§8");
			} 
			else {
				altList.add("§c" + name + "§8");
			}
		}
		*/
		
		return altList;
	}
	
	/**
	 * Set the death location of the player.
	 * 
	 * @param loc The death Location
	 */
	public void setDeathLoc(Location loc) {
		getData().store(DataValue.DEATHLOCATION, loc);
	}
	
	/**
	 * Get the death location of the player.
	 * 
	 * @return The death location.
	 */
	public Location getDeathLoc() {
		try{
			return (Location) getData().get(DataValue.DEATHLOCATION);
		}
		catch (Exception ex){
			return null;
		}
	}
	
	/**
	 * Set the last location of the player.
	 * 
	 * @param loc The last location.
	 */
	public void setLastLoc(Location loc) {
		getData().store(DataValue.LASTLOCATION, loc);
	}
	
	/**
	 * Get the last location of the player.
	 * 
	 * @return The last location.
	 */
	public Location getLastLoc() {
		try{
			return (Location) getData().get(DataValue.LASTLOCATION);
		}
		catch (Exception ex){
			return null;
		}
	}
	
	/**
	 * Start ignoring the given player.
	 * 
	 * @param player The player to ignore
	 */
	public void ignore(Player player) {
		List<String> ignoreList = getIgnoreList();
		ignoreList.add(player.getUniqueId().toString());

		getData().store(DataValue.IGNORELIST, ignoreList);

		if(getPlayer() != null){
			getPlayer().sendMessage(plugin.getMessageManager().getMessage("now-ignoring", "name", player.getName()));
		}
	}

	/**
	 * Stop ignoring the given player.
	 * 
	 * @param player The player to stop ignoring
	 */
	public void unIgnore(Player player) {
		List<String> ignoreList = getIgnoreList();
		ignoreList.remove(player.getUniqueId().toString());
		
		getData().store(DataValue.IGNORELIST, ignoreList);

		if(getPlayer() != null){
			getPlayer().sendMessage(plugin.getMessageManager().getMessage("no-longer-ignoring", "name", player.getName()));
		}
	}
	
	/**
	 * Check if the this User is ignoring the given player.
	 * 
	 * @param player The player checking.
	 * @return True if he is, false otherwise.
	 */
	public boolean isIgnoring(Player player) {
		if (getRank().getLevel() >= Rank.STAFF.getLevel()) {
			return false;
		}
		
		return getIgnoreList().contains(player.getUniqueId().toString());
	}

	public List<String> getIgnoreList(){
		return (List<String>)getData().get(DataValue.IGNORELIST);
	}

	public String getIGN(){
		return (String)getData().get(DataValue.NAME);
	}
	
	/**
	 * Mute the user.
	 * 
	 * @param reason The reason of the mute.
	 * @param unmute The date of unmute, null if permanent.
	 */
	public void mute(String reason, String by, Date unmute) {
		getData().store(DataValue.MUTED, true);
		if(reason != null)getData().store(DataValue.MUTEREASON, reason);
		if(by != null)getData().store(DataValue.MUTEDBY, by);
		if(unmute != null)getData().store(DataValue.MUTETIME, unmute.getTime());

		PlayerUtils.broadcast(plugin.getMessageManager().getMessage("now-muted-broadcast", "player", getIGN()).replace("%reason%", getMuteReason()).replace("%by%",by));

		if(getPlayer() != null){
			getPlayer().sendMessage(plugin.getMessageManager().getMessage("now-muted-1"));
			getPlayer().sendMessage(plugin.getMessageManager().getMessage("now-muted-2","time",getMuteExpiration() == null ? "never" : DateUtils.formatDateDiff(getMuteExpiration().getTime())));
			getPlayer().sendMessage(plugin.getMessageManager().getMessage("now-muted-3","reason", getMuteReason()));
		}
	}

	/**
	 * Unmute the user.
	 */
	public void unMute(String by) {
		unMuteSilent();

		PlayerUtils.broadcast(plugin.getMessageManager().getMessage("unmuted-broadcast","player",getIGN()).replace("%by%", by));

		if(getPlayer() != null){
			getPlayer().sendMessage(plugin.getMessageManager().getMessage("unmuted"));
		}
	}

	public void unMuteSilent(){
		getData().remove(DataValue.MUTED);
		getData().remove(DataValue.MUTEREASON);
		getData().remove(DataValue.MUTEDBY);
		getData().remove(DataValue.MUTETIME);
	}

	public void ban(String reason, String by, Date unban){
		if(getIP() != null) {
			BanList ipbans = Bukkit.getBanList(BanList.Type.IP);
			ipbans.addBan(getIP(),by,unban,reason);
		}
		getData().store(DataValue.BANNED, true);
		if(reason != null)getData().store(DataValue.BANREASON, reason);
		if(by != null)getData().store(DataValue.BANNEDBY, by);
		if(unban != null)getData().store(DataValue.BANTIME, unban.getTime());

		PlayerUtils.broadcast(plugin.getMessageManager().getMessage("ban-message","banned","banned").replace("%player%", getIGN()).replace("%by%", getBannedBy()).replace("%time%",unban == null ? "never" : DateUtils.formatDateDiff(unban.getTime())).replace("%reason%", getBanReason()));

		if(getPlayer() != null){
			getPlayer().kickPlayer(plugin.getMessageManager().getMessage("ban-kick","banned","banned").replace("%by%", getBannedBy()).replace("%expires%", unban == null ? "never" : DateUtils.formatDateDiff(unban.getTime()).replace("%reason%", getBanReason())));
		}
	}

	public void unBan(String by){
		if(getIP() != null) {
			BanList ipbans = Bukkit.getBanList(BanList.Type.IP);
			if (ipbans.isBanned(getIP())){
				ipbans.pardon(getIGN());
			}
		}
		PlayerUtils.broadcast(plugin.getMessageManager().getMessage("unban-message","player",getIGN()).replace("%by%", by));
	}

	public void unBanSilent(){
		getData().remove(DataValue.BANNED);
		getData().remove(DataValue.BANREASON);
		getData().remove(DataValue.BANNEDBY);
		getData().remove(DataValue.BANTIME);
	}

	public boolean isBanned(){
		if(getBanExpiration() != null && getBanExpiration().after(new Date())){
			unBanSilent();
			return false;
		}
		return (Boolean)getData().get(DataValue.BANNED);
	}

	public String getBanReason(){
		return (String)getData().get(DataValue.BANREASON);
	}

	public Date getBanExpiration(){
		long time = (Long)getData().get(DataValue.BANTIME);
		return time == -1 ? null : new Date(time);
	}

	public String getBannedBy(){
		return (String)getData().get(DataValue.BANNEDBY);
	}

	
	/**
	 * Check if the player is muted.
	 * 
	 * @return <code>true</code> if the player is muted, <code>false</code> otherwise.
	 */
	public boolean isMuted() {
		// if the mute isnt permanent (perm == -1) and their mute time experied, return false and unmute.
		if (getMuteExpiration() != null && getMuteExpiration().after(new Date())) {
			unMuteSilent();
			return false;
		} 
		
		return (Boolean)getData().get(DataValue.MUTED);
	}
	
	/**
	 * Get the reason the player is muted.
	 * 
	 * @return The reason of the mute, null if not muted.
	 */
	public String getMuteReason() {
		return (String)getData().get(DataValue.MUTEREASON);
	}
	
	/**
	 * Get the time in milliseconds for the unmute.
	 * 
	 * @return The unmute time.
	 */
	public Date getMuteExpiration() {
		final long unmute = (Long)getData().get(DataValue.MUTETIME);
		
		if (unmute == -1) {
			return null;
		}
		
		return new Date(unmute);
	}
	
	/**
	 * Set the given stat to a new value
	 * 
	 * @param stat The stat setting.
	 * @param value The new value.
	 */
	public void setStat(Stat stat, double value) {
		if (game.isPrivateGame()) {
			return;
		}
		
		if (stat == Stat.ARENADEATHS || stat == Stat.ARENAKILLSTREAK || stat == Stat.ARENAKILLS) {
			if (!Bukkit.hasWhitelist()) {
				data.store(stat.getType(), value);
			}
		} else {
			if (GameState.isState(GameState.INGAME)) {
				data.store(stat.getType(), value);
			}
		}
	}
	
	/**
	 * Increase the given stat by 1.
	 * 
	 * @param stat the stat increasing.
	 */
	public void increaseStat(Stat stat) {
		if (game.isPrivateGame()) {
			return;
		}

		double current = getStat(stat);
		setStat(stat, current+1);
	}
	
	/**
	 * Get the amount from the given stat as a int.
	 * 
	 * @param stat the stat getting.
	 * @return The amount in a int form.
	 */
	public int getStat(Stat stat) {
		return (int)getStatDouble(stat);
	}
	
	/**
	 * Get the amount from the given stat as a double.
	 * 
	 * @param stat the stat getting.
	 * @return The amount in a double form.
	 */
	public double getStatDouble(Stat stat) {
		return (Double)getData().get(stat.getType());
	}
	
	/**
	 * Reset the players health, food, xp, inventory and effects.
	 */
	public void reset() {
        resetHealth();
        resetFood();
        resetExp();
        resetInventory();
        resetEffects();
    }

	/**
	 * Reset the players effects.
	 */
    public void resetEffects() {
        Collection<PotionEffect> effects = player.getActivePotionEffects();

        for (PotionEffect effect : effects) {
            player.removePotionEffect(effect.getType());
        }
    }

	/**
	 * Reset the players health.
	 */
    public void resetHealth() {
        player.setHealth(player.getMaxHealth());
    }

	/**
	 * Reset the players food.
	 */
    public void resetFood() {
        player.setSaturation(5.0F);
        player.setExhaustion(0F);
        player.setFoodLevel(20);
    }

	/**
	 * Reset the players xp.
	 */
    public void resetExp() {
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0F);
    }

	/**
	 * Reset the players inventory.
	 */
    public void resetInventory() {
    	final PlayerInventory inv = player.getInventory();

        inv.clear();
        inv.setArmorContents(null);
        player.setItemOnCursor(new ItemStack(Material.AIR));

        final InventoryView openInventory = player.getOpenInventory();
        
        if (openInventory.getType() == InventoryType.CRAFTING) {
            openInventory.getTopInventory().clear();
        }
    }

	public String getIP(){
		return (String)getData().get(DataValue.IP);
	}

	public void setIP(String ip){
		getData().store(DataValue.IP, ip);
	}

	public long getFirstJoined(){
		return (Long)getData().get(DataValue.FIRSTJOINED);
	}

	public void setFirstJoined(long firstJoined){
		getData().store(DataValue.FIRSTJOINED, firstJoined);
	}

	public long getLastLogin(){
		return (Long)getData().get(DataValue.LASTLOGIN);
	}

	public void setLastLogin(long lastLogin){
		getData().store(DataValue.LASTLOGIN, lastLogin);
	}

	public long getLastLogout(){
		return (Long)getData().get(DataValue.LASTLOGOUT);
	}

	public void setLastLogout(long lastLogout){
		getData().store(DataValue.LASTLOGOUT, lastLogout);
	}
	
    /**
     * The ranking enum class.
     */
    public enum Rank {
    	DEFAULT(1), DONATOR(2), SPEC(3), STAFF(4), TRIAL(5), HOST(6), OWNER(7);
    	
    	int level;
    	
    	private Rank(final int level) {
    		this.level = level;
    	}
    	
    	/**
    	 * Get the level of the rank.
    	 * <p>
    	 * It goes in order from 1 to 7 with 7 being the highest rank and 1 being the lowest.
    	 * 
    	 * @return The level.
    	 */
    	public int getLevel() {
    		return level;
    	}
    }


	public enum Hotbar{
		SWORD("Sword", 0),
		BOW("Bow", 1),
		BUCKET("Bucket", 2),
		PICKAXE("Pickaxe", 3),
		COBBLE("Cobble", 4),
		GAPPLE("Gapple", 5),
		SHOVEL("Shovel", 6),
		AXE("Axe", 7),
		FOOD("Food", 8)
		;

		private String name;
		private final DataValue type;

		private Hotbar(final String name, int slot) {
			this.name = name;
			type = new DataValue("hotar." + getName().toLowerCase(), slot, DataValueType.INT);
		}

		public String getName() {
			return name;
		}

		public Hotbar getHotbar(final String stat) {
			try {
				return valueOf(stat);
			} catch (Exception e) {
				for (Hotbar stats : values()) {
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

	public IData getData(){
		return data;
	}

	public int getHotbarItem(Hotbar hotbar){
		return (Integer) getData().get(hotbar.getType());
	}

	@Override
	public boolean equals(Object o){
		return o instanceof IgnitePlayer && ((IgnitePlayer)o).getUniqueId() == getUniqueId();
	}
}