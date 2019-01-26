package me.shakeforprotein.treebotickets.Methods.TicketUpdates;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PlayerUpdate {

    private TreeboTickets pl;

    public PlayerUpdate(TreeboTickets main) {
        this.pl = main;
    }

    public void playerUpdate(Player p, int t, String playerText) {
        if (p.hasPermission("tbtickets.view.own")) {
            if (playerText != "" && playerText != null) {
                String query = ("SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + t + "'");
                ResultSet response;
                int response2 = 0;
                try {
                    response = pl.connection.createStatement().executeQuery(query);
                    while (response.next()) {
                        int tId = response.getInt("ID");
                        String tPlayer = response.getString("IGNAME");
                        String tStaffSteps = response.getString("STAFFSTEPS");
                        String newStaffSteps = (tStaffSteps + "\n" + LocalDateTime.now() + " - " + p.getName() + "(Player) - " + playerText);
                        if (tPlayer.equalsIgnoreCase(p.getName())) {
                            String query2 = ("UPDATE  `" + pl.table + "` SET  `STAFFSTEPS` =  '" + newStaffSteps + "' WHERE  `ID` =" + tId);
                            try {
                                pl.connection.createStatement().executeUpdate(query2);
                                p.sendMessage("Ticket " + t + " updated.");
                            } catch (SQLException e) {
                                p.sendMessage(ChatColor.RED + "Something went wrong");
                                System.out.println("Encountered " + e.toString() + " during playerUpdate()");
                                pl.makeLog(e);
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Sorry " + p.getName() + " you do not own this ticket.");
                        }
                    }
                } catch (SQLException e) {
                    p.sendMessage(ChatColor.RED + "Something went wrong");
                    System.out.println("Encountered " + e.toString() + " during playerUpdate()");
                    pl.makeLog(e);
                }
            }
        } else {
            p.sendMessage("You don't the required permissions to run this command.");
        }
    }
}
