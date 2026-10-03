package com.nokopi.logfences.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

// 樹皮を剥げる部位。横木は全方向まとめて 1 部位
public enum FencePart {
    POST(BooleanProperty.create("post_stripped")),
    UPPER_RAIL(BooleanProperty.create("upper_stripped")),
    LOWER_RAIL(BooleanProperty.create("lower_stripped"));

    // 柱は中央 4x4（6/16〜10/16）。少し余裕を持たせて柱の表面のクリックを柱に含める
    private static final double POST_MIN = 6.0 / 16.0 - 1.0E-4;
    private static final double POST_MAX = 10.0 / 16.0 + 1.0E-4;
    // 下の横木 y 6〜9/16 と上の横木 y 12〜15/16 の中間
    private static final double RAIL_BOUNDARY_Y = 10.5 / 16.0;

    private final BooleanProperty strippedProperty;

    FencePart(BooleanProperty strippedProperty) {
        this.strippedProperty = strippedProperty;
    }

    public BooleanProperty strippedProperty() {
        return this.strippedProperty;
    }

    /**
     * クリック位置（ブロック内の相対座標 0〜1）から部位を決める。
     * 当たり判定は横木の隙間も含むので、柱の範囲外は高さで近いほうの横木にする。
     */
    public static FencePart fromLocalHit(double x, double y, double z, boolean hasRails) {
        if (!hasRails) {
            return POST;
        }
        boolean insidePost = x >= POST_MIN && x <= POST_MAX && z >= POST_MIN && z <= POST_MAX;
        if (insidePost) {
            return POST;
        }
        return y >= RAIL_BOUNDARY_Y ? UPPER_RAIL : LOWER_RAIL;
    }
}
