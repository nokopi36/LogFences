package com.nokopi.logfencesandgates.block;

// フェンス・フェンスゲート共通の数値
public final class LogFenceProperties {
    // バニラの木のフェンス・ゲートと同じ値（FireBlock#bootStrap の setFlammable(OAK_FENCE, 5, 20)）
    public static final int FIRE_SPREAD_SPEED = 5;
    public static final int FLAMMABILITY = 20;

    private LogFenceProperties() {
    }
}
