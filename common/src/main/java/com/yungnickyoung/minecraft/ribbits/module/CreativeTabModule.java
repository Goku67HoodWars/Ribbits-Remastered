package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.registry.RegistrySupplier;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTabModule {
    public static final RegistrySupplier<CreativeModeTab> TAB = RibbitsRegistries.CREATIVE_TABS.add(
            "general",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable("itemGroup.ribbits.general"))
                    .icon(() -> new ItemStack(BlockModule.RED_TOADSTOOL.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(BlockModule.RED_TOADSTOOL.get());
                        output.accept(BlockModule.BROWN_TOADSTOOL.get());
                        output.accept(BlockModule.TOADSTOOL_STEM.get());
                        output.accept(BlockModule.SWAMP_LANTERN.get());
                        output.accept(ItemModule.GIANT_LILYPAD.get());
                        output.accept(BlockModule.SWAMP_DAISY.get());
                        output.accept(BlockModule.TOADSTOOL.get());
                        output.accept(BlockModule.UMBRELLA_LEAF.get());
                        output.accept(BlockModule.MOSSY_OAK_PLANKS.get());
                        output.accept(BlockModule.MOSSY_OAK_PLANKS.getStairs());
                        output.accept(BlockModule.MOSSY_OAK_PLANKS.getSlab());
                        output.accept(BlockModule.MOSSY_OAK_PLANKS.getFence());
                        output.accept(BlockModule.MOSSY_OAK_PLANKS.getFenceGate());
                        output.accept(BlockModule.MOSSY_OAK_DOOR.get());

                        output.accept(ItemModule.MARACA.get());

                        output.accept(ItemModule.RIBBIT_NITWIT_SPAWN_EGG.get());
                        output.accept(ItemModule.RIBBIT_FISHERMAN_SPAWN_EGG.get());
                        output.accept(ItemModule.RIBBIT_GARDENER_SPAWN_EGG.get());
                        output.accept(ItemModule.RIBBIT_MERCHANT_SPAWN_EGG.get());
                        output.accept(ItemModule.RIBBIT_SORCERER_SPAWN_EGG.get());
                    })
                    .build());
}
