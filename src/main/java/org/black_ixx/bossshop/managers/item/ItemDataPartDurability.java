package org.black_ixx.bossshop.managers.item;

import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemDataPartDurability extends ItemDataPart {

    @Override
    public ItemStack transform(ItemStack item, String used_name, String argument) {
        short s = (short) InputReader.getInt(argument, -1);
        if (s == -1) {
            ClassManager.manager.getBugFinder().severe("Mistake in Config: '" + argument + "' is not a valid '" + used_name + "'. It needs to be a small number like '0', '2' or '5'. It also can be a number like '200' if you want to define the damage of tools, armor or weapons.");
            return item;
        }
        item.setDurability(s);
        return item;
    }

    @Override
    public int getPriority() {
        return PRIORITY_EARLY;
    }

    @Override
    public boolean removeSpaces() {
        return true;
    }

    @Override
    public String[] createNames() {
        return new String[]{"damage", "durability", "subid"};
    }

    @Override
    public List<String> read(ItemStack i, List<String> output) {
        if (i.getDurability() != 0) {
            output.add("durability:" + i.getDurability());
        }
        return output;
    }

    @Override
    public boolean isSimilar(ItemStack shop_item, ItemStack player_item, BSBuy buy, Player p) {
        return shop_item.getDurability() == player_item.getDurability();
    }

}
