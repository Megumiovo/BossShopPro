package org.black_ixx.bossshop.commands.sub;

import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.commands.MegumiCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand extends MegumiCommand {

    private BossShop plugin = BossShop.getInstance();

    @Override
    public void perform(CommandSender sender, String[] Strings) {
        if (Strings.length != 2) {
            sender.sendMessage(ChatColor.RED + "Format: /BossShop nbt <name> - Save the nbt of this item");
            return;
        }

        Player player = getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getItemMeta() == null) {
            sender.sendMessage("§cYou have no items in your hand!");
            return;
        }

        String name = Strings[1];

        plugin.getNbtManager().add(name, item);

        sender.sendMessage("§bAdd nbt successfully!");
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String getPermission() {
        return "BossShop.nbt";
    }
}
