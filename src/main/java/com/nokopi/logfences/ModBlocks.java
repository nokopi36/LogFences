package com.nokopi.logfences;

import com.nokopi.logfences.block.LogFenceBlock;
import com.nokopi.logfences.block.LogFenceGateBlock;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(LogFences.MODID);

    // 性能はバニラの Blocks.OAK_FENCE と同じ。地図の色は原木の側面に合わせる
    public static final DeferredBlock<LogFenceBlock> OAK_LOG_FENCE = BLOCKS.registerBlock("oak_log_fence",
            p -> new LogFenceBlock(p, true),
            p -> p.mapColor(Blocks.OAK_LOG.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava());

    // 性能はバニラの Blocks.OAK_FENCE_GATE と同じ。音（設置・開閉）は WoodType から決まる
    public static final DeferredBlock<LogFenceGateBlock> OAK_LOG_FENCE_GATE = BLOCKS.registerBlock("oak_log_fence_gate",
            p -> new LogFenceGateBlock(WoodType.OAK, p, true),
            p -> p.mapColor(Blocks.OAK_LOG.defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .ignitedByLava());

    private ModBlocks() {
    }
}
