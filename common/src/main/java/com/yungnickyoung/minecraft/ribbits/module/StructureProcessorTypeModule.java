package com.yungnickyoung.minecraft.ribbits.module;

import com.mojang.serialization.MapCodec;
import com.yungnickyoung.minecraft.ribbits.registry.RegistrySupplier;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import com.yungnickyoung.minecraft.ribbits.world.processor.BlockReplaceProcessor;
import com.yungnickyoung.minecraft.ribbits.world.processor.BrewingStandProcessor;
import com.yungnickyoung.minecraft.ribbits.world.processor.LapisBlockProcessor;
import com.yungnickyoung.minecraft.ribbits.world.processor.PillarProcessor;
import com.yungnickyoung.minecraft.ribbits.world.processor.PodzolProcessor;
import com.yungnickyoung.minecraft.ribbits.world.processor.WarpedNyliumProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

/**
 * Structure processors are registered by their codec: since 26.1 the structure processor registry
 * holds the {@link MapCodec} itself rather than a separate type object.
 */
public class StructureProcessorTypeModule {
    public static final RegistrySupplier<MapCodec<? extends StructureProcessor>> PILLAR_PROCESSOR =
            register("pillar_processor", PillarProcessor.CODEC);
    public static final RegistrySupplier<MapCodec<? extends StructureProcessor>> PODZOL_PROCESSOR =
            register("podzol_processor", PodzolProcessor.CODEC);
    public static final RegistrySupplier<MapCodec<? extends StructureProcessor>> WARPED_NYLIUM_PROCESSOR =
            register("warped_nylium_processor", WarpedNyliumProcessor.CODEC);
    public static final RegistrySupplier<MapCodec<? extends StructureProcessor>> BLOCK_REPLACE_PROCESSOR =
            register("block_replace_processor", BlockReplaceProcessor.CODEC);
    public static final RegistrySupplier<MapCodec<? extends StructureProcessor>> LAPIS_BLOCK_PROCESSOR =
            register("lapis_block_processor", LapisBlockProcessor.CODEC);
    public static final RegistrySupplier<MapCodec<? extends StructureProcessor>> BREWING_STAND_PROCESSOR =
            register("brewing_stand_processor", BrewingStandProcessor.CODEC);

    private static RegistrySupplier<MapCodec<? extends StructureProcessor>> register(
            String name, MapCodec<? extends StructureProcessor> codec) {
        return RibbitsRegistries.STRUCTURE_PROCESSORS.add(name, () -> codec);
    }
}
