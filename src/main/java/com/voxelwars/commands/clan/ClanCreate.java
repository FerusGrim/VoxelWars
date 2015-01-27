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
import com.voxelwars.managers.clan.ClanManager;
import com.voxelwars.managers.clan.ClanUtils;
import com.voxelwars.managers.gamemode.GameType;
import com.voxelwars.utils.Message;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ClanCreate extends Subcommand {

    private final ClanManager clans;

    public ClanCreate(Subcommand master) {
        super(master, "create");
        this.clans = master.getPlugin().getClanManager();

        this.setGameTypes(GameType.WARS);
        this.setShortDescription("Create a clan!");
        this.setLongDescription("Create a new clan, just for yourself.",
                "Clan names are case-insensitive, alpha-numeric, 4-10 characters in length.");
        this.setUsage("/clan create <name>");
        this.setConsoleAllowed(false);
        this.setArgsRequired(1);
        this.setAliases("c");
        this.setUseMasterLogger(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        String name = args[0];
        Clan clan = this.clans.getClan(uuid);

        if (clan != null) {
            sender.sendMessage(Message.chatFormat("C", "You are already in a Clan."));
            return;
        }

        if (this.clans.isDeserter(uuid)) {
            sender.sendMessage(Message.chatFormat("C", "You've recently deserted a Clan while it was being dominated!"));
            sender.sendMessage(Message.chatFormat("C", "You can create a new Clan in "
                    + TimeUnit.MILLISECONDS.toSeconds(this.clans.getDeserterMap().get(uuid) - System.currentTimeMillis()) + " seconds!"));
            return;
        }

        if (!ClanUtils.isValidName(name)) {
            sender.sendMessage(Message.chatFormat("C", "This name is invalid, and cannot be used."));
            sender.sendMessage(Message.chatFormat("C", "To view name restrictions, type '/help clan create'."));
            return;
        }

        clan = this.clans.getClan(name);

        if (clan != null) {
            sender.sendMessage(Message.chatFormat("C", "This name is already taken by another Clan!"));
            return;
        }

        sender.sendMessage(Message.chatFormat("C", ChatColor.AQUA + name + ChatColor.YELLOW + " has been created!"));
        this.getPlugin().getServer().broadcastMessage(
                Message.chatFormat("C", ChatColor.GREEN + player.getDisplayName() + ChatColor.YELLOW + " has formed the Clan " + ChatColor.AQUA + name));

        this.clans.addClan(ClanUtils.createClan((Player) sender, name));
    }
}
