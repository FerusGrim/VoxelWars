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
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BanCmd extends Rootcommand {

    private final ClientManager clients;

    public static final String DEFAULT_BAN_REASON = "Banned by an administrator.";
    public static final long DEFAULT_BAN_LENGTH = 7;
    public static final long MAXIMUM_BAN_LENGTH = 366;

    public BanCmd(VoxelWars plugin) {
        super(plugin, "Ban");
        this.clients = plugin.getClientManager();

        this.setShortDescription("Ban another player.");
        this.setLongDescription("Ban a player, if they're ban-able. Note, that you'll be",
                "unable to ban a player with a higher rank than your own.");
        this.setUsage("/ban <player> [days/reason]");
        this.setRankRequirement(Rank.ADMINISTRATOR);
        this.setConsoleAllowed(true);
        this.setArgsRequired(1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        //TODO: Implement actual - better - client interaction, below.
        String name = args[0];

        UUID playerUuid = this.clients.getNames().getUuid(name);
        if (playerUuid == null) {
            playerUuid = UUID.randomUUID(); //TODO: Use Client's lookup method.
        }

        if (!CommandUtils.senderAboveVictim(sender,
                clients.getRanks().getRank(sender),
                clients.getRanks().getRank(playerUuid))) {
            return;
        }

        if (this.clients.getBans().isBanned(playerUuid)) {
            this.failure(sender, "Player is already banned!");
            return;
        }

        Pair<String, Long> reasonAndLength = CommandUtils.getReasonAndLength(args, 1, DEFAULT_BAN_REASON,
                DEFAULT_BAN_LENGTH, MAXIMUM_BAN_LENGTH);

        String reason = reasonAndLength.getKey();
        long length = reasonAndLength.getValue();

        // Message other moderators about this action.
        //TODO: Colour of Admins name depending on their rank.

        this.getPlugin().getServer().broadcastMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Ban " + ChatColor.RED +
                sender.getName() + ChatColor.GRAY + " has banned " + ChatColor.YELLOW + name + ChatColor.GRAY +
                allTheGrammar(length) + "!");

        this.getPlugin().getServer().broadcastMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Ban " + ChatColor.GRAY +
                "Reason: " + reason);

        this.clients.getBans().setDiscipline(playerUuid, TimeUnit.DAYS, length, reason);

        Player player = this.getPlugin().getServer().getPlayer(playerUuid);
        if (player != null) {
            player.kickPlayer(reason);
        }
    }

    private String allTheGrammar(long length) {
        return (length == 0 ? " permanently" : " for " + length + (length == 1 ? " day" : " days"));
    }
}
