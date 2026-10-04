package com.nokopi.logfencesandgates.item;

import java.util.Map;

import com.nokopi.logfencesandgates.block.StrippablePart;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/**
 * 樹皮を剥いだ版のフェンス・ゲート。ブロックは普通の版と共通で、置くと全部位が剥がれた状態になる。
 */
public class StrippedLogFenceItem extends BlockItem {
    private final StrippablePart[] parts;

    public StrippedLogFenceItem(Block block, StrippablePart[] parts, Item.Properties properties) {
        super(block, properties);
        this.parts = parts;
    }

    @Override
    protected @Nullable BlockState getPlacementState(BlockPlaceContext context) {
        BlockState state = super.getPlacementState(context);
        return state == null ? null : withAllStripped(state, this.parts);
    }

    public static BlockState withAllStripped(BlockState state, StrippablePart[] parts) {
        for (StrippablePart part : parts) {
            state = state.setValue(part.strippedProperty(), true);
        }
        return state;
    }

    public static boolean isAllStripped(BlockState state, StrippablePart[] parts) {
        for (StrippablePart part : parts) {
            if (!state.getValue(part.strippedProperty())) {
                return false;
            }
        }
        return true;
    }

    // ブロック → アイテムの対応は普通の版のまま（上書きすると普通の版の asItem などが壊れる）
    @Override
    public void registerBlocks(Map<Block, Item> map, Item item) {
    }
}
