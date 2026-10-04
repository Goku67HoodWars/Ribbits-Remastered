package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.data.RibbitProfession;
import com.yungnickyoung.minecraft.ribbits.item.MaracaItem;
import com.yungnickyoung.minecraft.ribbits.item.RibbitSpawnEggDispenseItemBehavior;
import com.yungnickyoung.minecraft.ribbits.item.RibbitSpawnEggItem;
import com.yungnickyoung.minecraft.ribbits.platform.PlatformHelper;
import com.yungnickyoung.minecraft.ribbits.registry.RegistrySupplier;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import com.yungnickyoung.minecraft.ribbits.util.RegisterHelper;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.DispenserBlock;

public class ItemModule {
    public static final RegistrySupplier<Item> GIANT_LILYPAD = RibbitsRegistries.ITEMS.add(
            "giant_lilypad",
            () -> new PlaceOnWaterBlockItem(
                    BlockModule.GIANT_LILYPAD.get(),
                    new Item.Properties().setId(RegisterHelper.itemKey("giant_lilypad"))));

    public static final RegistrySupplier<Item> RIBBIT_NITWIT_SPAWN_EGG = spawnEgg("ribbit_nitwit_spawn_egg", RibbitProfessionModule.NITWIT);
    public static final RegistrySupplier<Item> RIBBIT_FISHERMAN_SPAWN_EGG = spawnEgg("ribbit_fisherman_spawn_egg", RibbitProfessionModule.FISHERMAN);
    public static final RegistrySupplier<Item> RIBBIT_GARDENER_SPAWN_EGG = spawnEgg("ribbit_gardener_spawn_egg", RibbitProfessionModule.GARDENER);
    public static final RegistrySupplier<Item> RIBBIT_MERCHANT_SPAWN_EGG = spawnEgg("ribbit_merchant_spawn_egg", RibbitProfessionModule.MERCHANT);
    public static final RegistrySupplier<Item> RIBBIT_SORCERER_SPAWN_EGG = spawnEgg("ribbit_sorcerer_spawn_egg", RibbitProfessionModule.SORCERER);

    public static final RegistrySupplier<Item> MARACA = RibbitsRegistries.ITEMS.add(
            "maraca",
            () -> new MaracaItem(new Item.Properties().stacksTo(1).setId(RegisterHelper.itemKey("maraca"))));

    private static RegistrySupplier<Item> spawnEgg(String name, RibbitProfession profession) {
        return RibbitsRegistries.ITEMS.add(
                name,
                () -> new RibbitSpawnEggItem(profession, new Item.Properties().setId(RegisterHelper.itemKey(name))));
    }

    /**
     * Called once the block and item registries have been populated (from RibbitsCommon.init()).
     */
    public static void registerCompostablesAndDispenserBehavior() {
        PlatformHelper.addCompostableItem(BlockModule.SWAMP_DAISY.get().asItem(), 0.65F);
        PlatformHelper.addCompostableItem(BlockModule.GIANT_LILYPAD.get().asItem(), 0.65F);
        PlatformHelper.addCompostableItem(BlockModule.UMBRELLA_LEAF.get().asItem(), 0.65F);
        PlatformHelper.addCompostableItem(BlockModule.TOADSTOOL.get().asItem(), 0.65F);
        PlatformHelper.addCompostableItem(BlockModule.TOADSTOOL_STEM.get().asItem(), 0.85F);
        PlatformHelper.addCompostableItem(BlockModule.BROWN_TOADSTOOL.get().asItem(), 0.85F);
        PlatformHelper.addCompostableItem(BlockModule.RED_TOADSTOOL.get().asItem(), 0.85F);

        DispenseItemBehavior spawnEggBehavior = new RibbitSpawnEggDispenseItemBehavior();
        DispenserBlock.registerBehavior(RIBBIT_NITWIT_SPAWN_EGG.get(), spawnEggBehavior);
        DispenserBlock.registerBehavior(RIBBIT_FISHERMAN_SPAWN_EGG.get(), spawnEggBehavior);
        DispenserBlock.registerBehavior(RIBBIT_GARDENER_SPAWN_EGG.get(), spawnEggBehavior);
        DispenserBlock.registerBehavior(RIBBIT_MERCHANT_SPAWN_EGG.get(), spawnEggBehavior);
        DispenserBlock.registerBehavior(RIBBIT_SORCERER_SPAWN_EGG.get(), spawnEggBehavior);
    }
}
