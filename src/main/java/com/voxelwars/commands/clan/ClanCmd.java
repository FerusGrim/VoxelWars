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

import com.voxelwars.VoxelWars;
import com.voxelwars.commands.Rootcommand;
import com.voxelwars.managers.clan.Clan;
import com.voxelwars.managers.clan.ClanManager;
import com.voxelwars.managers.clan.ClanUtils;
import com.voxelwars.managers.gamemode.GameType;
import com.voxelwars.utils.Message;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClanCmd extends Rootcommand {

    public ClanCmd(VoxelWars plugin) {
        super(plugin, "Clan");

        this.setGameTypes(GameType.WARS);
        this.setCommandHub(true);
        this.setShortDescription("Shows clan commands.");
        this.setLongDescription("Clans are an integral part of the", "VoxelWars' Wars gamemode.");
        this.setAliases("c");
        this.setConsoleAllowed(false);
        this.setArgsRequired(0);

        this.addSubcommand(new ClanCreate(this));
        this.addSubcommand(new ClanLeave(this));
        this.addSubcommand(new ClanKick(this));
        this.addSubcommand(new ClanClaim(this));
        this.addSubcommand(new ClanUnclaim(this));
        this.addSubcommand(new ClanInfo(this));
    }

    @Override
    public void execute(CommandSender sender) {
        Clan clan = this.getPlugin().getClanManager().getClan(((Player) sender).getUniqueId());

        if (clan == null) {
            sender.sendMessage(Message.chatFormat("C", "You are not in a Clan."));
            return;
        }
        ClanUtils.showInfo(this.getPlugin(), clan, sender);

        //this.showHelp(sender);
    }
}
