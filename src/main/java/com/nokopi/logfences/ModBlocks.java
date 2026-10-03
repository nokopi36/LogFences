package com.nokopi.logfences;

import java.util.EnumMap;
import java.util.Map;

import com.nokopi.logfences.block.LogFenceBlock;
import com.nokopi.logfences.block.LogFenceGateBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(LogFences.MODID);

    private static final Map<LogWood, DeferredBlock<LogFenceBlock>> FENCES = new EnumMap<>(LogWood.class);
    private static final Map<LogWood, DeferredBlock<LogFenceGateBlock>> FENCE_GATES = new EnumMap<>(LogWood.class);

    static {
        for (LogWood wood : LogWood.values()) {
            FENCES.put(wood, BLOCKS.registerBlock(wood.fenceName(),
                    p -> new LogFenceBlock(p, wood.flammable(), () -> ModItems.strippedFence(wood).get()),
                    p -> commonProperties(p, wood).sound(wood.woodType().soundType())));
            // ゲートの音（設置・開閉）は FenceGateBlock が WoodType から設定する
            FENCE_GATES.put(wood, BLOCKS.registerBlock(wood.fenceGateName(),
                    p -> new LogFenceGateBlock(wood.woodType(), p, wood.flammable(), () -> ModItems.strippedFenceGate(wood).get()),
                    p -> commonProperties(p, wood)));
        }
    }

    private ModBlocks() {
    }

    public static DeferredBlock<LogFenceBlock> fence(LogWood wood) {
        return FENCES.get(wood);
    }

    public static DeferredBlock<LogFenceGateBlock> fenceGate(LogWood wood) {
        return FENCE_GATES.get(wood);
    }

    // 性能はバニラの木のフェンス・ゲートと同じ。地図の色は原木に合わせる
    private static BlockBehaviour.Properties commonProperties(BlockBehaviour.Properties p, LogWood wood) {
        p.mapColor(wood.log().defaultMapColor())
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F, 3.0F);
        return wood.flammable() ? p.ignitedByLava() : p;
    }
}
