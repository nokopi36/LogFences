package com.nokopi.logfences;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LogFences.MODID);

    public static final String TAB_TITLE = "itemGroup." + LogFences.MODID;

    // 専用タブ: 登録順にすべてのアイテムを並べる
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LOG_FENCES_TAB = CREATIVE_MODE_TABS.register("log_fences", () -> CreativeModeTab.builder()
            .title(Component.translatable(TAB_TITLE))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> new ItemStack(ModItems.OAK_LOG_FENCE.get()))
            .displayItems((parameters, output) -> ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get())))
            .build());

    private ModCreativeTabs() {
    }

    // 建築ブロックタブにも、各木のフェンスゲートの後ろに並べる
    static void addToVanillaTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.insertAfter(new ItemStack(Items.OAK_FENCE_GATE), new ItemStack(ModItems.OAK_LOG_FENCE.get()),
                    CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
            event.insertAfter(new ItemStack(ModItems.OAK_LOG_FENCE.get()), new ItemStack(ModItems.OAK_LOG_FENCE_GATE.get()),
                    CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
        }
    }
}
