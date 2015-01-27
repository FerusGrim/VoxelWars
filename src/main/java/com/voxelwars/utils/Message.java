/*
Copyright (c) 2014-2015 Nicholas Badger / VoxelWars

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

package com.voxelwars.utils;

import com.voxelwars.Rank;
import com.voxelwars.VoxelWars;
import com.voxelwars.managers.Identifier;
import com.voxelwars.managers.client.ClientManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message {

    public static void failure(CommandSender sender, String feature, String message) {
        sender.sendMessage(format(ChatColor.RED, feature, message));
    }

    public static void info(CommandSender sender, String feature, String message) {
        sender.sendMessage(format(ChatColor.BLUE, feature, message));
    }

    public static void info(VoxelWars plugin, Rank broadcastTo, String feature, String message) {
        plugin.getServer().getOnlinePlayers().stream()
                .filter(player -> plugin.getClientManager().getRanks().getRank(player).isAtLeast(broadcastTo))
                        .forEach(player -> info(player, feature, message));
    }

    public static void success(CommandSender sender, String feature, String message) {
        sender.sendMessage(format(ChatColor.GREEN, feature, message));
    }

    public static String format(ChatColor featureColor, ChatColor splitColor,
                                ChatColor bodyColor, String feature, String body) {
        return featureColor + "" + ChatColor.BOLD + feature + " " + bodyColor + body;
    }

    public static String format(ChatColor headColor, String feature, String body) {
        return format(headColor, headColor, ChatColor.GRAY, feature, body);
    }

    public static void broadcast(VoxelWars plugin, Player player, String feature, String message, boolean informUser) {
        if (informUser) {
            plugin.getServer().broadcastMessage(format(ChatColor.BLUE, feature, message));
            return;
        }
        plugin.getServer().getOnlinePlayers().stream().filter(players -> !players.equals(player)).forEach(players -> players.sendMessage(format(ChatColor.BLUE, feature, message)));
    }

    public static String chatFormat(String letter, String body) {
        return ChatColor.RED + "[" + letter + "] " + ChatColor.YELLOW + body;
    }

    public static String grayFormat(String letter, String body) {
        return ChatColor.DARK_GRAY + "[" + letter + "] " + ChatColor.GRAY + body;
    }
}
