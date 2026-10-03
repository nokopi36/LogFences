package com.nokopi.logfences.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

// フェンスゲートの樹皮を剥げる部位。左右の扉は同じ見た目にするため、扉の部位は左右まとめて 1 部位
public enum GatePart implements StrippablePart {
    // 柱・上下の横木はフェンスと同じ名前の状態を使う
    POST(FencePart.POST.strippedProperty()),
    INNER_POST(BooleanProperty.create("inner_stripped")),
    UPPER_RAIL(FencePart.UPPER_RAIL.strippedProperty()),
    LOWER_RAIL(FencePart.LOWER_RAIL.strippedProperty());

    // 表面のクリックを含めるための余裕
    private static final double EPSILON = 1.0E-4;
    // 両端の柱（0〜2/16, 14〜16/16）と扉の内側の縦木（6〜10/16）
    private static final double END_POST_WIDTH = 2.0 / 16.0;
    private static final double INNER_POST_MIN = 6.0 / 16.0;
    private static final double INNER_POST_MAX = 10.0 / 16.0;
    // 塀に付いたゲートは全体が 3/16 下がる
    private static final double WALL_OFFSET_Y = 3.0 / 16.0;
    // 下の横木 y 6〜9/16 と上の横木 y 12〜15/16 の中間
    private static final double RAIL_BOUNDARY_Y = 10.5 / 16.0;

    private final BooleanProperty strippedProperty;

    GatePart(BooleanProperty strippedProperty) {
        this.strippedProperty = strippedProperty;
    }

    @Override
    public BooleanProperty strippedProperty() {
        return this.strippedProperty;
    }

    /**
     * 閉じたゲートのクリック位置から部位を決める。along はゲートの長さ方向の相対座標（0〜1）。
     * ゲートのモデルは長さ方向に左右対称なので、向きによる反転は考えなくてよい。
     */
    public static GatePart fromHit(double along, double y, boolean inWall) {
        if (along <= END_POST_WIDTH + EPSILON || along >= 1.0 - END_POST_WIDTH - EPSILON) {
            return POST;
        }
        if (along >= INNER_POST_MIN - EPSILON && along <= INNER_POST_MAX + EPSILON) {
            return INNER_POST;
        }
        double railY = inWall ? y + WALL_OFFSET_Y : y;
        return railY >= RAIL_BOUNDARY_Y ? UPPER_RAIL : LOWER_RAIL;
    }
}
