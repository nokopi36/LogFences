package com.nokopi.logfences.datagen;

import java.util.Optional;

import com.nokopi.logfences.LogFences;
import com.nokopi.logfences.ModBlocks;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ModModelProvider extends ModelProvider {
    // assets/log_fences/models/block/template_log_fence_*.json（手書き）を型にする
    private static final ModelTemplate LOG_FENCE_POST = template("template_log_fence_post", "_post");
    private static final ModelTemplate LOG_FENCE_SIDE_UPPER = template("template_log_fence_side_upper", "_side_upper");
    private static final ModelTemplate LOG_FENCE_SIDE_LOWER = template("template_log_fence_side_lower", "_side_lower");
    private static final ModelTemplate LOG_FENCE_INVENTORY = template("template_log_fence_inventory", "_inventory");

    public ModModelProvider(PackOutput output) {
        super(output, LogFences.MODID);
    }

    private static ModelTemplate template(String name, String suffix) {
        return new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(LogFences.MODID, "block/" + name)),
                Optional.of(suffix), TextureSlot.SIDE, TextureSlot.END);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        createLogFence(blockModels, ModBlocks.OAK_LOG_FENCE.get(), Blocks.OAK_LOG);
    }

    private static void createLogFence(BlockModelGenerators blockModels, Block fence, Block log) {
        TextureMapping textures = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(log))
                .put(TextureSlot.END, TextureMapping.getBlockTexture(log, "_top"));

        MultiVariant post = BlockModelGenerators.plainVariant(LOG_FENCE_POST.create(fence, textures, blockModels.modelOutput));
        MultiVariant upper = BlockModelGenerators.plainVariant(LOG_FENCE_SIDE_UPPER.create(fence, textures, blockModels.modelOutput));
        MultiVariant lower = BlockModelGenerators.plainVariant(LOG_FENCE_SIDE_LOWER.create(fence, textures, blockModels.modelOutput));
        Identifier inventory = LOG_FENCE_INVENTORY.create(fence, textures, blockModels.modelOutput);

        MultiPartGenerator generator = MultiPartGenerator.multiPart(fence).with(post);
        // 横木は北向きのモデルを回転させて 4 方向に使う。木目の向きを保つため uvlock は使わない
        addSide(generator, BlockStateProperties.NORTH, upper, lower, null);
        addSide(generator, BlockStateProperties.EAST, upper, lower, BlockModelGenerators.Y_ROT_90);
        addSide(generator, BlockStateProperties.SOUTH, upper, lower, BlockModelGenerators.Y_ROT_180);
        addSide(generator, BlockStateProperties.WEST, upper, lower, BlockModelGenerators.Y_ROT_270);
        blockModels.blockStateOutput.accept(generator);
        blockModels.registerSimpleItemModel(fence, inventory);
    }

    private static void addSide(MultiPartGenerator generator, BooleanProperty direction, MultiVariant upper, MultiVariant lower,
            VariantMutator rotation) {
        MultiVariant rotatedUpper = rotation == null ? upper : upper.with(rotation);
        MultiVariant rotatedLower = rotation == null ? lower : lower.with(rotation);
        generator.with(BlockModelGenerators.condition().term(direction, true), rotatedUpper);
        generator.with(BlockModelGenerators.condition().term(direction, true), rotatedLower);
    }
}
