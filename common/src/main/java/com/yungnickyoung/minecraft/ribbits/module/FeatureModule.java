package com.yungnickyoung.minecraft.ribbits.module;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.ribbits.registry.RegistrySupplier;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import com.yungnickyoung.minecraft.ribbits.world.feature.RibbitsVegetationBlockFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FeatureModule {
    // 26.3: register the feature's MapCodec into the FEATURE_TYPE registry (same shape as structure processors).
    public static final RegistrySupplier<MapCodec<? extends Feature>> RIBBITS_VEGETATION_FEATURE =
            register("vegetation_block_feature", RibbitsVegetationBlockFeature.CODEC);

    private static RegistrySupplier<MapCodec<? extends Feature>> register(String name, MapCodec<? extends Feature> codec) {
        return RibbitsRegistries.FEATURES.add(name, () -> codec);
    }
}
