package me.shakeforprotein.treebotickets;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;


public class Commands implements CommandExecutor {

    private TreeboTickets pl;
    private PlayerInput pi;

    public Commands(TreeboTickets main) {
        pl = main;
        this.pi = pi;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            //TBTICKET Logic
            if (cmd.getName().equalsIgnoreCase("tbTicket")) {
                // TBTICKET COMMAND - With no arguments
                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "Incorrect usage. Please try one of the following.");
                    p.sendMessage(ChatColor.GOLD + "/tbtickets open  -  Creates a new ticket");
                    p.sendMessage(ChatColor.GOLD + "/tbtickets close <ticket_number> - Closes the ticket with the ticket number if it is your own");
                    p.sendMessage(ChatColor.GOLD + "/tbtickets view <ticket_number>  -  Shows the detail of one your ticket with the listed ticket_number");
                    p.sendMessage(ChatColor.GOLD + "/tbtickets list  -  Lists all tickets you've created");
                }

                if (args.length == 1) {
                    // TBTICKET COMMAND - OPEN NEW TICKET LOGIC
                    if (args[0].equalsIgnoreCase("open") && p.hasPermission("tbtickets.create")) {
                        p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                        p.sendMessage("Please enter");
                        p.sendMessage(ChatColor.GOLD + "1 " + ChatColor.RESET + "-" + ChatColor.RED + " For Server related issues");
                        p.sendMessage(ChatColor.GOLD + "2 " + ChatColor.RESET + "-" + ChatColor.RED + " For Griefer related issues");
                        p.sendMessage(ChatColor.GOLD + "3 " + ChatColor.RESET + "-" + ChatColor.RED + " For Other issues");
                        p.sendMessage("or enter 'cancel' at any time to stop creating a ticket");
                        pl.getConfig().set("players." + p.getName() + ".ticketstate", 1);
                        pl.saveConfig();
                    }
                    //TBTICKEWT COMMAND - LIST OWN Logic
                    else if (args[0].equalsIgnoreCase("list") && p.hasPermission("tbtickets.view.own")) {
                    //Todo Fill command details for /tbticket list
                        String fullData = pl.listTickets(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE IGNAME='" + p.getName() + "'");
                    }

                    //TBTICKET COMMAND - INCOMPLETE COMMANDS
                    else if (args[0].equalsIgnoreCase("view") && p.hasPermission("tbtickets.view.own")) {
                        p.sendMessage(ChatColor.RED + "Please include the ticket number at the end of the command. Eg. /tbticket view 274");
                    }
                    else if (args[0].equalsIgnoreCase("close") && p.hasPermission("tbtickets.close.own")) {
                        p.sendMessage(ChatColor.RED + "Please include the ticket number at the end of the command. Eg. /tbticket close 274");
                    }
                }



                else if (args.length == 2){
                // Make sure second argumentt is a valid integer.
                    Integer ticketNumber = -1;
                    if(isNumeric(args[1])){
                        ticketNumber = Integer.parseInt(args[1]);
                    }

                    if(ticketNumber != -1){
                    //TBTICKET COMMAND - VIEW SPECIFIC TICKET LOGIC
                    if (args[0].equalsIgnoreCase("view")){
                        getTicket(p, ticketNumber);
                    }
                    if (args[0].equalsIgnoreCase("close")){
                        closeTicket(p,ticketNumber);
                    }
                }
            }
            }

            if (cmd.getName().equalsIgnoreCase("tbta")) {
                if (args.length < 1){
                    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                    p.sendMessage(ChatColor.RED + "INCORRECT USAGE. CORRECT USAGE IS AS FOLLOWS");
                    p.sendMessage(ChatColor.GOLD + "/tbta list assigned  -  Lists all tickets assigned to you");
                    p.sendMessage(ChatColor.GOLD + "/tbta list unassigned  -  Lists all tickets not assigned to a staff member");
                    p.sendMessage(ChatColor.GOLD + "/tbta view <ticket_number>  -  Displays details on ticket assigned to ");
                }

            }
        }

        return true;
    }


    public static boolean isNumeric(String str)
    {
        return str.matches("\\d+");
    }


    public String getTicket(Player p, Integer t){
        String fullData = pl.genericQuery(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE IGNAME='" + p.getName() + "'");
        return fullData;
    }


    public String closeTicket (Player p, Integer t){
        //TODO Close ticket logic
        return "";
    }

    public void dislpayTicket (Player p, Integer t){
        String  ticket = getTicket(p, t);
        p.sendMessage(ticket);
        String tId = "";
        String tPlayer = "";
        String tCoords = "";
        String tDesc = "";
        String tStaffA = "";
        if(p.hasPermission("tbtickets.view.any")){

        }
        else if(p.hasPermission("tbtickets.view.own")){

        }


        p.sendMessage("");
        //TODO
    }

    public String closeTicket (Integer t){

        return "";
    }
}