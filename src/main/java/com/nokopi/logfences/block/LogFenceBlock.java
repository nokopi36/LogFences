package com.nokopi.logfences.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class LogFenceBlock extends FenceBlock {
    // バニラの木のフェンスと同じ値（FireBlock#bootStrap の setFlammable(OAK_FENCE, 5, 20)）
    private static final int FIRE_SPREAD_SPEED = 5;
    private static final int FLAMMABILITY = 20;
    // 1 部位剥ぐごとの斧の耐久消費（バニラの原木と同じ）
    private static final int AXE_DAMAGE_PER_STRIP = 1;

    private final boolean flammable;

    public LogFenceBlock(Properties properties, boolean flammable) {
        super(properties);
        this.flammable = flammable;
        BlockState state = this.defaultBlockState();
        for (FencePart part : FencePart.values()) {
            state = state.setValue(part.strippedProperty(), false);
        }
        this.registerDefaultState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        for (FencePart part : FencePart.values()) {
            builder.add(part.strippedProperty());
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hitResult) {
        if (!isAxe(stack)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        Vec3 local = hitResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        FencePart part = FencePart.fromLocalHit(local.x, local.y, local.z, hasRails(state));
        if (state.getValue(part.strippedProperty())) {
            // 剥がれ済みなら何もせず、通常の右クリック（リードを結ぶなど）に回す
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        BlockState stripped = state.setValue(part.strippedProperty(), true);
        level.setBlock(pos, stripped, Block.UPDATE_ALL_IMMEDIATE);
        level.playSound(player, pos, SoundEvents.AXE_STRIP.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, stripped));
        stack.hurtAndBreak(AXE_DAMAGE_PER_STRIP, player, hand.asEquipmentSlot());
        return InteractionResult.SUCCESS;
    }

    // バニラの斧と同じく、斧の BlockTransformer を持つアイテムを斧とみなす（Item.Properties#axe で付く）
    private static boolean isAxe(ItemStack stack) {
        Holder<BlockTransformer> transformer = stack.get(DataComponents.BLOCK_TRANSFORMER);
        return transformer != null && transformer.is(BlockTransformers.AXE);
    }

    private static boolean hasRails(BlockState state) {
        return state.getValue(NORTH) || state.getValue(EAST) || state.getValue(SOUTH) || state.getValue(WEST);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return this.flammable ? FLAMMABILITY : 0;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return this.flammable ? FIRE_SPREAD_SPEED : 0;
    }
}
