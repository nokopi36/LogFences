package com.nokopi.logfences.datagen;

import com.nokopi.logfences.LogFences;
import com.nokopi.logfences.LogWood;
import com.nokopi.logfences.ModBlocks;
import com.nokopi.logfences.ModCreativeTabs;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ModLanguageProvider {
    private ModLanguageProvider() {
    }

    public static final class English extends LanguageProvider {
        public English(PackOutput output) {
            super(output, LogFences.MODID, "en_us");
        }

        @Override
        protected void addTranslations() {
            for (LogWood wood : LogWood.values()) {
                this.addBlock(ModBlocks.fence(wood), wood.englishName() + " Fence");
                this.addBlock(ModBlocks.fenceGate(wood), wood.englishName() + " Fence Gate");
            }
            this.add(ModCreativeTabs.TAB_TITLE, "Log Fences");
        }
    }

    public static final class Japanese extends LanguageProvider {
        public Japanese(PackOutput output) {
            super(output, LogFences.MODID, "ja_jp");
        }

        @Override
        protected void addTranslations() {
            for (LogWood wood : LogWood.values()) {
                this.addBlock(ModBlocks.fence(wood), wood.japaneseName() + "のフェンス");
                this.addBlock(ModBlocks.fenceGate(wood), wood.japaneseName() + "のフェンスゲート");
            }
            this.add(ModCreativeTabs.TAB_TITLE, "Log Fences");
        }
    }
}
