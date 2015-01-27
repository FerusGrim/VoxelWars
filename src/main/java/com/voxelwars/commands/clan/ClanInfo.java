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
import org.bukkit.command.CommandSender;

public class ClanInfo extends Subcommand {

    private final ClanManager clans;

    public ClanInfo(Subcommand master) {
        super(master, "info");

        this.clans = master.getPlugin().getClanManager();

        this.setGameTypes(GameType.WARS);
        this.setShortDescription("Show Clan information.");
        this.setLongDescription("Show other Clan information given the Player or Clan name.");
        this.setUsage("/clan info <name>");
        this.setConsoleAllowed(false);
        this.setArgsRequired(1);
        this.setAliases("c");
        this.setUseMasterLogger(true);
    }

    //TODO search for clan via player name or clan name, needs to check for matches for example:
    //     Matches for [Emp] - Emperor, Employ, Emplist
    //     or
    //     Matches for [Danny] - DannyDog, Dannyfrog, Dannycat, Dannyyisadogue

    @Override
    public void execute(CommandSender sender, String[] args) {
        Clan clan = this.getPlugin().getClanManager().getClan(args[0]);

        if (clan == null) {
            Message.failure(sender, "Clans", "Cannot find Clan " + args[0]);
        }

        ClanUtils.showInfo(this.getPlugin(), clan, sender);
    }
}
