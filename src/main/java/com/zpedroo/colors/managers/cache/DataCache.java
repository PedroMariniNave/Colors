package com.zpedroo.colors.managers.cache;

import com.zpedroo.colors.objects.Color;
import com.zpedroo.colors.objects.PlayerData;
import com.zpedroo.colors.utils.FileUtils;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DataCache {

    private final Map<Player, PlayerData> playersData = new HashMap<>(64);
    private final Map<String, Color> colors = getColorsFromConfig();

    private Map<String, Color> getColorsFromConfig() {
        FileUtils.Files file = FileUtils.Files.CONFIG;
        Map<String, Color> ret = new HashMap<>(16);
        for (String name : FileUtils.get().getSection(file, "Colors")) {
            String colorSymbol = FileUtils.get().getString(file, "Colors." + name);

            ret.put(name, new Color(name, colorSymbol));
        }

        return ret;
    }
}