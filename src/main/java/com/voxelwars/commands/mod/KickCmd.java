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
import com.voxelwars.commands.CommandUtils;
import com.voxelwars.commands.Rootcommand;
import com.voxelwars.managers.client.ClientManager;
import com.voxelwars.utils.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCmd extends Rootcommand {

    private final ClientManager clients;

    private static final String DEFAULT_KICK_REASON = "Kicked by a moderator!";

    public KickCmd(VoxelWars plugin) {
        super(plugin, "Kick");
        this.clients = plugin.getClientManager();

        this.setShortDescription("Kick another player.");
        this.setLongDescription("Kicks a player, if they're kick-able. Note, that you'll be",
                "unable to kick a player with a higher rank than your own.");
        this.setUsage("/kick <player> [reason]");
        this.setRankRequirement(Rank.MODERATOR);
        this.setConsoleAllowed(true);
        this.setArgsRequired(1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String name = args[0];
        Player player = this.getPlugin().getServer().getPlayer(name);

        if (player == null) {
            this.failure(sender, "That player isn't online!");
            return;
        }

        if (!CommandUtils.senderAboveVictim(sender,
                this.clients.getRanks().getRank(sender),
                this.clients.getRanks().getRank(player))) {
            return;
        }

        String reason = DEFAULT_KICK_REASON;
        if (args.length > 1) {
            reason = CommandUtils.buildStringFromArgs(args, 1);
        }

        Message.info(this.getPlugin(), Rank.MODERATOR, this.getNameCanonical(),
                sender.getName() + " has kicked " + name + "!");
        Message.info(this.getPlugin(), Rank.MODERATOR, this.getNameCanonical(), "Reason " + reason);

        player.kickPlayer(reason);
    }
}
