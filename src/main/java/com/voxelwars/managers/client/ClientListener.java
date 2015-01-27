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
import com.voxelwars.utils.Message;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.UUID;

public class ClientListener extends ClientModule implements Listener {

    public ClientListener(ClientManager clients) {
        super(clients, ClientManager.MODULE_LISTENER);
    }

    @Override
    public void loadConfig() {

    }

    @Override
    public void saveConfig() {

    }

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        Rank rank = this.getClients().getRanks().getRank(uuid);

        if (rank == Rank.BANNED) {
            String reason = this.getClients().getBans().getReason(uuid);
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, reason);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //TODO: Add a check to see if they recovered from combat logging.
        event.setJoinMessage(Message.grayFormat("Player Join", event.getPlayer().getName()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        //TODO: Add safe/unsafe at the end of the message
        event.setQuitMessage(Message.grayFormat("Player Quit", event.getPlayer().getName()));
    }

}
