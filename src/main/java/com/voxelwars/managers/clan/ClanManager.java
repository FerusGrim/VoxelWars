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
import com.voxelwars.managers.Identifier;
import com.voxelwars.managers.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ClanManager extends Manager {

    private final Map<String, Clan> clanMap;
    private final Map<UUID, Long> deserterMap;

    public static final String MODULE_LISTENER = "clan_listener";
    public static final String MODULE_WARS = "clan_wars";

    public ClanManager(VoxelWars plugin) {
        super(plugin, Identifier.CLANS);
        this.clanMap = new HashMap<>();
        this.deserterMap = new HashMap<>();
    }

    @Override
    public void registerModules() {
        this.addModule(new ClanListener(this));
    }

    public ClanListener getListener() {
        return (ClanListener) this.getModule(MODULE_LISTENER);
    }

    public ClanWars getWars() { return (ClanWars) this.getModule(MODULE_WARS); }

    public Set<Clan> getClans() {
        return this.clanMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    public Map<String, Clan> getClanMap() {
        return this.clanMap;
    }

    public Clan getClan(String name) {
        return this.clanMap.get(name);
    }

    public Clan getClan(UUID uuid) {
        for (Clan clan : clanMap.values()) {
            if (clan.getRole(uuid) != ClanRole.NOT_JOINED) {
                return clan;
            }
        }
        return null;
    }

    public Clan getClan(Chunk chunk) {
        for (Clan clan : clanMap.values()) {
            if (clan.isClaim(chunk)) {
                return clan;
            }
        }
        return null;
    }

    public void addClan(Clan clan) {
        this.clanMap.put(clan.getName(), clan);
    }

    public void removeClan(String name) {
        this.clanMap.remove(name);
    }

    public Map<UUID, Long> getDeserterMap() {
        return this.deserterMap;
    }

    public void addDeserter(UUID uuid) {
        this.deserterMap.put(uuid, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30));
        //TODO: Make this rely on Domination. For now, auto-set to 30 minutes.
    }

    public void removeDeserter(UUID uuid) {
        this.deserterMap.remove(uuid);
    }

    public boolean isDeserter(UUID uuid) {
        if (!this.deserterMap.containsKey(uuid)) {
            return false;
        }

        if (System.currentTimeMillis() >= this.deserterMap.get(uuid)) {
            this.deserterMap.remove(uuid);
            return false;
        }

        return true;
    }

    public void inform(Player player, Clan c, String message, boolean informUser) {
        if (informUser) {
            c.getMemberSet().stream().filter(uuid -> this.getPlugin().getServer().getOfflinePlayer(uuid).isOnline()).forEach(uuid -> this.getPlugin().getServer().getPlayer(uuid).sendMessage(message));
            return;
        }
        c.getMemberSet().stream().filter(uuid -> this.getPlugin().getServer().getOfflinePlayer(uuid).isOnline()).filter(uuid -> !player.getUniqueId().equals(uuid)).forEach(uuid -> this.getPlugin().getServer().getPlayer(uuid).sendMessage(message));
    }
}
