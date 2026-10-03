package com.nokopi.logfences;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import com.nokopi.logfences.block.FencePart;
import com.nokopi.logfences.block.GatePart;
import com.nokopi.logfences.item.StrippedLogFenceItem;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LogFences.MODID);

    private static final Map<LogWood, DeferredItem<BlockItem>> FENCES = new EnumMap<>(LogWood.class);
    private static final Map<LogWood, DeferredItem<BlockItem>> FENCE_GATES = new EnumMap<>(LogWood.class);
    private static final Map<LogWood, DeferredItem<StrippedLogFenceItem>> STRIPPED_FENCES = new EnumMap<>(LogWood.class);
    private static final Map<LogWood, DeferredItem<StrippedLogFenceItem>> STRIPPED_FENCE_GATES = new EnumMap<>(LogWood.class);

    static {
        for (LogWood wood : LogWood.values()) {
            FENCES.put(wood, ITEMS.registerSimpleBlockItem(wood.fenceName(), ModBlocks.fence(wood), fuel(wood)));
            FENCE_GATES.put(wood, ITEMS.registerSimpleBlockItem(wood.fenceGateName(), ModBlocks.fenceGate(wood), fuel(wood)));
            STRIPPED_FENCES.put(wood, ITEMS.registerItem(wood.strippedFenceName(),
                    p -> new StrippedLogFenceItem(ModBlocks.fence(wood).get(), FencePart.values(), p), fuel(wood)));
            STRIPPED_FENCE_GATES.put(wood, ITEMS.registerItem(wood.strippedFenceGateName(),
                    p -> new StrippedLogFenceItem(ModBlocks.fenceGate(wood).get(), GatePart.values(), p), fuel(wood)));
        }
    }

    private ModItems() {
    }

    public static DeferredItem<BlockItem> fence(LogWood wood) {
        return FENCES.get(wood);
    }

    public static DeferredItem<BlockItem> fenceGate(LogWood wood) {
        return FENCE_GATES.get(wood);
    }

    public static DeferredItem<StrippedLogFenceItem> strippedFence(LogWood wood) {
        return STRIPPED_FENCES.get(wood);
    }

    public static DeferredItem<StrippedLogFenceItem> strippedFenceGate(LogWood wood) {
        return STRIPPED_FENCE_GATES.get(wood);
    }

    // 燃料はバニラの木のフェンスと同じ（COOKING_TIME_WOOD_BLOCKS = 300 tick）。ネザーの木は燃料にならない
    private static UnaryOperator<Item.Properties> fuel(LogWood wood) {
        return wood.flammable() ? p -> p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS) : UnaryOperator.identity();
    }
}
