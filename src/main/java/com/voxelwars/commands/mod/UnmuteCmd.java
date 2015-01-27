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
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class UnmuteCmd extends Rootcommand {

    private final ClientManager clients;

    public UnmuteCmd(VoxelWars plugin) {
        super(plugin, "Unmute");
        this.clients = plugin.getClientManager();

        this.setShortDescription("Unmute a player.");
        this.setLongDescription("Unmute another player, if they're already muted.");
        this.setUsage("/unmute <player>");
        this.setRankRequirement(Rank.MODERATOR);
        this.setConsoleAllowed(true);
        this.setArgsRequired(1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        //TODO: Implement actual - better - client interactions, below.
        String name = args[0];

        UUID playerUuid = this.clients.getNames().getUuid(name);
        if (playerUuid == null) {
            playerUuid = UUID.randomUUID(); //TODO: Use Client's lookup method.
        }

        if (!clients.getMutes().isMuted(playerUuid)) {
            this.info(sender, name + " wasn't muted.");
        } else {
            this.success(sender, name + " has had their mute removed.");
        }

        clients.getMutes().removeDiscipline(playerUuid);
    }
}
