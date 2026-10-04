package com.nokopi.logfencesandgates.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

// フェンスとフェンスゲートで共通の、斧で部位の樹皮を剥ぐ処理
public final class BarkStripping {
    // 1 部位剥ぐごとの斧の耐久消費（バニラの原木と同じ）
    private static final int AXE_DAMAGE_PER_STRIP = 1;

    private BarkStripping() {
    }

    // バニラの斧と同じく、斧の BlockTransformer を持つアイテムを斧とみなす（Item.Properties#axe で付く）
    public static boolean isAxe(ItemStack stack) {
        Holder<BlockTransformer> transformer = stack.get(DataComponents.BLOCK_TRANSFORMER);
        return transformer != null && transformer.is(BlockTransformers.AXE);
    }

    public static boolean isStripped(BlockState state, StrippablePart part) {
        return state.getValue(part.strippedProperty());
    }

    // 部位を剥いで、バニラの原木と同じ音・ゲームイベント・斧の耐久消費を起こす
    public static void strip(BlockState state, StrippablePart part, Level level, BlockPos pos, Player player, ItemStack axe,
            InteractionHand hand) {
        BlockState stripped = state.setValue(part.strippedProperty(), true);
        level.setBlock(pos, stripped, Block.UPDATE_ALL_IMMEDIATE);
        level.playSound(player, pos, SoundEvents.AXE_STRIP.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, stripped));
        axe.hurtAndBreak(AXE_DAMAGE_PER_STRIP, player, hand.asEquipmentSlot());
    }

    public static BlockState withAllBark(BlockState state, StrippablePart[] parts) {
        for (StrippablePart part : parts) {
            state = state.setValue(part.strippedProperty(), false);
        }
        return state;
    }
}
