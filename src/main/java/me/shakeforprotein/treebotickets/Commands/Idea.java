package me.shakeforprotein.treebotickets.Commands;

import me.shakeforprotein.treebotickets.Listeners.PlayerInput;
import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Idea implements CommandExecutor {

    private TreeboTickets pl;

    public Idea(TreeboTickets main) {
        pl = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String w = p.getWorld().getName();
            if (cmd.getName().equalsIgnoreCase("idea")) {
                pl.getConfig().set("players." + p.getName() + ".ticketstate", (int) 2);
                pl.getConfig().set("players." + p.getName() + ".type", "Idea");
                p.sendMessage("Please give a brief description of your idea.");
            }
        }
        return true;
    }
}