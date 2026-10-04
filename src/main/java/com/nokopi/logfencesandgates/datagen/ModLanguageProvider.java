package com.nokopi.logfencesandgates.datagen;

import com.nokopi.logfencesandgates.LogFencesAndGates;
import com.nokopi.logfencesandgates.LogWood;
import com.nokopi.logfencesandgates.ModBlocks;
import com.nokopi.logfencesandgates.ModCreativeTabs;
import com.nokopi.logfencesandgates.ModItems;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ModLanguageProvider {
    private ModLanguageProvider() {
    }

    public static final class English extends LanguageProvider {
        public English(PackOutput output) {
            super(output, LogFencesAndGates.MODID, "en_us");
        }

        @Override
        protected void addTranslations() {
            for (LogWood wood : LogWood.values()) {
                this.addBlock(ModBlocks.fence(wood), wood.englishName() + " Fence");
                this.addBlock(ModBlocks.fenceGate(wood), wood.englishName() + " Fence Gate");
                this.addItem(ModItems.strippedFence(wood), "Stripped " + wood.englishName() + " Fence");
                this.addItem(ModItems.strippedFenceGate(wood), "Stripped " + wood.englishName() + " Fence Gate");
            }
            this.add(ModCreativeTabs.TAB_TITLE, "Log Fences and Gates");
        }
    }

    public static final class Japanese extends LanguageProvider {
        public Japanese(PackOutput output) {
            super(output, LogFencesAndGates.MODID, "ja_jp");
        }

        @Override
        protected void addTranslations() {
            for (LogWood wood : LogWood.values()) {
                this.addBlock(ModBlocks.fence(wood), wood.japaneseName() + "のフェンス");
                this.addBlock(ModBlocks.fenceGate(wood), wood.japaneseName() + "のフェンスゲート");
                this.addItem(ModItems.strippedFence(wood), wood.strippedJapaneseName() + "のフェンス");
                this.addItem(ModItems.strippedFenceGate(wood), wood.strippedJapaneseName() + "のフェンスゲート");
            }
            this.add(ModCreativeTabs.TAB_TITLE, "Log Fences and Gates");
        }
    }
}
