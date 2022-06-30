package com.zpedroo.colors.utils.menu;

import com.zpedroo.colors.managers.DataManager;
import com.zpedroo.colors.objects.Color;
import com.zpedroo.colors.objects.PlayerData;
import com.zpedroo.colors.utils.FileUtils;
import com.zpedroo.colors.utils.builder.InventoryBuilder;
import com.zpedroo.colors.utils.builder.InventoryUtils;
import com.zpedroo.colors.utils.builder.ItemBuilder;
import com.zpedroo.colors.utils.color.Colorize;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Menus extends InventoryUtils {

    private static Menus instance;
    public static Menus getInstance() { return instance; }

    public Menus() {
        instance = this;
    }

    public void openColorsMenu(Player player) {
        FileUtils.Files file = FileUtils.Files.COLORS;

        String title = Colorize.getColored(FileUtils.get().getString(file, "Inventory.title"));
        int size = FileUtils.get().getInt(file, "Inventory.size");

        InventoryBuilder inventory = new InventoryBuilder(title, size);
        PlayerData data = DataManager.getInstance().getPlayerData(player);

        String selectColorStatus = Colorize.getColored(FileUtils.get().getString(file, "Status-Translations.selected"));
        String unselectColorStatus = Colorize.getColored(FileUtils.get().getString(file, "Status-Translations.unselected"));

        for (String items : FileUtils.get().getSection(file, "Inventory.items")) {
            Color color = DataManager.getInstance().getColorByName(items);
            ItemStack item = ItemBuilder.build(FileUtils.get().getFile(file).get(), "Inventory.items." + items, new String[]{
                    "{symbol}",
                    "{status}"
            }, new String[]{
                    color == null ? "" : color.getColorSymbol(),
                    data.isSelectedColor(color) ? selectColorStatus : unselectColorStatus
            }).build();
            int slot = FileUtils.get().getInt(file, "Inventory.items." + items + ".slot");

            inventory.addItem(item, slot, () -> {
                if (color == null) return;

                player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1f, 2f);

                if (data.isSelectedColor(color)) {
                    data.setSelectedColor(null);
                } else {
                    data.setSelectedColor(color);
                }

                openColorsMenu(player);
            }, ActionType.ALL_CLICKS);
        }

        inventory.open(player);
    }
}