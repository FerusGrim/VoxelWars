/*
Copyright (c) 2014-2015 Nicholas Badger / VoxelWars

THIS COMMAND FRAMEWORK WAS HEAVILY INSPIRED BY GABIZOU'S OWN FRAMEWORK
FOR AFTERKRAFT'S KRAFTRPG, WHICH IS ALSO LICENSED UNDER THE MIT LICENSE!
https://afterkraft.com/

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

package com.voxelwars.commands.clan;

import com.voxelwars.commands.Subcommand;
import com.voxelwars.managers.clan.Clan;
import com.voxelwars.managers.gamemode.GameType;
import com.voxelwars.utils.Message;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClanHome extends Subcommand {

    public ClanHome(Subcommand master) {
        super(master, "home");

        this.setGameTypes(GameType.WARS);
        this.setShortDescription("Set the location of your Clan home.");
        this.setLongDescription("Set the location of your Clan home in your territory",
                "You can only set one chunk your home.");
        this.setUsage("/clan home <set>");
        this.setConsoleAllowed(false);
        this.setArgsRequired(0);
        this.setAliases("c");
        this.setUseMasterLogger(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        Chunk chunk = player.getLocation().getChunk();
        Location location = player.getLocation();

        Clan clan = this.getPlugin().getClanManager().getClan(uuid);

        if (clan == null) {
            sender.sendMessage(Message.chatFormat("C", "You are not in a Clan."));
            return;
        }

        if (args.length == 1) {
            if ("all".equalsIgnoreCase(args[1])) {
                Clan clanObj = this.getPlugin().getClanManager().getClan(chunk);

                if (clanObj == null) {
                    sender.sendMessage(Message.chatFormat("C", "You cannot set your home in Wilderness."));
                    return;
                }

                if (!clan.isClaim(chunk)) {
                    sender.sendMessage(Message.chatFormat("C", "You do not own this chunk!"));
                    return;
                }

                sender.sendMessage(Message.chatFormat("C", "You have set your home at " + location.getX() + location.getY() + location.getZ()));
                this.getPlugin().getClanManager().inform(player, clan, Message.chatFormat("C", ChatColor.AQUA + player.getName() + ChatColor.YELLOW
                        + " has set your Clan home to ") + location.getX() + location.getY() + location.getZ(), false);
                clan.setHome(player.getLocation());
            }
            return;
        }

        //TODO check if spawn claim

        sender.sendMessage(Message.chatFormat("C", "You have teleported to your Clan home."));
        player.teleport(clan.getHome());
    }
}
