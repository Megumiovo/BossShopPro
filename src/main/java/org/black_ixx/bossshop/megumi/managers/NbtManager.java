package org.black_ixx.bossshop.megumi.managers;

import lombok.Getter;
import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.megumi.util.ItemConvertUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NbtManager {
    private BossShop plugin;
    @Getter
    private ItemConvertUtil convertUtil;

    @Getter
    private HashMap<String, ItemStack> items;
    @Getter
    private HashMap<String, List<NbtContent>> nbtContents;

    @Getter
    YamlConfiguration item;

    public NbtManager(BossShop plugin) {
        this.plugin = plugin;
        this.convertUtil = new ItemConvertUtil(plugin.getVersion());
        this.items = new HashMap<>();
        this.nbtContents = new HashMap<>();
    }

    public void init() {
        item = initFile("item.yml");
        YamlConfiguration nbt = initFile("nbt.yml");

        ConfigurationSection itemSection = item.getConfigurationSection("Items");
        if (itemSection != null) {
            for (String s : itemSection.getKeys(false)) {
                String convert = itemSection.getString(s);
                items.put(s, convertUtil.convert(convert));
            }
        }

        ConfigurationSection nbtSection = nbt.getConfigurationSection("Nbts");
        if (nbtSection != null) {
            for (String s : nbtSection.getKeys(false)) {
                List<NbtContent> list = new ArrayList<>();
                ConfigurationSection section = nbt.getConfigurationSection("Nbts." + s);
                if (section == null) continue;
                for (String s1 : section.getKeys(false)) {
                    String type = section.getString(s1 + ".Type");
                    String value = section.getString(s1 + ".Value");
                    list.add(new NbtContent(type, s1, value));
                }
                nbtContents.put(s, list);
            }
        }
    }

    private void saveNbt() {
        File file = new File(plugin.getDataFolder(), "item.yml");
        try {
            item.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private YamlConfiguration initFile(String name) {
        File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            copyFile(plugin.getResource(name), file);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    private void copyFile(InputStream inputStream, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] arrayOfByte = new byte['?'];
            int i;
            while ((i = inputStream.read(arrayOfByte)) > 0) {
                fileOutputStream.write(arrayOfByte, 0, i);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(String name, ItemStack itemStack) {
        String convert = convertUtil.convert(itemStack);
        item.set(String.format("Nbts.%s", name), convert);
        items.put(name, itemStack);
        saveNbt();
    }
}
