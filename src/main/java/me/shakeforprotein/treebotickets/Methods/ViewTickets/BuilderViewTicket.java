package me.shakeforprotein.treebotickets.Methods.ViewTickets;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuilderViewTicket {

    private TreeboTickets pl;

    public BuilderViewTicket(TreeboTickets main){this.pl = main;}

    public void builderViewTicket(Player p, int t) {
        if (p.hasPermission("tbtickets.builder")) {
            int tId = -1;
            String tPlayer = "";
            String tCoords = "";
            String tDesc = "";
            String tStaffS = "";
            String tUserS = "";
            String tStatus = "";
            String tOpened = "";
            String tModified = "";
            String tWorld = "";
            ResultSet response;
            try {
                response = pl.connection.createStatement().executeQuery("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
                while (response.next()) {
                    String assigned = response.getString("STAFF");
                    if (assigned.equalsIgnoreCase("Builders")) {
                        tId = response.getInt("ID");
                        tPlayer = response.getString("IGNAME");
                        tCoords = (response.getString("X") + " " + response.getString("Y") + " " + response.getString("Z"));
                        tDesc = response.getString("DESCRIPTION");
                        tStaffS = response.getString("STAFFSTEPS");
                        tUserS = response.getString("USERSTEPS");
                        tStatus = response.getString("STATUS");
                        tOpened = response.getDate("OPENED").toString();
                        tWorld = response.getString("WORLD");
                        try {
                            tModified = response.getDate("MODIFIED").toString();
                        } catch (NullPointerException e) {
                            tModified = "BLANK VALUE";
                        }

                        p.sendMessage((pl.badge + "XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.GREEN + "Ticket ID: " + ChatColor.WHITE + tId);
                        if (tStatus.equalsIgnoreCase("OPEN")) {
                            p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.GREEN + "Ticket Status: " + ChatColor.GOLD + tStatus);
                        } else if (tStatus.equalsIgnoreCase("closed")) {
                            p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.GREEN + "Ticket Status: " + ChatColor.BLUE + tStatus);
                        } else {
                            p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.GREEN + "Ticket Status: " + ChatColor.WHITE + tStatus);
                        }
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.GREEN + "Opened by Player: " + ChatColor.WHITE + tPlayer);
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.GREEN + "Opened at: " + ChatColor.WHITE + tOpened);
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.GREEN + "Last Updated: " + ChatColor.WHITE + tModified);
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.GREEN + "On World: " + ChatColor.WHITE + tWorld);
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.GREEN + "At Coordinates: " + ChatColor.GOLD + tCoords);
                        p.sendMessage("");
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.GREEN + "User Description: " + ChatColor.WHITE + tDesc.replace("APOSTR","'").replace("BSlash","\\").replace(" FSlash ","/"));
                        p.sendMessage("");
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.BLUE + "Steps taken by user: " + ChatColor.WHITE + tUserS.replace("APOSTR","'").replace("BSlash","\\").replace(" FSlash ","/"));
                        p.sendMessage("");
                        p.sendMessage(ChatColor.GOLD + "[X]" + ChatColor.RESET + ChatColor.RED + "Staff comments / actions: " + ChatColor.WHITE + tStaffS.replace("APOSTR","'").replace("BSlash","\\").replace(" FSlash ","/"));
                    } else {
                        p.sendMessage(pl.err + "You can't use this command");
                    }
                }
            } catch (SQLException e) {
                p.sendMessage(pl.err + "Something went wrong");
                System.out.println(pl.err + "Encountered " + e.toString() + " during builderViewTicket()");
                pl.makeLog(e);
            }
        } else {
            p.sendMessage(pl.err + "You lack the sufficient permissions.");
        }
    }
}
