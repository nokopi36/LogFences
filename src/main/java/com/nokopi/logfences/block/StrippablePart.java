package com.nokopi.logfences.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

// 樹皮を剥げる部位。フェンス（FencePart）とゲート（GatePart）で共通
public interface StrippablePart {
    BooleanProperty strippedProperty();
}
