package org.black_ixx.bossshop.commands.sub;

import org.black_ixx.bossshop.commands.MegumiCommand;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.item.ItemDataPart;
import org.black_ixx.bossshop.misc.Misc;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ReadCommand extends MegumiCommand {

    @Override
    public void perform(CommandSender sender, String[] Strings) {
        Player p = (Player) sender;
        ItemStack item = Misc.getItemInMainHand(p);
        if (item == null || item.getType() == Material.AIR) {
            ClassManager.manager.getMessageHandler().sendMessage("Main.NeedItemInHand", sender);
            return;
        }
        List<String> itemData = ItemDataPart.readItem(item);
        ClassManager.manager.getItemDataStorage().addItemData(p.getName(), itemData);
        ClassManager.manager.getMessageHandler().sendMessage("Main.PrintedItemInfo", sender);
        for (String line : itemData) {
            sender.sendMessage("- " + line);
        }
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String getPermission() {
        return "BossShop.read";
    }
}
