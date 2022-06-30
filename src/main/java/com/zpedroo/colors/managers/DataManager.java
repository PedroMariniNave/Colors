package com.zpedroo.colors.managers;

import com.zpedroo.colors.managers.cache.DataCache;
import com.zpedroo.colors.mysql.DBConnection;
import com.zpedroo.colors.objects.Color;
import com.zpedroo.colors.objects.PlayerData;
import org.bukkit.entity.Player;

public class DataManager {

    private static DataManager instance;
    public static DataManager getInstance() { return instance; }

    private final DataCache dataCache = new DataCache();

    public DataManager() {
        instance = this;
    }

    public PlayerData getPlayerData(Player player) {
        PlayerData data = dataCache.getPlayersData().get(player);
        if (data == null) {
            if (DBConnection.getInstance() != null) {
                data = DBConnection.getInstance().getDBManager().getPlayerDataFromDatabase(player);
            } else {
                data = new PlayerData(player.getUniqueId(), null);
            }
            dataCache.getPlayersData().put(player, data);
        }

        return data;
    }

    public Color getColorByName(String name) {
        return dataCache.getColors().get(name);
    }

    public void savePlayerData(Player player) {
        if (DBConnection.getInstance() == null) return;

        PlayerData data = dataCache.getPlayersData().get(player);
        if (data == null || !data.isQueueUpdate()) return;

        DBConnection.getInstance().getDBManager().savePlayerData(data);
        data.setUpdate(false);
    }

    public void saveAllPlayersData() {
        dataCache.getPlayersData().keySet().forEach(this::savePlayerData);
    }

    public DataCache getCache() {
        return dataCache;
    }
}