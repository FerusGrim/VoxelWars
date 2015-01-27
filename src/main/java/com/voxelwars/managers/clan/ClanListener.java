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

import com.voxelwars.managers.gamemode.GameType;
import com.voxelwars.utils.Message;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ClanListener extends ClanModule implements Listener {

    public ClanListener(ClanManager manager) {
        super(manager, ClanManager.MODULE_LISTENER);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Clan clan = this.getClanManager().getClan(event.getBlock().getChunk());

        if (clan == null) {
            return;
        }

        if (clan.getRole(event.getPlayer().getUniqueId()) == ClanRole.NOT_JOINED) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        Clan clan = this.getClanManager().getClan(event.getBlock().getChunk());

        if (clan == null) {
            return;
        }

        if (clan.getRole(event.getPlayer().getUniqueId()) == ClanRole.NOT_JOINED) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!this.getPlugin().getGameManager().getGame(GameType.WARS).isActiveWorld(player.getWorld().getName())) {
            return;
        }

        Chunk fromChunk = event.getFrom().getChunk();
        Chunk toChunk = event.getTo().getChunk();

        if (fromChunk == toChunk) {
            return;
        }

        Clan clanObj = this.getClanManager().getClan(toChunk);

        if (clanObj == null) {
            Message.chatFormat("C", "Territory: " + ChatColor.DARK_GREEN + "Wilderness");
            return;
        }

        Clan clan = this.getClanManager().getClan(player.getUniqueId());

        if (clan == null || clanObj.getRole(player.getUniqueId()) == ClanRole.NOT_JOINED) {
            Message.chatFormat("C", "Territory: " + clanObj.getName());
            return;
        }
        
        //TODO Methods for checking clan relations
    }
}
