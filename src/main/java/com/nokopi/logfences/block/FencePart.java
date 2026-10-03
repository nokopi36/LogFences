package com.nokopi.logfences.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

// フェンスの樹皮を剥げる部位。横木は全方向まとめて 1 部位
public enum FencePart implements StrippablePart {
    POST(BooleanProperty.create("post_stripped")),
    UPPER_RAIL(BooleanProperty.create("upper_stripped")),
    LOWER_RAIL(BooleanProperty.create("lower_stripped"));

    // 表面のクリックを含めるための余裕
    private static final double EPSILON = 1.0E-4;
    // 柱は中央 4x4（6/16〜10/16）
    private static final double POST_MIN = 6.0 / 16.0;
    private static final double POST_MAX = 10.0 / 16.0;
    // 下の横木 y 6〜9/16 と上の横木 y 12〜15/16 の中間
    private static final double RAIL_BOUNDARY_Y = 10.5 / 16.0;

    private final BooleanProperty strippedProperty;

    FencePart(BooleanProperty strippedProperty) {
        this.strippedProperty = strippedProperty;
    }

    @Override
    public BooleanProperty strippedProperty() {
        return this.strippedProperty;
    }

    /**
     * フェンスのクリック位置（ブロック内の相対座標 0〜1）から部位を決める。
     * 当たり判定は横木の隙間も含むので、柱の範囲外は高さで近いほうの横木にする。
     */
    public static FencePart fromHit(double x, double y, double z, boolean hasRails) {
        if (!hasRails) {
            return POST;
        }
        if (within(x) && within(z)) {
            return POST;
        }
        return y >= RAIL_BOUNDARY_Y ? UPPER_RAIL : LOWER_RAIL;
    }

    private static boolean within(double value) {
        return value >= POST_MIN - EPSILON && value <= POST_MAX + EPSILON;
    }
}
