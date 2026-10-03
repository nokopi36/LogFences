package com.nokopi.logfences;

import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LogFences.MODID);

    public static final String TAB_TITLE = "itemGroup." + LogFences.MODID;

    // 専用タブ: 木の種類ごとにフェンス → ゲート → 剥いだフェンス → 剥いだゲートの順で並べる
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LOG_FENCES_TAB = CREATIVE_MODE_TABS.register("log_fences", () -> CreativeModeTab.builder()
            .title(Component.translatable(TAB_TITLE))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> new ItemStack(ModItems.fence(LogWood.OAK).get()))
            .displayItems((parameters, output) -> {
                for (LogWood wood : LogWood.values()) {
                    output.accept(ModItems.fence(wood).get());
                    output.accept(ModItems.fenceGate(wood).get());
                    output.accept(ModItems.strippedFence(wood).get());
                    output.accept(ModItems.strippedFenceGate(wood).get());
                }
            })
            .build());

    private ModCreativeTabs() {
    }

    // 建築ブロックタブにも、各木のフェンスゲートの後ろに並べる
    static void addToVanillaTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            for (LogWood wood : LogWood.values()) {
                ItemStack previous = new ItemStack(wood.vanillaFenceGate());
                for (ItemStack stack : List.of(new ItemStack(ModItems.fence(wood).get()), new ItemStack(ModItems.fenceGate(wood).get()),
                        new ItemStack(ModItems.strippedFence(wood).get()), new ItemStack(ModItems.strippedFenceGate(wood).get()))) {
                    event.insertAfter(previous, stack, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                    previous = stack;
                }
            }
        }
    }
}
