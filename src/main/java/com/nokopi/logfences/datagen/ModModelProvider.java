package com.nokopi.logfences.datagen;

import java.util.Optional;

import com.nokopi.logfences.LogFences;
import com.nokopi.logfences.ModBlocks;
import com.nokopi.logfences.block.FencePart;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
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

    private static final String STRIPPED_SUFFIX = "_stripped";

    public ModModelProvider(PackOutput output) {
        super(output, LogFences.MODID);
    }

    private static ModelTemplate template(String name, String suffix) {
        return new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(LogFences.MODID, "block/" + name)),
                Optional.of(suffix), TextureSlot.SIDE, TextureSlot.END);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        createLogFence(blockModels, ModBlocks.OAK_LOG_FENCE.get(), Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
    }

    private static TextureMapping logTextures(Block log) {
        return new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(log))
                .put(TextureSlot.END, TextureMapping.getBlockTexture(log, "_top"));
    }

    private static void createLogFence(BlockModelGenerators blockModels, Block fence, Block log, Block strippedLog) {
        TextureMapping bark = logTextures(log);
        TextureMapping stripped = logTextures(strippedLog);

        MultiPartGenerator generator = MultiPartGenerator.multiPart(fence);
        addPost(blockModels, generator, fence, bark, false);
        addPost(blockModels, generator, fence, stripped, true);
        addRails(blockModels, generator, fence, LOG_FENCE_SIDE_UPPER, FencePart.UPPER_RAIL, bark, false);
        addRails(blockModels, generator, fence, LOG_FENCE_SIDE_UPPER, FencePart.UPPER_RAIL, stripped, true);
        addRails(blockModels, generator, fence, LOG_FENCE_SIDE_LOWER, FencePart.LOWER_RAIL, bark, false);
        addRails(blockModels, generator, fence, LOG_FENCE_SIDE_LOWER, FencePart.LOWER_RAIL, stripped, true);
        blockModels.blockStateOutput.accept(generator);

        // インベントリでは樹皮付きの見た目
        blockModels.registerSimpleItemModel(fence, LOG_FENCE_INVENTORY.create(fence, bark, blockModels.modelOutput));
    }

    private static Identifier createModel(BlockModelGenerators blockModels, ModelTemplate template, Block fence,
            TextureMapping textures, boolean isStripped) {
        return isStripped
                ? template.createWithSuffix(fence, STRIPPED_SUFFIX, textures, blockModels.modelOutput)
                : template.create(fence, textures, blockModels.modelOutput);
    }

    private static void addPost(BlockModelGenerators blockModels, MultiPartGenerator generator, Block fence,
            TextureMapping textures, boolean isStripped) {
        MultiVariant post = BlockModelGenerators.plainVariant(createModel(blockModels, LOG_FENCE_POST, fence, textures, isStripped));
        generator.with(BlockModelGenerators.condition().term(FencePart.POST.strippedProperty(), isStripped), post);
    }

    // 横木は北向きのモデルを回転させて 4 方向に使う。木目の向きを保つため uvlock は使わない
    private static void addRails(BlockModelGenerators blockModels, MultiPartGenerator generator, Block fence,
            ModelTemplate template, FencePart part, TextureMapping textures, boolean isStripped) {
        MultiVariant rail = BlockModelGenerators.plainVariant(createModel(blockModels, template, fence, textures, isStripped));
        addRail(generator, BlockStateProperties.NORTH, part, isStripped, rail);
        addRail(generator, BlockStateProperties.EAST, part, isStripped, rail.with(BlockModelGenerators.Y_ROT_90));
        addRail(generator, BlockStateProperties.SOUTH, part, isStripped, rail.with(BlockModelGenerators.Y_ROT_180));
        addRail(generator, BlockStateProperties.WEST, part, isStripped, rail.with(BlockModelGenerators.Y_ROT_270));
    }

    private static void addRail(MultiPartGenerator generator, BooleanProperty direction, FencePart part, boolean isStripped,
            MultiVariant rail) {
        generator.with(BlockModelGenerators.condition()
                .term(direction, true)
                .term(part.strippedProperty(), isStripped), rail);
    }
}
