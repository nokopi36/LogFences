package com.nokopi.logfences.datagen;

import com.nokopi.logfences.LogFences;
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
            this.addBlock(ModBlocks.OAK_LOG_FENCE, "Oak Log Fence");
            this.add(ModCreativeTabs.TAB_TITLE, "Log Fences");
        }
    }

    public static final class Japanese extends LanguageProvider {
        public Japanese(PackOutput output) {
            super(output, LogFences.MODID, "ja_jp");
        }

        @Override
        protected void addTranslations() {
            this.addBlock(ModBlocks.OAK_LOG_FENCE, "オークの原木のフェンス");
            this.add(ModCreativeTabs.TAB_TITLE, "Log Fences");
        }
    }
}
