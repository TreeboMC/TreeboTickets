package me.shakeforprotein.treebotickets;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



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
            String w = p.getWorld().getName();
            if (cmd.getName().equalsIgnoreCase("survival")) {pl.serverSwitch(p, "survival");}
            else if (cmd.getName().equalsIgnoreCase("lobby")) {pl.serverSwitch(p, "hub");}
            else if (cmd.getName().equalsIgnoreCase("creative")) {pl.serverSwitch(p, "creative");}
            else if (cmd.getName().equalsIgnoreCase("skyblock")) {pl.serverSwitch(p, "sky");}
            else if (cmd.getName().equalsIgnoreCase("skygrid")) {pl.serverSwitch(p, "sky");}
            else if (cmd.getName().equalsIgnoreCase("acidislands")) {pl.serverSwitch(p, "sky");}
            else if (cmd.getName().equalsIgnoreCase("hardcore")) {pl.serverSwitch(p, "hardcore");}
            else if (cmd.getName().equalsIgnoreCase("prison")) {pl.serverSwitch(p, "prison");}
            else if (cmd.getName().equalsIgnoreCase("plots")) {pl.serverSwitch(p, "creative");}
            else if (cmd.getName().equalsIgnoreCase("games")) {pl.serverSwitch(p, "games");}
            else if (cmd.getName().equalsIgnoreCase("test")) {pl.serverSwitch(p, "test");}
            else if (cmd.getName().equalsIgnoreCase("idea")){
                pl.getConfig().set("players." + p.getName() + ".ticketstate", (int) 2);
                pl.getConfig().set("players." + p.getName() + ".type", "Idea");
                p.sendMessage("Please give a brief description of your idea.");
            }
            else if (cmd.getName().equalsIgnoreCase("review")){
                pl.getConfig().set("players." + p.getName() + ".ticketstate", (int) 2);
                pl.getConfig().set("players." + p.getName() + ".type", "Review");
                p.sendMessage("Please give a brief description of your build.");
            }

            else if (cmd.getName().equalsIgnoreCase("reviewlist") && p.hasPermission("tbtickets.builder")){
                pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='Builders'");
            }
            else if (cmd.getName().equalsIgnoreCase("reviewview") && p.hasPermission("tbtickets.builder")){
                if(args.length > 0){
                    pl.builderViewTicket(p, Integer.parseInt(args[0]));}
            }
            else if (cmd.getName().equalsIgnoreCase("reviewstats") && p.hasPermission("tbtickets.builder")){
                    pl.builderStats(p);
            }
            else if (cmd.getName().equalsIgnoreCase("reviewclose") && p.hasPermission("tbtickets.builder")){
                if(args.length > 0){
                    pl.builderClose(p, args[0]);}
            }
                //TBTICKET Logic
            else if (cmd.getName().equalsIgnoreCase("tbTicket")) {
                // TBTICKET COMMAND - With no arguments

                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "Incorrect usage. Please try one of the following.");
                    p.sendMessage(ChatColor.GOLD + "/tbticket open  -  Creates a new ticket");
                    p.sendMessage(ChatColor.GOLD + "/tbticket close <ticket_number> - Closes the ticket with the ticket number if it is your own");
                    p.sendMessage(ChatColor.GOLD + "/tbticket view <ticket_number>  -  Shows the detail of one your ticket with the listed ticket_number");
                    p.sendMessage(ChatColor.GOLD + "/tbticket list  -  Lists all tickets you've created");
                } else if (args.length == 1) {
                    // TBTICKET COMMAND - OPEN NEW TICKET LOGIC
                    if (args[0].equalsIgnoreCase("open") && p.hasPermission("tbtickets.create")) {
                        pl.startTicketLogic(p);
                    }
                    else if(args[0].equalsIgnoreCase("open") && !p.hasPermission("tbtickets.create")){p.sendMessage(ChatColor.RED+ "You lack the required permission to create a new ticket");}
                    //TBTICKET COMMAND - LIST OWN Logic
                    else if (args[0].equalsIgnoreCase("list") && (p.hasPermission("tbtickets.view.own"))) {
                        String fullData = pl.listTickets(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE IGNAME='" + p.getName() + "'");
                    }
                    else if (args[0].equalsIgnoreCase("list") && !p.hasPermission("tbtickets.view.own")){p.sendMessage(ChatColor.RED+ "You lack the required permission to list tickets");}

                    //TBTICKET COMMAND - INCOMPLETE COMMANDS
                    else if (args[0].equalsIgnoreCase("view") && p.hasPermission("tbtickets.view.own")) {
                        p.sendMessage(ChatColor.RED + "Please include the ticket number at the end of the command. Eg. /tbticket view 274");
                    }
                    else if (args[0].equalsIgnoreCase("view") && !p.hasPermission("tbtickets.view.own")){p.sendMessage(ChatColor.RED+ "You lack the required permission to view tickets");}
                    else if (args[0].equalsIgnoreCase("close") && p.hasPermission("tbtickets.close.own")) {
                        p.sendMessage(ChatColor.RED + "Please include the ticket number at the end of the command. Eg. /tbticket close 274");
                    }
                    else if (args[0].equalsIgnoreCase("view") && !p.hasPermission("tbtickets.close.own")){p.sendMessage(ChatColor.RED+ "You lack the required permission to close a ticket");}

                } else if (args.length == 2) {
                    // Make sure second argumentt is a valid integer.
                    Integer ticketNumber = -1;
                    if (isNumeric(args[1])) {
                        ticketNumber = Integer.parseInt(args[1]);
                    }

                    if (ticketNumber != -1) {
                        //TBTICKET COMMAND - VIEW SPECIFIC TICKET LOGIC
                        if(args[0].equalsIgnoreCase("view") && !p.hasPermission("tbtickets.view.own")){p.sendMessage(ChatColor.RED+ "You lack the required permission to view tickets");}
                        else if(args[0].equalsIgnoreCase("close") && !p.hasPermission("tbtickets.close.own")){p.sendMessage(ChatColor.RED+ "You lack the required permission to close tickets");}

                        else if (args[0].equalsIgnoreCase("view")) {
                            pl.getTicket(p, ticketNumber);
                        }
                        else if (args[0].equalsIgnoreCase("close")) {
                            pl.closeTicket(p, ticketNumber);
                        }
                    }
                }
            }

            if (cmd.getName().equalsIgnoreCase("tbta")) {
                if (args.length < 2) {
                    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                    p.sendMessage(ChatColor.RED + "INCORRECT USAGE. CORRECT USAGE IS AS FOLLOWS");
                    p.sendMessage(ChatColor.GOLD + "/tbta list <assigned|unnassigned|open|closed|idea>  -  Lists all tickets assigned to you");
                    p.sendMessage(ChatColor.GOLD + "/tbta view <ticket_number>  -  Displays details on specific ticket");
                    p.sendMessage(ChatColor.GOLD + "/tbta close <ticket_number>  -  Close ticket with id");
                    p.sendMessage(ChatColor.GOLD + "/tbta <claim|unclaim> <ticket_number>  -  Assigns an unassigned ticket to yourself");
                    p.sendMessage(ChatColor.GOLD + "/tbta tp <ticket_number>  -  Teleport to location of ticket number");
                    p.sendMessage(ChatColor.GOLD + "/tbta update <ticket_number>  <your message> -  Updates a tickets staff steps data.  Remember this can be seen by the ticket submitter");



                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("list")){
                        if(args[1].equalsIgnoreCase("assigned")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='" + p.getName() + "'");
                        }
                        else if (args[1].equalsIgnoreCase("unassigned")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='UNASSIGNED'");
                        }
                        else if (args[1].equalsIgnoreCase("idea")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE TYPE='Idea'");
                        }
                        else if (args[1].equalsIgnoreCase("open")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='OPEN'");
                        }
                        else if (args[1].equalsIgnoreCase("closed")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='CLOSED'");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("close") && p.hasPermission("tbtickets.close.any")){
                        pl.staffCloseTicket(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("view") && p.hasPermission("tbtickets.view.any")){
                        pl.staffViewTicket(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("claim")) {
                        pl.staffClaim(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("unclaim")){
                        pl.staffUnclaim(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("tp")) {
                        if (isNumeric(args[1])) {
                            pl.staffTP(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + args[1] + "'", Integer.parseInt(args[1]));
                        }
                    }
                }
                else if (args.length >= 3 && args[0].equalsIgnoreCase("update")){
                    StringBuilder staffText = new StringBuilder();
                    for(int i = 2; i < args.length; i++){
                        staffText.append(args[i] + " ");
                    }

                    pl.staffUpdate(p, Integer.parseInt(args[1]), staffText.toString());
                }
            }

            if (cmd.getName().equalsIgnoreCase("tbticketadmin")) {
                if (args.length == 1 && args[0].equalsIgnoreCase("stats") && p.hasPermission("tbtickets.admin")){pl.adminStats(p);}
                else if (args.length == 1 && args[0].equalsIgnoreCase("version") && p.hasPermission("tbtickets.admin")){p.sendMessage(pl.getConfig().getString("networkName") + " - Version:  " +  pl.getDescription().getVersion());}
                else if (args.length == 1 && args[0].equalsIgnoreCase("reload") && p.hasPermission("tbtickets.admin")){pl.reloadConfig(); p.sendMessage(pl.getConfig().getString("networkName") +  " plugin config reloaded");}

                else if (args.length < 2) {
                    p.sendMessage(("XXXNETWORKNAMEXXX - " + ChatColor.RED + "Ticket System").replace("XXXNETWORKNAMEXXX", ChatColor.GOLD + pl.getConfig().getString("networkName")));
                    p.sendMessage(ChatColor.RED + "INCORRECT USAGE. CORRECT USAGE IS AS FOLLOWS");
                    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin reload");
                    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin list <assigned|unnassigned|open|closed> - Lists tickets of the selected type");
                    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin staffList <staff_name> - Lists tickets assigned to particular staff member");
                    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin assign <ticket_number> <staff_name> -  Displays details on specific ticket");
                    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin close <ticket_number>  -  Close ticket with id");
                    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin delete <ticket_number>  -  Assigns an unassigned ticket to yourself");
                    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin update <ticket_number> <your update>  -  Updates the staff steps data for this ticket (This can be seen by anyone who can view the ticket)");
                    p.sendMessage(ChatColor.GOLD + "/tbTicketAdmin stats");



                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("list")){
                        if (args[1].equalsIgnoreCase("unassigned")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='UNASSIGNED'");
                        }
                        else if (args[1].equalsIgnoreCase("assigned")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF!='UNASSIGNED'");
                        }
                        else if (args[1].equalsIgnoreCase("open")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='OPEN'");
                        }
                        else if (args[1].equalsIgnoreCase("closed")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STATUS='CLOSED'");
                        }
                        else if (args[1].equalsIgnoreCase("idea")) {
                            pl.staffList(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE TYPE='Idea'");
                        }
                    }
                    else if(args[0].equalsIgnoreCase("staffList")) {
                        pl.adminListStaff(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE STAFF='" + args[1] + "'", args[1]);
                    }
                    else if (args[0].equalsIgnoreCase("close") && p.hasPermission("tbtickets.admin")){
                        pl.adminCloseTicket(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("delete") && p.hasPermission("tbtickets.admin")){
                        pl.adminDeleteTicket(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("view") && p.hasPermission("tbtickets.admin")){
                        pl.staffViewTicket(p, Integer.parseInt(args[1]));
                    }
                    else if (args[0].equalsIgnoreCase("tp")) {
                        if (isNumeric(args[1])) {
                            pl.staffTP(p, "SELECT * FROM `" + pl.getConfig().getString("table") + "` WHERE ID='" + args[1] + "'", Integer.parseInt(args[1]));
                        }
                    }

                }

                else if (args.length >= 3 ){

                    if (args[0].equalsIgnoreCase("assign")) {
                        pl.adminAssign(p, Integer.parseInt(args[1]), args[2]);
                    }

                    else if(args[0].equalsIgnoreCase("update")) {
                        StringBuilder staffText = new StringBuilder();
                        for (int i = 2; i < args.length; i++) {
                            staffText.append(args[i] + " ");
                        }

                        pl.adminUpdate(p, Integer.parseInt(args[1]), staffText.toString());
                    }
                }
            }

        }
        return true;
    }





        public static boolean isNumeric (String str)
        {
            return str.matches("\\d+");
        }

    }