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
import javafx.util.Pair;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MuteCmd extends Rootcommand {

    private final ClientManager clients;

    public static final String DEFAULT_MUTE_REASON = "Muted by a moderator.";
    public static final long DEFAULT_MUTE_LENGTH = 5; // Value is in minutes.
    public static final long MAXIMUM_MUTE_LENGTH = 10080; // 1 week, in minutes.

    public MuteCmd(VoxelWars plugin) {
        super(plugin, "Mute");
        this.clients = plugin.getClientManager();

        this.setShortDescription("Mute another player.");
        this.setLongDescription("Mutes a player, if they're mute-able. Note, that you'll be",
                "unable to mute a player with a higher rank than your own.");
        this.setUsage("/mute <player> [minutes/reason]");
        this.setRankRequirement(Rank.MODERATOR);
        this.setConsoleAllowed(true);
        this.setArgsRequired(1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        //TODO: Implement actual - better - client interaction, below.
        String name = args[0];
        UUID uuid = this.clients.getNames().getUuid(name);

        if (!CommandUtils.senderAboveVictim(sender,
                this.clients.getRanks().getRank(sender),
                this.clients.getRanks().getRank(uuid))) {
            return;
        }

        if (this.clients.getMutes().isMuted(uuid)) {
            this.failure(sender, "Player is already muted!");
        }

        Pair<String, Long> reasonAndLength = CommandUtils.getReasonAndLength(args, 1, DEFAULT_MUTE_REASON,
                DEFAULT_MUTE_LENGTH, MAXIMUM_MUTE_LENGTH);

        String reason = reasonAndLength.getKey();
        long length = reasonAndLength.getValue();

        // Message other moderators about this action.
        Message.info(this.getPlugin(), Rank.MODERATOR, this.getNameUpper(),
                sender.getName() + " has muted " + name + allTheGrammar(length) +  "!");
        Message.info(this.getPlugin(), Rank.MODERATOR, this.getNameUpper(), "Reason: " + reason);

        this.clients.getMutes().setDiscipline(uuid, TimeUnit.MINUTES, length, reason);

        Player player = this.getPlugin().getServer().getPlayer(uuid);
        if (player != null) {
            this.info(player, "You've been muted" + allTheGrammar(length) + ".");
        }
    }

    private String allTheGrammar(long length) {
        return (length == 0 ? " permanently" : " for " + length + (length == 1 ? " minute" : " minutes"));
    }
}
