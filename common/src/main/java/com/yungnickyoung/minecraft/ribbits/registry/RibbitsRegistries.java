package com.yungnickyoung.minecraft.ribbits.registry;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.ribbits.module.BlockModule;
import com.yungnickyoung.minecraft.ribbits.module.CreativeTabModule;
import com.yungnickyoung.minecraft.ribbits.module.EntityTypeModule;
import com.yungnickyoung.minecraft.ribbits.module.FeatureModule;
import com.yungnickyoung.minecraft.ribbits.module.ItemModule;
import com.yungnickyoung.minecraft.ribbits.module.ParticleTypeModule;
import com.yungnickyoung.minecraft.ribbits.module.RibbitInstrumentModule;
import com.yungnickyoung.minecraft.ribbits.module.RibbitProfessionModule;
import com.yungnickyoung.minecraft.ribbits.module.RibbitUmbrellaTypeModule;
import com.yungnickyoung.minecraft.ribbits.module.SoundModule;
import com.yungnickyoung.minecraft.ribbits.module.StructureProcessorTypeModule;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.core.particles.ParticleType;

import java.util.List;

/**
 * Every registry this mod contributes to, in the order the entries have to be created.
 * <p>
 * Blocks come before items because the block items look their block up while they are being built,
 * and the creative tab comes last because it lists both.
 */
public final class RibbitsRegistries {
    public static final DeferredRegistry<Block> BLOCKS =
            DeferredRegistry.of(BuiltInRegistries.BLOCK, Registries.BLOCK);
    public static final DeferredRegistry<Item> ITEMS =
            DeferredRegistry.of(BuiltInRegistries.ITEM, Registries.ITEM);
    public static final DeferredRegistry<EntityType<?>> ENTITY_TYPES =
            DeferredRegistry.of(BuiltInRegistries.ENTITY_TYPE, Registries.ENTITY_TYPE);
    public static final DeferredRegistry<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegistry.of(BuiltInRegistries.PARTICLE_TYPE, Registries.PARTICLE_TYPE);
    public static final DeferredRegistry<SoundEvent> SOUND_EVENTS =
            DeferredRegistry.of(BuiltInRegistries.SOUND_EVENT, Registries.SOUND_EVENT);
    public static final DeferredRegistry<Feature<?>> FEATURES =
            DeferredRegistry.of(BuiltInRegistries.FEATURE, Registries.FEATURE);
    public static final DeferredRegistry<MapCodec<? extends StructureProcessor>> STRUCTURE_PROCESSORS =
            DeferredRegistry.of(BuiltInRegistries.STRUCTURE_PROCESSOR, Registries.STRUCTURE_PROCESSOR);
    public static final DeferredRegistry<CreativeModeTab> CREATIVE_TABS =
            DeferredRegistry.of(BuiltInRegistries.CREATIVE_MODE_TAB, Registries.CREATIVE_MODE_TAB);

    private static final List<DeferredRegistry<?>> ALL = List.of(
            SOUND_EVENTS,
            BLOCKS,
            ITEMS,
            ENTITY_TYPES,
            PARTICLE_TYPES,
            FEATURES,
            STRUCTURE_PROCESSORS,
            CREATIVE_TABS);

    private RibbitsRegistries() {
    }

    /**
     * All registries, in creation order.
     */
    public static List<DeferredRegistry<?>> all() {
        return ALL;
    }

    /**
     * Loads the modules that queue registry entries, so their static fields line up before the
     * loader drains the registries directly or replays them during its own register event.
     */
    public static void bootstrap() {
        touch(SoundModule.class,
                BlockModule.class,
                ItemModule.class,
                EntityTypeModule.class,
                ParticleTypeModule.class,
                FeatureModule.class,
                StructureProcessorTypeModule.class,
                CreativeTabModule.class);
    }

    /**
     * Loads the modules that read registered values back out while they initialize: instruments
     * hold their sound events, so they have to come after registration rather than during
     * {@link #bootstrap()}.
     */
    public static void initDataModules() {
        touch(RibbitInstrumentModule.class,
                RibbitProfessionModule.class,
                RibbitUmbrellaTypeModule.class);
    }

    private static void touch(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Unable to load " + clazz.getName(), e);
            }
        }
    }
}
