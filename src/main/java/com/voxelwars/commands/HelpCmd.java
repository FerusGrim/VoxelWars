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

package com.voxelwars.commands;

import com.voxelwars.VoxelWars;
import com.voxelwars.managers.client.ClientManager;
import javafx.util.Pair;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCmd extends Rootcommand {

    private final CommandManager manager;
    private final ClientManager clients;

    public HelpCmd(VoxelWars plugin) {
        super(plugin, "Help");
        this.manager = plugin.getCommandManager();
        this.clients = plugin.getClientManager();

        this.setShortDescription("Shows this help menu!");
        this.setLongDescription("A simple-to-use help command, with a complex index",
                "and information system. Allows you to view the",
                "information for any root or subcommand.");
        this.setUsage("/help [cmd/page] [cmd/page] ...");
        this.setAliases("?");
        this.setConsoleAllowed(true);
        this.setArgsRequired(-1);
    }

    @Override
    public void execute(CommandSender sender) {
        this.showHelpIndex(sender, 1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Pair<Subcommand, String[]> properCommand = CommandUtils.getProperCommand(manager, args);
        Subcommand realCommand = properCommand.getKey();
        args = properCommand.getValue();

        if (realCommand == null) {
            if (args.length == 0) {
                this.failure(sender, "You must enter a valid command or number.");
            } else {
                try {
                    int page = Integer.parseInt(args[0]);
                    this.showHelpIndex(sender, page);
                } catch (NumberFormatException ignored) {
                    this.failure(sender, "You must enter a valid command or number.");
                }
            }
        } else {

            if (!realCommand.senderHasPermission(sender, !realCommand.isHiddenCommand())) {
                if (realCommand.isHiddenCommand()) {
                    this.failure(sender, "You must enter a valid command or number.");
                }
                return;
            }

            if (args.length == 0) {
                realCommand.showHelp(sender, 1);
            } else {
                try {
                    int page = Integer.parseInt(args[0]);
                    realCommand.showHelp(sender, page);
                } catch (NumberFormatException ignored) {
                    this.failure(sender, "You must enter a valid command or number.");
                }
            }
        }
    }

    private void showHelpIndex(CommandSender sender, int page) {
        int[] indexNumbers = CommandUtils.generateIndexNumbers(manager, clients.getRanks().getRank(sender), page);
        page = indexNumbers[0];
        int perPage = indexNumbers[1];
        int startIndex = indexNumbers[2];
        int totalCount = indexNumbers[3];
        int pageCount = indexNumbers[4];
        int startNo = indexNumbers[5];
        int endNo = indexNumbers[6];

        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + " /-- [" + ChatColor.GREEN + "Help Index" + ChatColor.GRAY + "] --");
        CommandUtils.sendPageInfo(sender, page, pageCount, startNo, endNo, totalCount);

        for (int i = startIndex; i < manager.getHelpList().size() && i < startIndex + perPage; i++) {
            Subcommand subcommand = manager.getCommands().get(manager.getHelpList().get(i));
            if (subcommand.senderHasPermission(sender, false)) {
                sender.sendMessage(ChatColor.GRAY + " | " + ChatColor.YELLOW + "/" + subcommand.getNameLower() + ChatColor.GRAY + " - " + (subcommand.isCommandHub() ? ChatColor.LIGHT_PURPLE : ChatColor.BLUE) + subcommand.getShortDescription());
            }
        }

        CommandUtils.sendEndPageInfo(sender, this.getCommandPath(), page, pageCount);
        sender.sendMessage("");
    }
}
