package com.nokopi.logfences;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * 原木のフェンス・フェンスゲートを作る木の種類（SPEC 2 章）。並び順はクリエイティブタブの並び順。
 * 表示名はバニラの原木・幹の名前に合わせる。
 */
public enum LogWood {
    OAK("oak_log", WoodType.OAK, true, "Oak Log", "オークの原木"),
    SPRUCE("spruce_log", WoodType.SPRUCE, true, "Spruce Log", "トウヒの原木"),
    BIRCH("birch_log", WoodType.BIRCH, true, "Birch Log", "シラカバの原木"),
    JUNGLE("jungle_log", WoodType.JUNGLE, true, "Jungle Log", "ジャングルの原木"),
    ACACIA("acacia_log", WoodType.ACACIA, true, "Acacia Log", "アカシアの原木"),
    DARK_OAK("dark_oak_log", WoodType.DARK_OAK, true, "Dark Oak Log", "ダークオークの原木"),
    MANGROVE("mangrove_log", WoodType.MANGROVE, true, "Mangrove Log", "マングローブの原木"),
    CHERRY("cherry_log", WoodType.CHERRY, true, "Cherry Log", "サクラの原木"),
    PALE_OAK("pale_oak_log", WoodType.PALE_OAK, true, "Pale Oak Log", "ペールオークの原木"),
    POPLAR("poplar_log", WoodType.POPLAR, true, "Poplar Log", "ポプラの原木"),
    CRIMSON("crimson_stem", WoodType.CRIMSON, false, "Crimson Stem", "真紅の幹"),
    WARPED("warped_stem", WoodType.WARPED, false, "Warped Stem", "歪んだ幹");

    private final String logName;
    private final WoodType woodType;
    private final boolean flammable;
    private final String englishName;
    private final String japaneseName;

    LogWood(String logName, WoodType woodType, boolean flammable, String englishName, String japaneseName) {
        this.logName = logName;
        this.woodType = woodType;
        this.flammable = flammable;
        this.englishName = englishName;
        this.japaneseName = japaneseName;
    }

    public String fenceName() {
        return this.logName + "_fence";
    }

    public String fenceGateName() {
        return this.logName + "_fence_gate";
    }

    public String strippedFenceName() {
        return "stripped_" + this.fenceName();
    }

    public String strippedFenceGateName() {
        return "stripped_" + this.fenceGateName();
    }

    public WoodType woodType() {
        return this.woodType;
    }

    // 燃える・燃料になる（ネザーの木は false）
    public boolean flammable() {
        return this.flammable;
    }

    public String englishName() {
        return this.englishName;
    }

    public String japaneseName() {
        return this.japaneseName;
    }

    // バニラに合わせ、原木は「樹皮を剥いだ」、ネザーの幹は「表皮を剥いだ」
    public String strippedJapaneseName() {
        return (this.logName.endsWith("_stem") ? "表皮" : "樹皮") + "を剥いだ" + this.japaneseName;
    }

    // バニラのブロック・アイテムは登録後に参照するため、メソッドで取り出す
    public Block log() {
        return switch (this) {
            case OAK -> Blocks.OAK_LOG;
            case SPRUCE -> Blocks.SPRUCE_LOG;
            case BIRCH -> Blocks.BIRCH_LOG;
            case JUNGLE -> Blocks.JUNGLE_LOG;
            case ACACIA -> Blocks.ACACIA_LOG;
            case DARK_OAK -> Blocks.DARK_OAK_LOG;
            case MANGROVE -> Blocks.MANGROVE_LOG;
            case CHERRY -> Blocks.CHERRY_LOG;
            case PALE_OAK -> Blocks.PALE_OAK_LOG;
            case POPLAR -> Blocks.POPLAR_LOG;
            case CRIMSON -> Blocks.CRIMSON_STEM;
            case WARPED -> Blocks.WARPED_STEM;
        };
    }

    public Block strippedLog() {
        return switch (this) {
            case OAK -> Blocks.STRIPPED_OAK_LOG;
            case SPRUCE -> Blocks.STRIPPED_SPRUCE_LOG;
            case BIRCH -> Blocks.STRIPPED_BIRCH_LOG;
            case JUNGLE -> Blocks.STRIPPED_JUNGLE_LOG;
            case ACACIA -> Blocks.STRIPPED_ACACIA_LOG;
            case DARK_OAK -> Blocks.STRIPPED_DARK_OAK_LOG;
            case MANGROVE -> Blocks.STRIPPED_MANGROVE_LOG;
            case CHERRY -> Blocks.STRIPPED_CHERRY_LOG;
            case PALE_OAK -> Blocks.STRIPPED_PALE_OAK_LOG;
            case POPLAR -> Blocks.STRIPPED_POPLAR_LOG;
            case CRIMSON -> Blocks.STRIPPED_CRIMSON_STEM;
            case WARPED -> Blocks.STRIPPED_WARPED_STEM;
        };
    }

    // クリエイティブの建築ブロックタブで、このアイテムの後ろに並べる
    public Item vanillaFenceGate() {
        return switch (this) {
            case OAK -> Items.OAK_FENCE_GATE;
            case SPRUCE -> Items.SPRUCE_FENCE_GATE;
            case BIRCH -> Items.BIRCH_FENCE_GATE;
            case JUNGLE -> Items.JUNGLE_FENCE_GATE;
            case ACACIA -> Items.ACACIA_FENCE_GATE;
            case DARK_OAK -> Items.DARK_OAK_FENCE_GATE;
            case MANGROVE -> Items.MANGROVE_FENCE_GATE;
            case CHERRY -> Items.CHERRY_FENCE_GATE;
            case PALE_OAK -> Items.PALE_OAK_FENCE_GATE;
            case POPLAR -> Items.POPLAR_FENCE_GATE;
            case CRIMSON -> Items.CRIMSON_FENCE_GATE;
            case WARPED -> Items.WARPED_FENCE_GATE;
        };
    }
}
