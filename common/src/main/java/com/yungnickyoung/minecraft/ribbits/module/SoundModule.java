package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.registry.RegistrySupplier;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import net.minecraft.sounds.SoundEvent;

public class SoundModule {
    public static final RegistrySupplier<SoundEvent> ENTITY_RIBBIT_AMBIENT = register("entity.ribbit.ambient");
    public static final RegistrySupplier<SoundEvent> ENTITY_RIBBIT_DEATH = register("entity.ribbit.death");
    public static final RegistrySupplier<SoundEvent> ENTITY_RIBBIT_HURT = register("entity.ribbit.hurt");
    public static final RegistrySupplier<SoundEvent> ENTITY_RIBBIT_STEP = register("entity.ribbit.step");
    public static final RegistrySupplier<SoundEvent> ENTITY_RIBBIT_MAGIC = register("entity.ribbit.magic");
    public static final RegistrySupplier<SoundEvent> MUSIC_RIBBIT_BASS = register("music.ribbit.bass");
    public static final RegistrySupplier<SoundEvent> MUSIC_RIBBIT_BONGO = register("music.ribbit.bongo");
    public static final RegistrySupplier<SoundEvent> MUSIC_RIBBIT_FLUTE = register("music.ribbit.flute");
    public static final RegistrySupplier<SoundEvent> MUSIC_RIBBIT_GUITAR = register("music.ribbit.guitar");
    public static final RegistrySupplier<SoundEvent> MUSIC_MARACA = register("music.ribbit.maraca");

    private static RegistrySupplier<SoundEvent> register(String name) {
        return RibbitsRegistries.SOUND_EVENTS.add(name, () -> SoundEvent.createVariableRangeEvent(RibbitsCommon.id(name)));
    }
}
