package com.nokopi.logfences.datagen;

import com.nokopi.logfences.LogFences;

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
        }
    }

    public static final class Japanese extends LanguageProvider {
        public Japanese(PackOutput output) {
            super(output, LogFences.MODID, "ja_jp");
        }

        @Override
        protected void addTranslations() {
        }
    }
}
