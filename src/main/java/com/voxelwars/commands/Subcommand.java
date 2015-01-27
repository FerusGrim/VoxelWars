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

import com.voxelwars.Frame;
import com.voxelwars.Rank;
import com.voxelwars.VoxelWars;
import com.voxelwars.managers.client.ClientManager;
import com.voxelwars.managers.gamemode.GameType;
import com.voxelwars.utils.Message;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class Subcommand extends Frame implements ISubcommand {

    @Override
    public void execute(CommandSender sender) {
        this.failure(sender, "Improperly configured command!");
        this.failure(sender, "Please show this message to a programmer.");
        this.failure(sender, "Command: " + this.getCommandPath());
        this.failure(sender, "TS:" + System.currentTimeMillis());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        this.failure(sender, "Improperly configured command!");
        this.failure(sender, "Please show this message to a programmer.");
        this.failure(sender, "Command: " + this.getCommandPath() + " "
                + CommandUtils.buildStringFromArgs(args, 0));
        this.failure(sender, "TS: " + System.currentTimeMillis());
    }

    private final String nameUpper;
    private final Map<String, Subcommand> subcommands;
    private final List<String> aliases;

    private final ClientManager clients;

    private Subcommand master = null;
    private boolean useMasterLogger;
    private String shortDescription;
    private String[] longDescription;
    private String usage;
    private Rank rankRequirement;
    private GameType[] gameTypes;
    private boolean hiddenCommand;
    private boolean consoleAllowed;
    private int requiredArgsLength;
    private boolean commandHub;

    private List<String> helpList;

    public Subcommand(VoxelWars plugin, String name) {
        super(plugin, name);
        this.clients = this.getPlugin().getClientManager();

        this.useMasterLogger = false;
        this.nameUpper = name;
        this.shortDescription = "";
        this.longDescription = new String[]{""};
        this.usage = "";
        this.rankRequirement = Rank.STANDARD;
        this.hiddenCommand = false;
        this.consoleAllowed = false;
        this.requiredArgsLength = 0;
        this.commandHub = false;
        this.subcommands = new HashMap<>();
        this.aliases = new ArrayList<>();
        this.helpList = null;
        this.gameTypes = new GameType[0];
    }

    public Subcommand(Subcommand master, String name) {
        this(master.getPlugin(), name);
        this.master = master;
    }

    public Subcommand getMaster() {
        return this.master;
    }

    public boolean hasMaster() {
        return this.master != null;
    }

    public boolean useMasterLogger() {
        return this.hasMaster() && this.useMasterLogger;
    }

    public void setUseMasterLogger(boolean useMasterLogger) {
        this.useMasterLogger = useMasterLogger;
    }

    public String getNameUpper() {
        return this.nameUpper;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String[] getLongDescription() {
        return this.longDescription;
    }

    public void setLongDescription(String... longDescription) {
        this.longDescription = longDescription;
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Rank getRankRequirement() {
        return this.rankRequirement;
    }

    public void setRankRequirement(Rank rankRequirement) {
        this.rankRequirement = rankRequirement;
    }

    public GameType[] getGameTypes() {
        return this.gameTypes;
    }

    public void setGameTypes(GameType... types) {
        this.gameTypes = types;
    }

    public boolean senderHasPermission(CommandSender sender, boolean notifyFailure) {
        if (sender instanceof Player && clients.getRanks().getRank(sender).isAtLeast(this.rankRequirement)) {
            return true;
        } else if (sender instanceof ConsoleCommandSender && this.consoleAllowed) {
            return true;
        }

        if (notifyFailure) {
            if (this.hiddenCommand) {
                sender.sendMessage(ChatColor.WHITE + "Unknown command. Type \""
                        + (sender instanceof Player ? "/" : "") + "help\" for help.");
            } else  {
                this.failure(sender, "You don't have permission!");
            }
        }

        return false;
    }

    public boolean senderInCorrectGame(CommandSender sender, boolean notifyFailure) {
        if (sender instanceof ConsoleCommandSender && this.consoleAllowed) {
            return true;
        }

        if (!(sender instanceof Player)) {
            return false;
        }

        if (this.getGameTypes().length < 1) {
            return true;
        }

        String senderWorld = ((Player) sender).getWorld().getName();
        for (GameType type : this.gameTypes) {
            if (this.getPlugin().getGameManager().getGame(type).isActiveWorld(senderWorld)) {
                return true;
            }
        }

        if (notifyFailure) {
            if (this.hiddenCommand) {
                sender.sendMessage(ChatColor.WHITE + "Unknown command. Type \"/help\" for help.");
            } else {
                this.failure(sender, "You're not in the correct Game!");
            }
        }
        return false;
    }

    public boolean senderCanUse(CommandSender sender, boolean notifyFailure) {
        return this.senderHasPermission(sender, notifyFailure)
                && this.senderInCorrectGame(sender, notifyFailure);
    }

    public boolean isHiddenCommand() {
        return this.hiddenCommand;
    }

    public void setHiddenCommand(boolean hiddenCommand) {
        this.hiddenCommand = hiddenCommand;
    }

    public boolean isConsoleAllowed() {
        return this.consoleAllowed;
    }

    public void setConsoleAllowed(boolean consoleAllowed) {
        this.consoleAllowed = consoleAllowed;
    }

    public boolean isArgsRequired() {
        return this.requiredArgsLength > 0;
    }

    public void setArgsRequired(int requiredArgsLength) {
        this.requiredArgsLength = requiredArgsLength;
    }

    public int getRequiredArgsLength() {
        return this.requiredArgsLength;
    }

    public boolean isCommandHub() {
        return this.commandHub;
    }

    public void setCommandHub(boolean commandHub) {
        this.commandHub = commandHub;
    }

    public Map<String, Subcommand> getSubcommands() {
        return this.subcommands;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public void setAliases(String... aliases) {
        Collections.addAll(this.aliases, aliases);
    }

    public boolean hasAliases() {
        return !this.aliases.isEmpty();
    }

    public String getAliasesAsString() {
        StringBuilder sb = new StringBuilder();
        String comma = "";
        for (String alias : this.aliases) {
            sb.append(comma).append("/").append(alias);
            comma = ", ";
        }
        return sb.toString();
    }

    public void addSubcommand(Subcommand subcommand) {
        this.subcommands.put(subcommand.getNameLower(), subcommand);

        if (this.helpList != null) {
            this.rebuildHelpList();
        }
    }

    public void removeSubcommand(String name) {
        this.subcommands.remove(name);

        if (this.helpList != null) {
            this.rebuildHelpList();
        }
    }

    public boolean hasChildren() {
        return !this.subcommands.isEmpty();
    }

    public Subcommand getSubcommand(String name) {
        Subcommand subcommand = this.subcommands.get(name);

        if (subcommand != null) {
            return subcommand;
        }

        for (Subcommand sub : this.subcommands.values()) {
            if (sub.getAliases().contains(name)) {
                return sub;
            }
        }

        return null;
    }

    public void showHelp(CommandSender sender, int page) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + " /-- [" + ChatColor.GREEN + "Help for /"
                + this.getCommandPath() + ChatColor.GRAY + "] --");

        for (String line : this.getLongDescription()) {
            sender.sendMessage(ChatColor.GRAY + " | " + ChatColor.BLUE + ChatColor.ITALIC + line);
        }

        if (!this.commandHub) {
            sender.sendMessage(ChatColor.GRAY + " | " + ChatColor.GOLD + "Usage: "
                    + ChatColor.GRAY + ChatColor.ITALIC + this.getUsage());
        }

        sender.sendMessage(ChatColor.GRAY + " | " + ChatColor.GOLD + "Aliases: "
                + ChatColor.GRAY + ChatColor.ITALIC + this.getAliasesAsString());

        if (this.hasChildren()) {
            sender.sendMessage("");
            int[] indexNumbers = CommandUtils.generateIndexNumbers(this, clients.getRanks().getRank(sender), page);
            page = indexNumbers[0];
            int perPage = indexNumbers[1];
            int startIndex = indexNumbers[2];
            int totalCount = indexNumbers[3];
            int pageCount = indexNumbers[4];
            int startNo = indexNumbers[5];
            int endNo = indexNumbers[6];

            sender.sendMessage("");
            CommandUtils.sendPageInfo(sender, page, pageCount, startNo, endNo, totalCount);

            for (int i = startIndex; i < this.helpList.size() && i < startIndex + perPage; i++) {
                Subcommand subcommand = this.subcommands.get(this.helpList.get(i));
                if (subcommand.senderHasPermission(sender, false)) {
                    sender.sendMessage(ChatColor.GRAY + " | " + ChatColor.YELLOW + "/... " + this.getNameLower()
                            + ChatColor.GRAY + " - " + ChatColor.BLUE + this.shortDescription);
                }
            }

            CommandUtils.sendEndPageInfo(sender, this.getCommandPath(), page, pageCount);
        }

        sender.sendMessage("");
    }

    public void showHelp(CommandSender sender) {
        this.showHelp(sender, 1);
    }

    public List<String> getHelpList() {
        return this.helpList;
    }

    public void rebuildHelpList() {
        this.helpList = new ArrayList<>(subcommands.size());
        this.helpList.addAll(this.subcommands.keySet());
        Collections.sort(this.helpList);
    }

    public String getCommandPath() {
        if (!this.hasMaster()) {
            return this.getNameLower();
        }

        StringBuilder sb = new StringBuilder();
        Subcommand subcommand = this;

        while (subcommand.hasMaster()) {
            sb.insert(0, subcommand.getNameLower() + " ");
            subcommand = subcommand.getMaster();
        }

        return sb.toString();
    }

    public void failure(CommandSender sender, String message) {
        if (this.useMasterLogger()) {
            this.getMaster().failure(sender, message);
            return;
        }

        Message.failure(sender, this.nameUpper, message);
    }

    public void info(CommandSender sender, String message) {
        if (this.useMasterLogger()) {
            this.getMaster().failure(sender, message);
            return;
        }

        Message.failure(sender, this.nameUpper, message);
    }

    public void success(CommandSender sender, String message) {
        if (this.useMasterLogger()) {
            this.getMaster().failure(sender, message);
            return;
        }

        Message.failure(sender, this.nameUpper, message);
    }
}
