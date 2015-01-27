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

package com.voxelwars.managers.gamemode.wars;

import com.voxelwars.managers.gamemode.Game;
import com.voxelwars.managers.gamemode.GameRunnable;
import com.voxelwars.utils.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public abstract class WarsRunnable extends GameRunnable {

    private final WarsGame game;

    public double borderPos;
    public double borderNeg;
    //TODO: Loading these doubles from JSON.
    public double warningPos;
    public double warningNeg;

    public WarsRunnable(Game game, String name) {
        super(game, name);
        this.game = (WarsGame) game;
        this.runTaskTimer(this.getPlugin(), 0, 20);
    }

    /**
     * @deprecated Use WarsRunnable#getWars
     * @return Game
     */
    @Deprecated
    @Override
    public Game getGame() {
        return this.game;
    }

    public WarsGame getWars() {
        return this.game;
    }

    @Override
    public void run() {
        for (Player player : this.getPlugin().getServer().getOnlinePlayers()) {
            if (player.getLocation().getX() > borderPos || player.getLocation().getX() < borderNeg
                    || player.getLocation().getZ() > borderPos || player.getLocation().getZ() < borderNeg) {
                player.damage(4);
                Message.info((CommandSender) player, "Server", "You are outside of the border!");
            }

            if (player.getLocation().getX() > warningPos || player.getLocation().getX() < warningNeg
                    || player.getLocation().getZ() > warningPos || player.getLocation().getZ() < warningNeg) {
                //TODO: Not sure how to send the message to the player only once, not every second.
            }
        }
    }


}
