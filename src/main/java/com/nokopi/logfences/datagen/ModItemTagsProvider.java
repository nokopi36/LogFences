package com.nokopi.logfences.datagen;

import java.util.concurrent.CompletableFuture;

import com.nokopi.logfences.LogFences;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;

public class ModItemTagsProvider extends BlockTagCopyingItemTagProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagsProvider.TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, LogFences.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.copy(BlockItemTags.WOODEN_FENCES.block(), BlockItemTags.WOODEN_FENCES.item());
    }
}
