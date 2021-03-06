package me.shakeforprotein.treebotickets.Methods.TicketStatistics;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminStats {

    private TreeboTickets pl;

    public AdminStats(TreeboTickets main) {
        this.pl = main;
    }

    public void adminStats(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(pl, new Runnable() {
            @Override
            public void run() {

                ResultSet response;
                try {
                    p.sendMessage((pl.badge + "XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE ID!='0'");
            while (response.next()) {
                p.sendMessage("Total Tickets:" + response.getInt("TOTAL"));
            }*/
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STAFF!='UNASSIGNED'");
            while (response.next()) {
                p.sendMessage("Assigned Tickets:" + response.getInt("TOTAL"));
            }*/
                    response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='UNASSIGNED'");
                    while (response.next()) {
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "UnAssigned Tickets:" + response.getInt("TOTAL"));
                    }
                    response = pl.connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='OPEN'");
                    while (response.next()) {
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + "Open Tickets:" + response.getInt("TOTAL"));
                    }
            /*response = connection.createStatement().executeQuery("SELECT Count(*) AS TOTAL FROM `" + getConfig().getString("table") + "` WHERE STATUS='CLOSED'");
            while (response.next()) {
                p.sendMessage("Closed Tickets:" + response.getInt("TOTAL"));
            }*/
                } catch (SQLException e) {
                    p.sendMessage(pl.err + "Something went wrong");
                    System.out.println(pl.err + "Encountered " + e.toString() + " during AdminStats()");
                    pl.makeLog(e);
                }
            }
        });
    }
}
