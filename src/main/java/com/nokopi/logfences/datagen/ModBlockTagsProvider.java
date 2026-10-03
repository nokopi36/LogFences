package com.nokopi.logfences.datagen;

import java.util.concurrent.CompletableFuture;

import com.nokopi.logfences.LogFences;
import com.nokopi.logfences.LogWood;
import com.nokopi.logfences.ModBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, LogFences.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        // wooden_fences: バニラの木の柵・フェンスゲートと繋がる（minecraft:fences にも含まれる）
        for (LogWood wood : LogWood.values()) {
            this.tag(BlockItemTags.WOODEN_FENCES.block()).add(ModBlocks.fence(wood).getKey());
            this.tag(BlockItemTags.FENCE_GATES.block()).add(ModBlocks.fenceGate(wood).getKey());
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.fence(wood).getKey()).add(ModBlocks.fenceGate(wood).getKey());
        }
    }
}
