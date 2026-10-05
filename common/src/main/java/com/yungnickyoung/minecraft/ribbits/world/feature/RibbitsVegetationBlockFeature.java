package com.yungnickyoung.minecraft.ribbits.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 26.3 merged the old {@code Feature<FC>} + separate {@code FeatureConfiguration} into one
 * codec-dispatched {@link Feature}: the feature carries its own config fields, exposes a
 * {@code codec()}, and {@code place} now takes the raw worldgen args (no {@code FeaturePlaceContext}).
 * The feature's {@code CODEC} is registered into the {@code FEATURE_TYPE} registry.
 */
public record RibbitsVegetationBlockFeature(Optional<BlockStateProvider> onSolidStateProvider,
                                            Optional<BlockStateProvider> onLiquidStateProvider,
                                            List<BlockState> cannotPlaceOn,
                                            int tries,
                                            int xzSpread,
                                            int ySpread) implements Feature {

    public static final MapCodec<RibbitsVegetationBlockFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(
                    BlockStateProvider.DIRECT_CODEC.optionalFieldOf("on_solid_state_provider").forGetter(RibbitsVegetationBlockFeature::onSolidStateProvider),
                    BlockStateProvider.DIRECT_CODEC.optionalFieldOf("on_liquid_state_provider").forGetter(RibbitsVegetationBlockFeature::onLiquidStateProvider),
                    BlockState.CODEC.listOf().optionalFieldOf("cannot_place_on", new ArrayList<>()).forGetter(RibbitsVegetationBlockFeature::cannotPlaceOn),
                    Codec.INT.optionalFieldOf("tries", 1).forGetter(RibbitsVegetationBlockFeature::tries),
                    Codec.INT.optionalFieldOf("xz_spread", 0).forGetter(RibbitsVegetationBlockFeature::xzSpread),
                    Codec.INT.optionalFieldOf("y_spread", 0).forGetter(RibbitsVegetationBlockFeature::ySpread))
            .apply(instance, RibbitsVegetationBlockFeature::new));

    @Override
    public MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator generator, RandomSource random, BlockPos origin) {
        boolean placed = false;
        for (int i = 0; i < this.tries; i++) {
            BlockPos candidate = origin.offset(
                    random.nextInt(this.xzSpread + 1) - random.nextInt(this.xzSpread + 1),
                    random.nextInt(this.ySpread + 1) - random.nextInt(this.ySpread + 1),
                    random.nextInt(this.xzSpread + 1) - random.nextInt(this.xzSpread + 1));
            placed |= placeSingle(level, random, candidate);
        }
        return placed;
    }

    private boolean placeSingle(WorldGenLevel level, RandomSource random, BlockPos origin) {
        if (!level.isEmptyBlock(origin)) {
            return false;
        }

        // Check for blocks we can't place on.
        if (this.cannotPlaceOn.contains(level.getBlockState(origin.below()))) {
            return false;
        }

        // Check for water, in which case we place lily pads instead.
        if (this.onLiquidStateProvider.isPresent() && level.getBlockState(origin.below()).is(Blocks.WATER)) {
            BlockStateProvider onWater = this.onLiquidStateProvider.get();
            level.setBlock(origin, onWater.getState(level, random, origin), 2);
            return true;
        }

        // If we have no block states to place, return false.
        if (this.onSolidStateProvider.isEmpty()) {
            return false;
        }

        BlockState toPlace = this.onSolidStateProvider.get().getState(level, random, origin);

        // Place block if it can survive.
        if (toPlace.canSurvive(level, origin)) {
            if (toPlace.getBlock() instanceof DoublePlantBlock) {
                if (!level.isEmptyBlock(origin.above())) {
                    return false;
                }
                DoublePlantBlock.placeAt(level, toPlace, origin, 3);
            } else {
                level.setBlock(origin, toPlace, 3);
            }
            return true;
        }
        return false;
    }
}
