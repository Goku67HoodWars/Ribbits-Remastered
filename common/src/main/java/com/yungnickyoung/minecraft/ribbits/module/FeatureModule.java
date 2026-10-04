package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.registry.RegistrySupplier;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import com.yungnickyoung.minecraft.ribbits.world.feature.RibbitsVegetationBlockFeature;

public class FeatureModule {
    public static final RegistrySupplier<RibbitsVegetationBlockFeature> RIBBITS_VEGETATION_FEATURE =
            RibbitsRegistries.FEATURES.add("vegetation_block_feature", RibbitsVegetationBlockFeature::new);
}
