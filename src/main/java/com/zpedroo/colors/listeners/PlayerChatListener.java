package com.zpedroo.colors.listeners;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import com.zpedroo.colors.managers.DataManager;
import com.zpedroo.colors.objects.Color;
import com.zpedroo.colors.objects.PlayerData;
import com.zpedroo.colors.utils.config.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(ChatMessageEvent event) {
        if (!Settings.CHANNELS.contains(event.getChannel().getName()) || event.isCancelled()) return;

        PlayerData data = DataManager.getInstance().getPlayerData(event.getSender());
        if (data == null || data.getSelectedColor() == null) return;

        Color color = data.getSelectedColor();
        String message = event.getMessage();
        event.setMessage(color.getColorSymbol() + message);
    }
}