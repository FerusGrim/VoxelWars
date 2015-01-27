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

import com.voxelwars.Rank;
import com.voxelwars.utils.Message;
import javafx.util.Pair;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class CommandUtils {

    public static Pair<Subcommand, String[]> getProperCommand(Subcommand subcommand, String[] args) {
        if (args.length == 0) {
            return new Pair<>(subcommand, args);
        }

        while (args.length > 0) {
            Subcommand placeholder = subcommand.getSubcommand(args[0]);

            if (placeholder == null) {
                break;
            }

            subcommand = placeholder;
            args = Arrays.copyOfRange(args, 1, args.length);
        }

        return new Pair<>(subcommand, args);
    }

    public static Pair<Subcommand, String[]> getProperCommand(CommandManager manager, String[] args) {
        if (args.length == 0) {
            return new Pair<>(null, args);
        }

        Subcommand subcommand = manager.getCommand(args[0]);
        if (subcommand != null) {
            return getProperCommand(subcommand, Arrays.copyOfRange(args, 1, args.length));
        }

        return new Pair<>(null, args);
    }

    private static int[] generateIndexNumbers(Map<String, Subcommand> subcommands, List<String> helpList, Rank playerRank, int perPage, int page) {
        if (page < 1) {
            page = 1;
        }

        int startCount = (page - 1) * perPage;
        int startIndex = -1;
        int endIndex = 0;
        int totalCount = 0;

        for (int i = 0; i < helpList.size(); i++) {
            Subcommand subcommand = subcommands.get(helpList.get(i));
            if (playerRank.isAtLeast(subcommand.getRankRequirement())) {

                if (totalCount == startCount) {
                    startIndex = i;
                }

                if (totalCount % perPage == 0) {
                    endIndex = i;
                }

                totalCount++;
            }
        }

        int pageCount = (int) Math.ceil(((double) totalCount) / perPage);

        if (page > pageCount || startIndex == -1) {
            page = pageCount;
            startIndex = endIndex;
        }

        int endNo = perPage * page;
        int startNo = endNo - perPage + 1;

        if (endNo > totalCount) {
            endNo = totalCount;
        }

        return new int[]{page, perPage, startIndex, totalCount, pageCount, startNo, endNo};
    }

    public static int[] generateIndexNumbers(CommandManager manager, Rank playerRank, int page) {
        if (manager.getHelpList() == null) {
            manager.rebuildHelpList();
        }

        return generateIndexNumbers(manager.getCommands(), manager.getHelpList(), playerRank, 8, page);
    }

    public static int[] generateIndexNumbers(Subcommand subcommand, Rank playerRank, int page) {
        if (subcommand.getHelpList() == null) {
            subcommand.rebuildHelpList();
        }

        return generateIndexNumbers(subcommand.getSubcommands(), subcommand.getHelpList(), playerRank, 4, page);
    }

    public static void sendPageInfo(CommandSender sender, int page, int pageCount, int startNo, int endNo, int totalCount) {
        sender.sendMessage(ChatColor.GRAY + " | Showing commands " + ChatColor.YELLOW + startNo + ChatColor.GRAY
                + " - " + ChatColor.YELLOW + endNo + ChatColor.GRAY + " of " + ChatColor.YELLOW + totalCount
                + ChatColor.GRAY + " (Page: " + ChatColor.YELLOW + page + ChatColor.GRAY + "/" + ChatColor.YELLOW
                + pageCount + ChatColor.GRAY + ").");
    }

    public static void sendEndPageInfo(CommandSender sender, String path, int page, int pageCount) {
        if (page != 1) {
            sender.sendMessage(ChatColor.GRAY + " | Previous: "
                    + ChatColor.ITALIC + "/" + path + " " + (page - 1));
        }

        if (page != pageCount) {
            sender.sendMessage(ChatColor.GRAY + " | More: "
                    + ChatColor.ITALIC + "/" + path + " " + (page + 1));
        }
    }

    public static boolean senderAboveVictim(CommandSender sender, Rank senderRank, Rank victim) {
        if (!senderRank.isAbove(victim)) {
            Message.failure(sender, "Rank", "Player's rank is higher than or equal to your own.");
            return false;
        }
        return true;
    }

    public static Pair<String, Long> getReasonAndLength(String[] args, int delimit, String defaultReason, long defaultLength, long maximumLength) {
        args = Arrays.copyOfRange(args, delimit, args.length);

        String reason = defaultReason;
        long length = defaultLength;

        if (args.length > 0) {
            try {
                length = Long.parseLong(args[0]);

                if (args.length > 1) {
                    reason = buildStringFromArgs(args, 1);
                }
            } catch (NumberFormatException ignored) {
                reason = buildStringFromArgs(args, 0);
            }
        }

        if (length > maximumLength) {
            length = maximumLength;
        }

        return new Pair<>(reason, length);
    }

    public static String buildStringFromArgs(String[] args, int delimit) {
        args = Arrays.copyOfRange(args, delimit, args.length);
        StringBuilder sb = new StringBuilder();

        for (String arg : args) {
            sb.append(arg).append(" ");
        }

        return sb.toString().trim();
    }
}
