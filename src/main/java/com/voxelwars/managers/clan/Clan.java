/*
Copyright (c) 2014-2015 Nicholas Badger / VoxelWars

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package com.voxelwars.managers.clan;

import com.voxelwars.VoxelWars;
import com.voxelwars.utils.HandyLocation;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Clan {

    private final String name;
    private Location home;

    private final Map<UUID, ClanRole> memberMap;
    private final Set<String> claimSet;
    private final Map<UUID, Long> inviteMap;
    private final Map<UUID, Long> deserterMap;
    private final Set<Clan> allySet;
    private final Set<Clan> trustSet;
    private final Set<Clan> enemySet;

    public Clan(String name) {
        this.name = name;
        this.memberMap = new HashMap<>();
        this.claimSet = new HashSet<>();
        this.inviteMap = new HashMap<>();
        this.deserterMap = new HashMap<>();
        this.allySet = new HashSet<>();
        this.trustSet = new HashSet<>();
        this.enemySet = new HashSet<>();
    }

    public String getName() {
        return this.name;
    }

    public Location getHome() {
        return this.home;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public ClanRole getRole(UUID uuid) {
        ClanRole role = this.memberMap.get(uuid);

        if (role == null) {
            return ClanRole.NOT_JOINED;
        }

        return role;
    }

    public Set<UUID> getMemberSet() { return this.memberMap.keySet(); }

    public void setRole(UUID uuid, ClanRole role) {
        this.memberMap.put(uuid, role);
    }

    public void removeRole(UUID uuid) {
        this.memberMap.remove(uuid);
    }

    public boolean isClaim(Chunk claim) {
        return this.claimSet.contains(HandyLocation.chunkToString(claim));
    }

    public void addClaim(Chunk claim) {
        this.claimSet.add(HandyLocation.chunkToString(claim));
    }

    public void addClaim(String claim) {
        this.claimSet.add(claim);
    }

    public void removeClaim(Chunk claim) {
        this.claimSet.remove(HandyLocation.chunkToString(claim));
    }

    public Set<String> getClaimSet() {
        return claimSet;
    }

    public boolean canClaim(Chunk chunk) {
        return this.memberMap.size() + 3 < this.claimSet.size();
    }

    public void addInvite(UUID uuid) {
        this.inviteMap.put(uuid, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30));
    }

    public void removeInvite(UUID uuid) {
        this.inviteMap.remove(uuid);
    }

    public boolean isInvited(UUID uuid) {
        if (!inviteMap.containsKey(uuid)) {
            return false;
        }

        if (System.currentTimeMillis() >= this.inviteMap.get(uuid)) {
            this.inviteMap.remove(uuid);
            return false;
        }

        return true;
    }

    public String getMembers(VoxelWars plugin) {
        String members = "";
        for (UUID uuid : this.memberMap.keySet()) {
            OfflinePlayer player = plugin.getServer().getOfflinePlayer(uuid);
            switch(this.memberMap.get(uuid)) {
                case RECRUIT:
                    if (player.isOnline()) {
                        members = members + ChatColor.GREEN + "R." + player.getName() + ChatColor.GRAY + ", ";
                    } else {
                        members = members + ChatColor.RED + "R." + player.getName() + ChatColor.GRAY + ", ";
                    }
                case MEMBER:
                    if (player.isOnline()) {
                        members = members + ChatColor.GREEN + "M." + player.getName() + ChatColor.GRAY + ", ";
                    } else {
                        members = members + ChatColor.RED + "M." + player.getName() + ChatColor.GRAY + ", ";
                    }
                case ELDER:
                    if (player.isOnline()) {
                        members = members + ChatColor.GREEN + "E." + player.getName() + ChatColor.GRAY + ", ";
                    } else {
                        members = members + ChatColor.RED + "E." + player.getName() + ChatColor.GRAY + ", ";
                    }
                case LEADER:
                    if (player.isOnline()) {
                        members = members + ChatColor.GREEN + "L." + player.getName() + ChatColor.GRAY + ", ";
                    } else {
                        members = members + ChatColor.RED + "L." + player.getName() + ChatColor.GRAY + ", ";
                }
            }
        }
        return members.substring(0, members.length() - 1);
    }

    public Set<Clan> getAllySet() { return this.allySet; }

    public void ally(Clan clan) {
        this.allySet.add(clan);
    }

    public void removeAlly(Clan clan) {
        this.allySet.remove(clan);
    }

    public boolean canAlly(Clan clan) {
        return clan.allySet.size() < (8 - clan.memberMap.keySet().size()) && (clan.canAlly(this));
    }

    public String getAllies() {
        String allies = "";
        for (Clan c : this.allySet) {
            allies = allies + c.getName() + ChatColor.GRAY + ", ";
        }
        return allies.length() > 1 ? allies.substring(0, allies.length() - 1) : allies;
    }

    public String getAllies(Clan clan) {
        String allies = "";
        for (Clan clanObj : clan.allySet) {
            if (this.equals(clanObj)) {
                allies = allies + ChatColor.DARK_AQUA + clanObj.getName() + ChatColor.GRAY + ", ";
            } else  if (this.allySet.contains(clanObj)) {
                allies = allies + ChatColor.GREEN + clanObj.getName() + ChatColor.GRAY + ", ";
            } else if (this.enemySet.contains(clanObj)) {
                allies = allies + ChatColor.RED + clanObj.getName() + ChatColor.GRAY + ", ";
            } else {
                allies = allies + ChatColor.YELLOW + clanObj.getName() + ChatColor.GRAY + ", ";
            }
        }
        return allies.substring(0, allies.length() - 1);
    }

    public String getEnemies() {
        String enemies = "";
        for (Clan c : this.enemySet) {
            enemies = enemies + c.getName() + ChatColor.GRAY + ", ";
        }
        return enemies.length() > 1 ? enemies.substring(0, enemies.length() - 1) : enemies;
    }

    public Set<Clan> getEnemySet() { return this.enemySet; }

    public String getEnemies(Clan clan) {
        String enemies = "";
        for (Clan clanObj : clan.enemySet) {
            if (this.equals(clanObj)) {
                enemies = enemies + ChatColor.DARK_AQUA + clanObj.getName() + ChatColor.GRAY + ", ";
            } else  if (this.allySet.contains(clanObj)) {
                enemies = enemies + ChatColor.GREEN + clanObj.getName() + ChatColor.GRAY + ", ";
            } else if (this.enemySet.contains(clanObj)) {
                enemies = enemies + ChatColor.RED + clanObj.getName() + ChatColor.GRAY + ", ";
            } else {
                enemies = enemies + ChatColor.YELLOW + clanObj.getName() + ChatColor.GRAY + ", ";
            }
        }
        return enemies.length() > 1 ? enemies.substring(0, enemies.length() - 1) : enemies;
    }
}
