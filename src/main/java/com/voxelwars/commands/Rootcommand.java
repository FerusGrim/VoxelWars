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

import com.google.common.collect.ImmutableList;
import com.voxelwars.VoxelWars;
import javafx.util.Pair;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class Rootcommand extends Subcommand implements TabExecutor {

    public Rootcommand(VoxelWars plugin, String name) {
        super(plugin, name);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Pair<Subcommand, String[]> properCommand = CommandUtils.getProperCommand(this, args);
        Subcommand realCommand = properCommand.getKey();
        args = properCommand.getValue();

        if (!senderCanUse(sender, true)) {
            return true;
        }

        if (args.length == 0) {
            if (realCommand.getRequiredArgsLength() == -1) {
                realCommand.execute(sender);
                return true;
            }

            if (realCommand.isArgsRequired()) {
                realCommand.showHelp(sender);
                realCommand.failure(sender, "Required Arguments: " + realCommand.getRequiredArgsLength());
                return true;
            } else {
                realCommand.execute(sender);
                return true;
            }
        }

        if (realCommand.getRequiredArgsLength() == 0) {
            realCommand.showHelp(sender);
            realCommand.failure(sender, "This command doesn't accept arguments!");
            return true;
        }

        if (realCommand.getRequiredArgsLength() > args.length) {
            realCommand.showHelp(sender);
            realCommand.failure(sender, "Required Arguments: " + realCommand.getRequiredArgsLength());
            return true;
        }

        realCommand.execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Pair<Subcommand, String[]> properCommand = CommandUtils.getProperCommand(this, args);
        Subcommand realCommand = properCommand.getKey();
        args = properCommand.getValue();

        List<String> matches = new ArrayList<>();

        if (args.length > 0) {
            StringUtil.copyPartialMatches(args[0], realCommand.getSubcommands().keySet(), matches);
            ListIterator<String> matchesIter = matches.listIterator();

            while (matchesIter.hasNext()) {
                if (realCommand.getSubcommand(matchesIter.next()).senderHasPermission(sender, false)) {
                    matchesIter.remove();
                }
            }

            return matches;
        }

        return ImmutableList.of();
    }
}