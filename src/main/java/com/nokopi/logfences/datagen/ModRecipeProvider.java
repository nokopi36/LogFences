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
    // SPEC 4 章。バニラの「板材 4 + 棒 2 → フェンス 3 個」「板材 2 + 棒 4 → ゲート 1 個」を、
    // 原木 1 個 = 板材 LogWood#planksPerLog 枚で換算する（原木 12 個・4 個、竹ブロック 6 個・2 個）
    private static final int VANILLA_FENCES_PER_PLANK_RECIPE = 3;
    private static final int VANILLA_FENCE_GATES_PER_PLANK_RECIPE = 1;

    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    protected void buildRecipes() {
        for (LogWood wood : LogWood.values()) {
            int fenceCount = fenceCount(wood);
            int fenceGateCount = fenceGateCount(wood);
            logFence(ModItems.fence(wood).get(), wood.log(), fenceCount);
            logFenceGate(ModItems.fenceGate(wood).get(), wood.log(), fenceGateCount);
            logFence(ModItems.strippedFence(wood).get(), wood.strippedLog(), fenceCount);
            logFenceGate(ModItems.strippedFenceGate(wood).get(), wood.strippedLog(), fenceGateCount);
        }
    }

    // フェンスのレシピは原木 4 個 = 板材 4 * planksPerLog 枚分で、バニラのレシピ planksPerLog 回分
    public static int fenceCount(LogWood wood) {
        return VANILLA_FENCES_PER_PLANK_RECIPE * wood.planksPerLog();
    }

    // ゲートのレシピは原木 2 個 = 板材 2 * planksPerLog 枚分で、バニラのレシピ planksPerLog 回分
    public static int fenceGateCount(LogWood wood) {
        return VANILLA_FENCE_GATES_PER_PLANK_RECIPE * wood.planksPerLog();
    }

    private void logFence(ItemLike result, ItemLike log, int count) {
        this.shaped(RecipeCategory.DECORATIONS, result, count)
                .define('W', log)
                .define('#', Items.STICK)
                .pattern("W#W")
                .pattern("W#W")
                .group("log_fence")
                .unlockedBy(getHasName(log), this.has(log))
                .save(this.output);
    }

    private void logFenceGate(ItemLike result, ItemLike log, int count) {
        this.shaped(RecipeCategory.REDSTONE, result, count)
                .define('#', Items.STICK)
                .define('W', log)
                .pattern("#W#")
                .pattern("#W#")
                .group("log_fence_gate")
                .unlockedBy(getHasName(log), this.has(log))
                .save(this.output);
    }
}
