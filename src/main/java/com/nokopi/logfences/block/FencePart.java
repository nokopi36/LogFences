package com.nokopi.logfences.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

// 樹皮を剥げる部位。横木は全方向（ゲートは左右の扉）まとめて 1 部位
public enum FencePart {
    POST(BooleanProperty.create("post_stripped")),
    UPPER_RAIL(BooleanProperty.create("upper_stripped")),
    LOWER_RAIL(BooleanProperty.create("lower_stripped"));

    // 表面のクリックを含めるための余裕
    private static final double EPSILON = 1.0E-4;
    // フェンスの柱は中央 4x4（6/16〜10/16）
    private static final double FENCE_POST_MIN = 6.0 / 16.0;
    private static final double FENCE_POST_MAX = 10.0 / 16.0;
    // ゲートの縦の木: 両端の柱（0〜2/16, 14〜16/16）と扉の内側の縦木（6〜10/16）
    private static final double GATE_END_POST_WIDTH = 2.0 / 16.0;
    private static final double GATE_INNER_POST_MIN = 6.0 / 16.0;
    private static final double GATE_INNER_POST_MAX = 10.0 / 16.0;
    // 塀に付いたゲートは全体が 3/16 下がる
    private static final double GATE_WALL_OFFSET_Y = 3.0 / 16.0;
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
     * フェンスのクリック位置（ブロック内の相対座標 0〜1）から部位を決める。
     * 当たり判定は横木の隙間も含むので、柱の範囲外は高さで近いほうの横木にする。
     */
    public static FencePart fromFenceHit(double x, double y, double z, boolean hasRails) {
        if (!hasRails) {
            return POST;
        }
        if (within(x, FENCE_POST_MIN, FENCE_POST_MAX) && within(z, FENCE_POST_MIN, FENCE_POST_MAX)) {
            return POST;
        }
        return railAt(y);
    }

    /**
     * 閉じたゲートのクリック位置から部位を決める。along はゲートの長さ方向の相対座標（0〜1）。
     * ゲートのモデルは長さ方向に左右対称なので、向きによる反転は考えなくてよい。
     */
    public static FencePart fromGateHit(double along, double y, boolean inWall) {
        boolean endPost = along <= GATE_END_POST_WIDTH + EPSILON || along >= 1.0 - GATE_END_POST_WIDTH - EPSILON;
        if (endPost || within(along, GATE_INNER_POST_MIN, GATE_INNER_POST_MAX)) {
            return POST;
        }
        return railAt(inWall ? y + GATE_WALL_OFFSET_Y : y);
    }

    private static boolean within(double value, double min, double max) {
        return value >= min - EPSILON && value <= max + EPSILON;
    }

    private static FencePart railAt(double y) {
        return y >= RAIL_BOUNDARY_Y ? UPPER_RAIL : LOWER_RAIL;
    }
}
