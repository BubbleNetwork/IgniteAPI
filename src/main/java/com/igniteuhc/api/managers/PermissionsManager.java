 package com.igniteuhc.api.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.igniteuhc.api.player.IgnitePlayer;
import com.igniteuhc.api.plugin.IgniteAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.google.common.collect.ImmutableMap;
import com.igniteuhc.uhc.IgniteUHC;
import com.igniteuhc.uhc.User;
import com.igniteuhc.uhc.User.Rank;

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
public class PermissionsManager {
	private final IgniteAPI api;
	
	/**
	 * Permissions manager class constructor.
	 * 
	 * @param plugin The main class.
	 */
	public PermissionsManager(IgniteAPI api) {
		this.api = api;
	}
	
	private final Map<UUID, PermissionAttachment> permissions = new HashMap<UUID, PermissionAttachment>();
	
	/**
	 * Get a copy of the permissions HashMap.
	 * 
	 * @return Copy of permissions HashMap.
	 */
	public Map<UUID, PermissionAttachment> getPermissions() {
		return ImmutableMap.copyOf(permissions);
	}
	
	/**
	 * Handle the permissions for the given player.
	 * 
	 * @param player the player.
	 */
	public void addPermissions(Player player) {
		if (!permissions.containsKey(player.getUniqueId())) {
			permissions.put(player.getUniqueId(), player.addAttachment(api));
		}

		PermissionAttachment perm = permissions.get(player.getUniqueId());

		User user = plugin.getUser(player);
		Rank rank = user.getRank();
		
		if (rank == Rank.OWNER) {
			player.setOp(true);
			return;
		}
		
		player.setOp(false);
		
		perm.setPermission("uhc.border", true);
		perm.setPermission("uhc.stats", true);
		perm.setPermission("uhc.top", true);
		perm.setPermission("uhc.team", true);
		perm.setPermission("uhc.msg", true);
		perm.setPermission("uhc.reply", true);
		perm.setPermission("uhc.tl", true);
		perm.setPermission("uhc.pm", true);
		perm.setPermission("uhc.pmores", true);
		perm.setPermission("uhc.pmminedores", true);
		perm.setPermission("uhc.health", true);
		perm.setPermission("uhc.ms", true);
		perm.setPermission("uhc.tps", true);
		perm.setPermission("uhc.uhc", true);
		perm.setPermission("uhc.hof", true);
		perm.setPermission("uhc.helpop", true);
		perm.setPermission("uhc.matchpost", true);
		perm.setPermission("uhc.scenario", true);
		perm.setPermission("uhc.ignore", true);
		perm.setPermission("uhc.timeleft", true);
		perm.setPermission("uhc.list", true);
		perm.setPermission("uhc.arena", true);
		perm.setPermission("uhc.hotbar", true);
		perm.setPermission("uhc.parkour", true);
		perm.setPermission("uhc.combatlog", true);
		
		// spectator perms, they can only use them if they're spectating.
		perm.setPermission("uhc.spectate", true);
		perm.setPermission("uhc.invsee", true);
		perm.setPermission("uhc.near", true);
		perm.setPermission("uhc.specchat", true);
		perm.setPermission("uhc.speed", true);
		perm.setPermission("uhc.tp", true);
		perm.setPermission("uhc.back", true);
		
		if (rank == Rank.DEFAULT) {
			return;
		}
		
		perm.setPermission("uhc.prelist", true);
		
		if (rank == Rank.SPEC) {
			return;
		}
		
		// adding donation features at at later date.
		
		if (rank == Rank.DONATOR) {
			return;
		}

		perm.setPermission("uhc.cantignore", true);
		perm.setPermission("uhc.ac", true);
		perm.setPermission("uhc.ban", true);
		perm.setPermission("uhc.dq", true);
		perm.setPermission("uhc.broadcast", true);
		perm.setPermission("uhc.fly", true);
		perm.setPermission("uhc.info", true);
		perm.setPermission("uhc.chat", true);
		perm.setPermission("uhc.respawn", true);
		perm.setPermission("uhc.kick", true);
		perm.setPermission("uhc.mute", true);
		perm.setPermission("uhc.cmdspy", true);
		perm.setPermission("uhc.staff", true);
		perm.setPermission("uhc.admin", true);
		perm.setPermission("uhc.team.admin", true);
		perm.setPermission("uhc.tempban", true);
		perm.setPermission("uhc.tp.bypass", true);
		perm.setPermission("uhc.whitelist", true);
		perm.setPermission("uhc.mod", true);
		
		if (rank == Rank.STAFF) {
			return;
		}

		perm.setPermission("uhc.give", true);
		perm.setPermission("uhc.tp.other", true);
		perm.setPermission("uhc.arena.admin", true);
		perm.setPermission("uhc.board", true);
		perm.setPermission("uhc.butcher", true);
		perm.setPermission("uhc.clearinv", true);
		perm.setPermission("uhc.clearxp", true);
		perm.setPermission("uhc.config", true);
		perm.setPermission("uhc.end", true);
		perm.setPermission("uhc.feed", true);
		perm.setPermission("uhc.restart", true);
		perm.setPermission("uhc.giveall", true);
		perm.setPermission("uhc.heal", true);
		perm.setPermission("uhc.pregen", true);
		perm.setPermission("uhc.random", true);
		perm.setPermission("uhc.scenario.manage", true);
		perm.setPermission("uhc.sethealth", true);
		perm.setPermission("uhc.setmaxhealth", true);
		perm.setPermission("uhc.start", true);
		perm.setPermission("uhc.spread", true);
		perm.setPermission("uhc.spectate.others", true);
		perm.setPermission("uhc.timer", true);
		perm.setPermission("uhc.vote", true);
		perm.setPermission("uhc.mysteryteams", true);
		perm.setPermission("uhc.bestbtc", true);
		perm.setPermission("uhc.bestpve", true);
		perm.setPermission("uhc.bigcrack", true);
		perm.setPermission("uhc.slimycrack", true);
		perm.setPermission("uhc.kings", true);
		perm.setPermission("uhc.damagedodgers", true);
		
		perm.setPermission("uhc.pvp", true);
		perm.setPermission("uhc.pregen", true);
		perm.setPermission("uhc.border.set", true);
		perm.setPermission("uhc.world", true);
		perm.setPermission("uhc.host", true);

	}
	
	/**
	 * Handle the permissions for the given player if he leaves.
	 * 
	 * @param player the player.
	 */
	public void removePermissions(Player player) {
		if (!permissions.containsKey(player.getUniqueId())) {
			return;
		}
		
		try {
			player.removeAttachment(permissions.get(player.getUniqueId()));
		} catch (Exception e) {
			Bukkit.getLogger().warning("Couldn't remove " + player.getName() + "'s permissions.");
		}
		
		permissions.remove(player.getUniqueId());
	}
}