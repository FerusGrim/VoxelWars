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

package com.voxelwars.managers.clan;

import com.voxelwars.VoxelWars;
import com.voxelwars.utils.HandyLocation;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class ClanUtils {

    public static final Pattern validName = Pattern.compile("^[A-Za-z0-9]{4,10}$");

    public static boolean isValidName(String name) {
        return validName.pattern().matches(name);
    }

    public static boolean isClanAlone(Clan clan, Chunk chunk) {
        for (Entity entity : chunk.getEntities()) {
            if (clan.getRole(entity.getUniqueId()) == ClanRole.NOT_JOINED) {
                return false;
            }
        }
        return true;
    }

    public static Clan createClan(Player player, String name) {
        Clan clan = new Clan(name);

        clan.setRole(player.getUniqueId(), ClanRole.LEADER);

        return clan;
    }

    public static Clan loadClan(JSONObject clanObj) {
        String name = (String) clanObj.get("name");
        String home = (String) clanObj.get("home");
        JSONArray mbrArray = (JSONArray) clanObj.get("members");
        JSONArray clmArray = (JSONArray) clanObj.get("claims");

        Clan clan = new Clan(name);

        if (!"".equals(home)) {
            clan.setHome(HandyLocation.locationFromString(home));
        }

        for (Object mbr : mbrArray) {
            JSONObject member = (JSONObject) mbr;

            UUID uuid = UUID.fromString((String) member.get("uuid"));
            ClanRole role = ClanRole.fromString((String) member.get("role"));

            clan.setRole(uuid, role);
        }

        for (Object claim : clmArray) {
            clan.addClaim((String) claim);
        }

        return clan;
    }

    public static JSONObject saveClan(Clan clan) {
        return null;
    }


    public static void showInfo(VoxelWars plugin, Clan clan, CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "[C]" + ChatColor.DARK_AQUA + " " + ChatColor.BOLD + clan.getName());
        sender.sendMessage(ChatColor.YELLOW + "Allies ["
                + ChatColor.GREEN + clan.getAllySet().size() + "/"
                + (8 - clan.getMemberSet().size())
                + ChatColor.YELLOW + "]:"
                + ChatColor.GREEN + clan.getAllies());
        sender.sendMessage(ChatColor.YELLOW + "Enemies [" + ChatColor.GREEN + clan.getEnemySet().size() + ChatColor.YELLOW + "]:" + ChatColor.RED + clan.getEnemies());
        sender.sendMessage(ChatColor.YELLOW + "Members: " + clan.getMembers(plugin));
    }

    public void showInfo(VoxelWars plugin, Clan clanSender, Clan clanLookup, CommandSender sender) {

    }
}
