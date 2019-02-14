package me.shakeforprotein.treebotickets.Methods.PlayerStatistics;

import me.shakeforprotein.treebotickets.TreeboTickets;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetStatistic {

    private TreeboTickets pl;

    public GetStatistic(TreeboTickets main){
        this.pl = main;
    }

    public String getStatistic(String stat, String playerName, Player sender){

        if(!stat.equalsIgnoreCase("UUID")){
        String server = pl.getServerName(sender);
        String query = "SELECT * FROM `stats_" + server + "` WHERE `IGNAME` = '" + playerName + "'";
        String returnedStat = "";
        ResultSet response;
        try {
            response = pl.connection.createStatement().executeQuery(query);
            while(response.next()){
                returnedStat = response.getString(stat);
            }
        } catch (SQLException e) {
            sender.sendMessage(ChatColor.RED + "Something went wrong");
            System.out.println("Encountered " + e.toString() + " during genericQuery()");
            pl.makeLog(e);
        }


        return playerName + " - " + stat + " - " + returnedStat;}
        else{
            return "You may not look up a player's UUID";
        }
    }


    public String getStatistic2(String stat, String playerName, String server){

        if(!stat.equalsIgnoreCase("UUID")){
            String query = "SELECT * FROM `stats_" + server + "` WHERE `IGNAME` = '" + playerName + "'";
            String returnedStat = "";
            ResultSet response;
            try {
                response = pl.connection.createStatement().executeQuery(query);
                while(response.next()){
                    returnedStat = response.getString(stat);
                }
            } catch (SQLException e) {
                System.out.println(ChatColor.RED + "Something went wrong");
                System.out.println("Encountered " + e.toString() + " during genericQuery()");
                pl.makeLog(e);
            }


            return playerName + " - " + stat + " - " + returnedStat;}
        else{
            return "You may not look up a player's UUID";
        }
    }

}
