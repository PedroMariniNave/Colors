package com.zpedroo.colors.objects;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private Color selectedColor;
    private boolean update = false;

    public PlayerData(UUID uuid, Color selectedColor) {
        this.uuid = uuid;
        this.selectedColor = selectedColor;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public boolean isQueueUpdate() {
        return update;
    }

    public boolean isSelectedColor(Color color) {
        return selectedColor != null && selectedColor.equals(color);
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        this.update = true;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}