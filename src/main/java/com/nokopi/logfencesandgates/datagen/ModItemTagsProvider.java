package com.nokopi.logfencesandgates.datagen;

import java.util.concurrent.CompletableFuture;

import com.nokopi.logfencesandgates.LogFencesAndGates;
import com.nokopi.logfencesandgates.LogWood;
import com.nokopi.logfencesandgates.ModItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;

public class ModItemTagsProvider extends BlockTagCopyingItemTagProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagsProvider.TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, LogFencesAndGates.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.copy(BlockItemTags.WOODEN_FENCES.block(), BlockItemTags.WOODEN_FENCES.item());
        this.copy(BlockItemTags.FENCE_GATES.block(), BlockItemTags.FENCE_GATES.item());
        // バニラと同じく、ネザーの木のフェンス・ゲートは燃えない木材として扱う
        for (LogWood wood : LogWood.values()) {
            // 剥いだ版のアイテムはブロックを持たないので、アイテムのタグに直接入れる
            this.tag(BlockItemTags.WOODEN_FENCES.item()).add(ModItems.strippedFence(wood).getKey());
            this.tag(BlockItemTags.FENCE_GATES.item()).add(ModItems.strippedFenceGate(wood).getKey());
            if (!wood.flammable()) {
                this.tag(ItemTags.NON_FLAMMABLE_WOOD)
                        .add(ModItems.fence(wood).getKey())
                        .add(ModItems.fenceGate(wood).getKey())
                        .add(ModItems.strippedFence(wood).getKey())
                        .add(ModItems.strippedFenceGate(wood).getKey());
            }
        }
    }
}
