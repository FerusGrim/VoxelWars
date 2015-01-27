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

package com.voxelwars.commands.mod;

import com.voxelwars.Rank;
import com.voxelwars.VoxelWars;
import com.voxelwars.commands.Rootcommand;
import com.voxelwars.managers.client.ClientManager;
import com.voxelwars.utils.Message;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class SetRankCmd extends Rootcommand {

    private final ClientManager clients;

    public SetRankCmd(VoxelWars plugin) {
        super(plugin, "SetRank");
        this.clients = plugin.getClientManager();

        this.setShortDescription("Changes a player's rank.");
        this.setLongDescription("Modifies a player's rank. You must be a higher",
                "rank than the one you're modifying a player to.");
        this.setUsage("/setrank [player] [rank]");
        this.setAliases("sr");
        this.setRankRequirement(Rank.ADMINISTRATOR);
        this.setHiddenCommand(true);
        this.setConsoleAllowed(true);
        this.setArgsRequired(2);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Rank rank = getSpecifiedRank(args[1]);

        if (rank == null) {
            this.failure(sender, "Please specify a valid rank.");
            return;
        }


        if (!clients.getRanks().getRank(sender).isAbove(rank)) {
            this.failure(sender, "You don't have permission!");
            return;
        }

        UUID playerUuid = clients.getNames().getUuid(args[0]);
        if (playerUuid == null) {
            playerUuid = UUID.randomUUID(); //TODO: Use Client's lookup method.
        }

        clients.getRanks().setRank(playerUuid, rank);
        Message.success(sender, this.getNameUpper(), "Set " + args[0] + "'s rank to " + rank.toString() + ".");
    }

    private Rank getSpecifiedRank(String rank) {
        try {
            return Rank.fromLevel(Integer.parseInt(rank));
        } catch (NumberFormatException ignored) {
            return Rank.fromString(rank);
        }
    }
}
