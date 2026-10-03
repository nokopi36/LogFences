package com.nokopi.logfences;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LogFences.MODID);

    // 燃料はバニラの木の柵と同じ（COOKING_TIME_WOOD_BLOCKS = 300 tick）
    public static final DeferredItem<BlockItem> OAK_LOG_FENCE = ITEMS.registerSimpleBlockItem("oak_log_fence",
            ModBlocks.OAK_LOG_FENCE,
            p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));

    public static final DeferredItem<BlockItem> OAK_LOG_FENCE_GATE = ITEMS.registerSimpleBlockItem("oak_log_fence_gate",
            ModBlocks.OAK_LOG_FENCE_GATE,
            p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS));

    private ModItems() {
    }
}
