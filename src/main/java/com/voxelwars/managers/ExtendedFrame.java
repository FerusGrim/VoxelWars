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

package com.voxelwars.managers;

import com.voxelwars.Frame;
import com.voxelwars.VoxelWars;
import com.voxelwars.utils.Message;
import org.bukkit.command.CommandSender;

public abstract class ExtendedFrame extends Frame implements IExtendedFrame {

    public ExtendedFrame(VoxelWars plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void loadConfig() {
        // Loading Config is optional.
    }

    @Override
    public void saveConfig() {
        // Saving Config is optional.
    }


    // Having Modules return the Manager's name, rather than the module itself.
    // Obviously, this can be overridden by statically referencing the relative
    // Message method directly.

    public void failure(CommandSender sender, String message) {
        Message.failure(sender, (
                this instanceof Module ? ((Module) this).getManager().getNameCanonical()
                        : this.getNameCanonical()
        ), message);
    }

    public void info(CommandSender sender, String message) {
        Message.info(sender, (
                this instanceof Module ? ((Module) this).getManager().getNameCanonical()
                        : this.getNameCanonical()
        ), message);
    }

    public void success(CommandSender sender, String message) {
        Message.success(sender, (
                this instanceof Module ? ((Module) this).getManager().getNameCanonical()
                        : this.getNameCanonical()
        ), message);
    }
}
