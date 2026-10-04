package com.yungnickyoung.minecraft.ribbits.forge.module;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.data.RibbitData;
import com.yungnickyoung.minecraft.ribbits.module.EntityDataSerializerModule;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class EntityDataSerializerModuleForge {
    public static final DeferredRegister<EntityDataSerializer<?>> DATA_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, RibbitsCommon.MOD_ID);

    public static final Supplier<EntityDataSerializer<RibbitData>> RIBBIT_DATA = DATA_SERIALIZERS.register(
            "ribbit_data",
            () -> EntityDataSerializerModule.RIBBIT_DATA_SERIALIZER
    );

    public static void register(BusGroup modBus) {
        DATA_SERIALIZERS.register(modBus);
    }
}
