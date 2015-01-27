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

import com.voxelwars.commands.Subcommand;
import org.bukkit.command.CommandSender;

public class ShopList extends Subcommand {

    public ShopList(Subcommand master) {
        super(master, "List");

        this.setUseMasterLogger(true);
        this.setShortDescription("Shows a list of shops.");
        this.setLongDescription("Shows a listing of all available shops",
                "at your rank, as well as their locations.");
        this.setAliases("l");
        this.setConsoleAllowed(true);
        this.setArgsRequired(0);
    }

    @Override
    public void execute(CommandSender sender) {
        //TODO: Implement some sort of shop list.
        //TODO: Maybe a page system, like with commands?
        //TODO: REMEMBER, If using a page system, change required args to -1.
        this.info(sender, "Functionality not developed, yet.");
    }
}
