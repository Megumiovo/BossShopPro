package org.black_ixx.bossshop.commands.sub;

import org.black_ixx.bossshop.commands.MainCommands;
import org.black_ixx.bossshop.commands.MegumiCommand;
import org.black_ixx.bossshop.core.BSShop;
import org.black_ixx.bossshop.managers.ClassManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenCommand extends MegumiCommand {

    @Override
    public void perform(CommandSender sender, String[] Strings) {
        if (Strings.length < 2) {
            sender.sendMessage(ChatColor.RED + "Format: /BossShop open <Shop> <Player> [input] - Opens named shop for the named player");
            return;
        }

        String shopName = Strings[1].toLowerCase();
        BSShop shop = ClassManager.manager.getShops().getShop(shopName);
        String name = Strings[2];
        Player p = Bukkit.getPlayerExact(name);
        String argument = Strings.length > 3 ? Strings[3] : null;

        if (p == null) {
            p = Bukkit.getPlayer(name);
        }

        if (p == null) {
            ClassManager.manager.getMessageHandler().sendMessage("Main.PlayerNotFound", sender, name, null, shop, null, null);
            return;
        }

        if (shop == null) {
            ClassManager.manager.getMessageHandler().sendMessage("Main.ShopNotExisting", sender, null, p, shop, null, null);
            return;
        }

        MainCommands.playerCommandOpenShop(sender, p, shopName, argument);
        if (p != sender) {
            ClassManager.manager.getMessageHandler().sendMessage("Main.OpenShopOtherPlayer", sender, null, p, shop, null, null);
        }
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return null;
    }
}
