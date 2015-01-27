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
import com.voxelwars.commands.mod.*;
import com.voxelwars.commands.clan.*;
import com.voxelwars.managers.Identifier;
import com.voxelwars.managers.Manager;

import java.util.*;

public class CommandManager extends Manager {

    private final Map<String, Subcommand> commandMap;
    private List<String> helpList = null;

    public CommandManager(VoxelWars plugin) {
        super(plugin, Identifier.COMMAND);
        this.commandMap = new HashMap<>();
    }

    @Override
    public void registerModules() {
        // Admin commands
        this.addCommand(new BanCmd(this.getPlugin()));
        this.addCommand(new UnbanCmd(this.getPlugin()));
        this.addCommand(new SetRankCmd(this.getPlugin()));

        // Mod commands.
        this.addCommand(new MuteCmd(this.getPlugin()));
        this.addCommand(new UnmuteCmd(this.getPlugin()));
        this.addCommand(new KickCmd(this.getPlugin()));

        // Clan commands (if more than /clan)
        this.addCommand(new ClanCmd(this.getPlugin()));

        // General commands
        this.addCommand(new HelpCmd(this.getPlugin()));


        // Set Command Executors
        for (Subcommand command : this.commandMap.values()) {
            this.getPlugin().getCommand(command.getNameLower()).setExecutor((Rootcommand) command);

            // WARNING: ALIASES MUST ALSO BE REGISTERED AS SEPARATE COMMANDS IN THE PLUGIN.YML!
            for (String alias : command.getAliases()) this.getPlugin().getCommand(alias).setExecutor((Rootcommand) command);
            // REGISTERING ALIASES IS DONE THIS WAY TO ENSURE DEFAULT COMMANDS ARE OVERRIDDEN!
        }
    }

    @Override
    public void loadConfig() {

    }

    @Override
    public void saveConfig() {

    }

    public void addCommand(Subcommand subcommand) {
        this.commandMap.put(subcommand.getNameLower(), subcommand);
    }

    public Map<String, Subcommand> getCommands() {
        return this.commandMap;
    }

    public List<String> getHelpList() {
        return this.helpList;
    }

    public void rebuildHelpList() {
        this.helpList = new ArrayList<>(this.commandMap.size());
        this.helpList.addAll(this.commandMap.keySet());
        Collections.sort(this.helpList);
    }

    public Subcommand getCommand(String name) {
        Subcommand subcommand = this.commandMap.get(name);

        if (subcommand != null) {
            return subcommand;
        }

        for (Subcommand sub : this.commandMap.values()) {
            if (sub.getAliases().contains(name)) {
                return sub;
            }
        }

        return null;
    }
}
