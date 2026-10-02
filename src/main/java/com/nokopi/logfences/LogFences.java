package com.nokopi.logfences;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(LogFences.MODID)
public class LogFences {
    public static final String MODID = "log_fences";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LogFences(IEventBus modEventBus, ModContainer modContainer) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
    }
}
