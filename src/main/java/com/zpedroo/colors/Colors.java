package com.zpedroo.colors;

import com.zpedroo.colors.commands.ColorsCmd;
import com.zpedroo.colors.listeners.PlayerChatListener;
import com.zpedroo.colors.listeners.PlayerGeneralListeners;
import com.zpedroo.colors.managers.DataManager;
import com.zpedroo.colors.mysql.DBConnection;
import com.zpedroo.colors.utils.FileUtils;
import com.zpedroo.colors.utils.menu.Menus;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;

import static com.zpedroo.colors.utils.config.Settings.*;

public class Colors extends JavaPlugin {

    private static Colors instance;
    public static Colors get() { return instance; }

    public void onEnable() {
        instance = this;

        new FileUtils(this);
        new DataManager();
        new Menus();

        if (isMySQLEnabled(getConfig())) {
            new DBConnection(getConfig());
        }

        registerListeners();
        registerCommand(COMMAND, ALIASES, PERMISSION, PERMISSION_MESSAGE, new ColorsCmd());
    }

    public void onDisable() {
        if (!isMySQLEnabled(getConfig())) return;

        try {
            DataManager.getInstance().saveAllPlayersData();
            DBConnection.getInstance().closeConnection();
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, "An error occurred while trying to save data!");
            ex.printStackTrace();
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerGeneralListeners(), this);
    }

    private void registerCommand(String command, List<String> aliases, String permission, String permissionMessage, CommandExecutor commandExecutor) {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            PluginCommand pluginCmd = constructor.newInstance(command, this);
            pluginCmd.setAliases(aliases);
            pluginCmd.setExecutor(commandExecutor);
            if (permission != null && !permission.isEmpty()) pluginCmd.setPermission(permission);
            if (permissionMessage != null && !permissionMessage.isEmpty()) pluginCmd.setPermission(permissionMessage);

            Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            commandMap.register(getName().toLowerCase(), pluginCmd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean isMySQLEnabled(FileConfiguration file) {
        return file.getBoolean("MySQL.enabled", false);
    }
}