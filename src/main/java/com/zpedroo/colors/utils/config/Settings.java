package com.zpedroo.colors.utils.config;

import com.zpedroo.colors.utils.FileUtils;
import com.zpedroo.colors.utils.color.Colorize;

import java.util.List;

public class Settings {

    public static final String COMMAND = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.command");

    public static final List<String> ALIASES = FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Settings.aliases");

    public static final String PERMISSION = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.permission", null);

    public static final String PERMISSION_MESSAGE = Colorize.getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.permission-message", null));

    public static final List<String> CHANNELS = FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Settings.channels");
}