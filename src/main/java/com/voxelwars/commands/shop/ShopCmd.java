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

package com.voxelwars.commands.shop;

import com.voxelwars.VoxelWars;
import com.voxelwars.commands.Rootcommand;
import org.bukkit.command.CommandSender;

public class ShopCmd extends Rootcommand {

    public ShopCmd(VoxelWars plugin) {
        super(plugin, "Shop");

        this.setCommandHub(true);
        this.setShortDescription("Shows shop commands.");
        this.setLongDescription("Shops are an integral part of VoxelWars as a",
                "whole, providing access to all sorts of unique",
                "or hard-to-access items. As well as the simple stuff.");
        this.setAliases("s");
        this.setConsoleAllowed(true);
        this.setArgsRequired(0);

        this.addSubcommand(new ShopList(this));
    }

    @Override
    public void execute(CommandSender sender) {
        this.showHelp(sender);
    }
}