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

package com.voxelwars.managers.client;

import com.voxelwars.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientRanks extends ClientModule {

    private final Map<UUID, Rank> rankMap;

    public ClientRanks(ClientManager clients) {
        super(clients, ClientManager.MODULE_RANKS);

        this.rankMap = new HashMap<>();
    }

    @Override
    public void loadConfig() {

    }

    @Override
    public void saveConfig() {

    }

    public Rank getRank(UUID uuid) {
        if (this.rankMap.get(uuid) == null) {
            this.setRank(uuid, Rank.STANDARD);
        }

        return this.rankMap.get(uuid);
    }

    public Rank getRank(Player player) {
        return this.getRank(player.getUniqueId());
    }

    public Rank getRank(CommandSender sender) {
        if (sender instanceof Player) {
            return this.getRank(((Player) sender).getUniqueId());
        }

        if (sender instanceof ConsoleCommandSender) {
            return Rank.CONSOLE;
        }

        return Rank.BANNED;
    }

    public void setRank(UUID uuid, Rank rank) {
        this.rankMap.put(uuid, rank);
    }
}
