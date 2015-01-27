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
import com.voxelwars.managers.clan.ClanRole;
import com.voxelwars.managers.clan.ClanUtils;
import com.voxelwars.managers.gamemode.GameType;
import com.voxelwars.utils.Message;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClanClaim extends Subcommand {


    public ClanClaim(Subcommand master) {
        super(master, "claim");

        this.setGameTypes(GameType.WARS);
        this.setShortDescription("Claim a chunk!");
        this.setLongDescription("Claim a chunk to protect your base.");
        this.setUsage("/clan claim");
        this.setConsoleAllowed(false);
        this.setArgsRequired(0);
        this.setAliases("c");
        this.setUseMasterLogger(true);
    }

    @Override
    public void execute(CommandSender sender) {

        UUID uuid = ((Player) sender).getUniqueId();
        Clan clan = this.getPlugin().getClanManager().getClan(uuid);
        Chunk chunk = ((Player) sender).getLocation().getChunk();

        if (clan == null) {
            sender.sendMessage(Message.chatFormat("C", "You are not in a Clan."));
            return;
        }

        if (!clan.getRole(uuid).isAtLeast(ClanRole.ELDER)) {
            sender.sendMessage(Message.chatFormat("C", "You do not have permission to claim land."));
            return;
        }
        if (!clan.canClaim(chunk)) {
            sender.sendMessage(Message.chatFormat("C", "You have reached the maximum amount of claims for your Clan."));
            return;
        }

        if (!ClanUtils.isClanAlone(clan, chunk)) {
            sender.sendMessage(Message.chatFormat("C", "You cannot claim this Chunk with other players occupying it's space."));
            return;
        }

        sender.sendMessage(Message.chatFormat("C", "You have claimed Chunk: " + ChatColor.GREEN + chunk.getX() + ChatColor.YELLOW + ":" + ChatColor.GREEN + chunk.getZ()));

        this.getPlugin().getClanManager().inform(((Player) sender), clan, Message.chatFormat("C", sender.getName()
                + " has claimed Chunk: " + ChatColor.GREEN + chunk.getX() + ChatColor.YELLOW + ":" + ChatColor.GREEN + chunk.getZ()), false);
        clan.addClaim(chunk);
    }
}
