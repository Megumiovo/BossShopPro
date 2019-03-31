package org.black_ixx.bossshop.commands;

import org.black_ixx.bossshop.commands.sub.*;
import org.black_ixx.bossshop.managers.ClassManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MainCommands implements CommandExecutor {

    private HelpCommand help;
    private HashMap<String, MegumiCommand> commands;

    public MainCommands() {
        this.help = new HelpCommand();
        this.commands = new HashMap<>();
        this.commands.put("help", new HelpCommand());
        this.commands.put("reload", new ReloadCommand());
        this.commands.put("read", new ReadCommand());
        this.commands.put("simulate", new SimulateCommand());
        this.commands.put("close", new CloseCommand());
        this.commands.put("open", new OpenCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        MegumiCommand cmd = help;
        if (strings.length >= 1 && commands.containsKey(strings[0])) {
            cmd = commands.get(strings[0]);
        }
        else {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                String shop = ClassManager.manager.getSettings().getMainShop();
                if (strings.length != 0) {
                    shop = strings[0].toLowerCase();
                }
                String argument = strings.length > 1 ? strings[1] : null;
                playerCommandOpenShop(sender, p, shop, argument);
                return true;
            }
        }

        cmd.execute(sender, strings);
        return false;
    }

    public static boolean playerCommandOpenShop(CommandSender sender, Player target, String shop, String argument) {
        if (sender == target) {
            if (!(sender.hasPermission("BossShop.open") || sender.hasPermission("BossShop.open.command") || sender.hasPermission("BossShop.open.command." + shop))) {
                ClassManager.manager.getMessageHandler().sendMessage("Main.NoPermission", sender);
                return false;
            }
        } else {
            if (!sender.hasPermission("BossShop.open.other")) {
                ClassManager.manager.getMessageHandler().sendMessage("Main.NoPermission", sender);
                return false;
            }
        }
        if (argument != null) {
            ClassManager.manager.getPlayerDataHandler().enteredInput(target, argument);
        }
        if (ClassManager.manager == null) {
            return false;
        }
        if (ClassManager.manager.getShops() == null) {
            return false;
        }
        ClassManager.manager.getShops().openShop(target, shop);
        return true;
    }
}
