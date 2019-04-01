package org.black_ixx.bossshop.managers.item;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.black_ixx.bossshop.megumi.managers.NbtContent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class ItemDataPartNbt extends ItemDataPart {

    private BossShop plugin = BossShop.getInstance();

    @Override
    public ItemStack transform(ItemStack item, String used_name, String argument) {

        String[] parts = argument.split("#", 2);
        if (parts.length != 2) {
            ClassManager.manager.getBugFinder().severe("Mistake in Config: '" + argument + "' is not a valid '" + used_name + "'. It has to look like this: '[Item/Data]#<Value>'. For example 'Data#String:Megumi'.");
            return item;
        }

        String type = parts[0].trim();
        String value = parts[1];

        if (type.equalsIgnoreCase("item")) {
            if (!plugin.getNbtManager().getItems().containsKey(value)) return item;

            ItemStack target = plugin.getNbtManager().getItems().get(value);
            item = mixNbt(item, target);
        }
        else if (type.equalsIgnoreCase("data")) {
            if (!plugin.getNbtManager().getNbtContents().containsKey(value)) return item;

            List<NbtContent> list = plugin.getNbtManager().getNbtContents().get(value);

            item = getBukkitItemStack(item);
            NbtCompound nbt = NbtFactory.asCompound(NbtFactory.fromItemTag(item));

            for (NbtContent content : list) {
                String t = content.getType();
                String n = content.getName();
                String v = content.getValue();

                if (t.equalsIgnoreCase("string")) {
                    nbt.put(n, v);
                }
                else if (t.equalsIgnoreCase("int")) {
                    int i = InputReader.getInt(v, -1);
                    if (i != -1) {
                        nbt.put(n, i);
                    }
                }
                else if (t.equalsIgnoreCase("double")) {
                    double d = InputReader.getDouble(v, -1);
                    if (d != -1) {
                        nbt.put(n, d);
                    }
                }
            }
        }

        return item;
    }

    private ItemStack mixNbt(ItemStack item, ItemStack target) {
        item = getBukkitItemStack(item);
        target = getBukkitItemStack(target);

        NbtCompound nbtA = NbtFactory.asCompound(NbtFactory.fromItemTag(item));
        NbtCompound nbtB = NbtFactory.asCompound(NbtFactory.fromItemTag(target));

        for (NbtBase base : nbtB) {
            if (base.getName().equals("display") || base.getName().equals("ench")) continue;
            nbtA.put(base);
        }

        NbtFactory.setItemTag(item, nbtA);

        return item;
    }

    private static ItemStack getBukkitItemStack(ItemStack item) {
        if (item == null) {
            return new ItemStack(Material.AIR);
        }
        return !item.getClass().getName().endsWith("CraftItemStack") ? MinecraftReflection.getBukkitItemStack(item) : item;
    }

    @Override
    public boolean isSimilar(ItemStack shop_item, ItemStack player_item, BSBuy buy, Player p) {
        NbtCompound nbtA = NbtFactory.asCompound(NbtFactory.fromItemTag(shop_item));
        NbtCompound nbtB = NbtFactory.asCompound(NbtFactory.fromItemTag(player_item));

        return nbtA.equals(nbtB);
    }

    @Override
    public List<String> read(ItemStack i, List<String> output) {
        output.add("nbt:Item#" + plugin.getNbtManager().getConvertUtil().convert(i));
        return output;
    }

    @Override
    public int getPriority() {
        return PRIORITY_NORMAL;
    }

    @Override
    public boolean removeSpaces() {
        return true;
    }

    @Override
    public String[] createNames() {
        return new String[]{"nbt"};
    }
}
