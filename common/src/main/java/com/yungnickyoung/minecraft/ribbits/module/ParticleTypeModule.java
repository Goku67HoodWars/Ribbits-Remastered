package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.registry.RegistrySupplier;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import net.minecraft.core.particles.SimpleParticleType;

public class ParticleTypeModule {
    public static final RegistrySupplier<SimpleParticleType> SPELL =
            RibbitsRegistries.PARTICLE_TYPES.add("spell", () -> new SimpleParticleType(false));
}
