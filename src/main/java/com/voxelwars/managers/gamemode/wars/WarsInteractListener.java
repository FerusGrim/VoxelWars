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

package com.voxelwars.managers.gamemode.wars;

import com.voxelwars.managers.clan.Clan;
import com.voxelwars.managers.clan.ClanRole;
import com.voxelwars.utils.Message;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;

import java.util.Iterator;

public class WarsInteractListener extends WarsModule implements Listener {

    public WarsInteractListener(WarsGame game) {
        super(game, WarsGame.MODULE_WARS_LISTENER);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Block block = event.getClickedBlock();
        final Player player = event.getPlayer();
        final Clan clan = this.getPlugin().getClanManager().getClan(block.getChunk());

        if (clan == null) {
            handleInteract(event);
            return;
        }

        //TODO: Check if Player is in a Trusted clan.

        if (clan.getRole(player.getUniqueId()) == ClanRole.NOT_JOINED) {
            Message.failure((CommandSender) player, "Clans", "You cannot interact with " + ChatColor.GREEN
                + StringUtils.capitalize(block.getType().toString().replace("_", " ")) + ChatColor.GRAY + " in "
                + ChatColor.YELLOW + clan.getName() + ChatColor.GRAY + " territory.");
            event.setCancelled(true);
            return;
        }

        handleInteract(event);
    }

    private void handleInteract(PlayerInteractEvent event) {
        switch (event.getClickedBlock().getType()) {
            case INK_SACK:
            case ENCHANTMENT_TABLE:
            case BREWING_STAND:
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            switch (block.getType()) {
                case IRON_DOOR:
                    block.setType(Material.WOODEN_DOOR);
                case SMOOTH_BRICK:
                    block.getState().setData(new MaterialData(Material.SMOOTH_BRICK, (byte) 1));
            }
        }
    }

}
