package com.nokopi.logfences.datagen;

import com.nokopi.logfences.LogWood;
import com.nokopi.logfences.ModItems;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

// 26.x ではレシピはリロード可能なレジストリとして生成する（DataGenerators で RecipeProvider.asBootstrap に渡す）
public class ModRecipeProvider extends RecipeProvider {
    // SPEC 4 章。バニラ（板材 4 + 棒 2 → フェンス 3 個）を原木 1 = 板材 4 で換算
    private static final int FENCE_COUNT = 12;
    // バニラ（板材 2 + 棒 4 → ゲート 1 個）を同じく換算
    private static final int FENCE_GATE_COUNT = 4;

    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    protected void buildRecipes() {
        for (LogWood wood : LogWood.values()) {
            logFence(ModItems.fence(wood).get(), wood.log());
            logFenceGate(ModItems.fenceGate(wood).get(), wood.log());
            logFence(ModItems.strippedFence(wood).get(), wood.strippedLog());
            logFenceGate(ModItems.strippedFenceGate(wood).get(), wood.strippedLog());
        }
    }

    private void logFence(ItemLike result, ItemLike log) {
        this.shaped(RecipeCategory.DECORATIONS, result, FENCE_COUNT)
                .define('W', log)
                .define('#', Items.STICK)
                .pattern("W#W")
                .pattern("W#W")
                .group("log_fence")
                .unlockedBy(getHasName(log), this.has(log))
                .save(this.output);
    }

    private void logFenceGate(ItemLike result, ItemLike log) {
        this.shaped(RecipeCategory.REDSTONE, result, FENCE_GATE_COUNT)
                .define('#', Items.STICK)
                .define('W', log)
                .pattern("#W#")
                .pattern("#W#")
                .group("log_fence_gate")
                .unlockedBy(getHasName(log), this.has(log))
                .save(this.output);
    }
}
