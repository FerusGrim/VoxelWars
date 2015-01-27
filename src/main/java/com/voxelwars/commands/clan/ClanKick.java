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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClanKick extends Subcommand {

    public ClanKick(Subcommand master) {
        super(master, "kick");

        this.setGameTypes(GameType.WARS);
        this.setShortDescription("Kick a player from your Clan.");
        this.setLongDescription("Kick a player from your Clan.", "You are not allowed to kick players who have a lower role.");
        this.setUsage("/clan kick <name>");
        this.setConsoleAllowed(false);
        this.setArgsRequired(1);
        this.setAliases("c");
        this.setUseMasterLogger(true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        //TODO search for player

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        Clan clan = this.getPlugin().getClanManager().getClan(uuid);

        if (clan == null) {
            sender.sendMessage(Message.chatFormat("C", "You are not in a Clan."));
            return;
        }

        //TODO searchPlayer

        //TODO check player Clan Role

        //TODO check for claim count

    }
}
