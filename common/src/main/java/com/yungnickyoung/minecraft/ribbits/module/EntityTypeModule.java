package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import com.yungnickyoung.minecraft.ribbits.registry.RegistrySupplier;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityTypeModule {
    public static final ResourceKey<EntityType<?>> RIBBIT_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, RibbitsCommon.id("ribbit"));

    public static final RegistrySupplier<EntityType<RibbitEntity>> RIBBIT = RibbitsRegistries.ENTITY_TYPES.add(
            "ribbit",
            () -> EntityType.Builder
                    .of(RibbitEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.75f)
                    .build(RIBBIT_KEY));
}
