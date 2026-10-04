package com.yungnickyoung.minecraft.ribbits.world.randomize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/**
 * Picks a block state at random from a weighted set, falling back to a default state.
 * <p>
 * Each entry carries an independent probability, and the entries are considered in order: an entry
 * with probability {@code 0.3} claims 30% of the rolls, the next entry claims its share of what is
 * left, and the default state is used when no entry claims the roll.
 * <p>
 * Self-contained replacement for YUNG's API BlockStateRandomizer (only the slice Ribbits uses).
 */
public class BlockStateRandomizer {
    public static final Codec<BlockStateRandomizer> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    BlockState.CODEC.fieldOf("defaultBlockState").forGetter(randomizer -> randomizer.defaultBlockState),
                    Entry.CODEC.listOf().optionalFieldOf("entries", List.of()).forGetter(randomizer -> randomizer.entries))
            .apply(instance, BlockStateRandomizer::new));

    private final BlockState defaultBlockState;
    private final List<Entry> entries;

    public BlockStateRandomizer(BlockState defaultBlockState) {
        this(defaultBlockState, List.of());
    }

    public BlockStateRandomizer(BlockState defaultBlockState, List<Entry> entries) {
        this.defaultBlockState = defaultBlockState;
        this.entries = new ArrayList<>(entries);
    }

    public BlockStateRandomizer addBlock(BlockState blockState, float probability) {
        this.entries.add(new Entry(blockState, probability));
        return this;
    }

    public BlockState getDefaultBlockState() {
        return this.defaultBlockState;
    }

    public List<Entry> getEntries() {
        return this.entries;
    }

    /**
     * @return a randomly chosen state, or the default state if no entry is chosen.
     */
    public BlockState get(RandomSource random) {
        float roll = random.nextFloat();
        float claimed = 0.0f;

        for (Entry entry : this.entries) {
            claimed += entry.probability();
            if (roll < claimed) {
                return entry.blockState();
            }
        }

        return this.defaultBlockState;
    }

    public record Entry(BlockState blockState, float probability) {
        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance
                .group(
                        BlockState.CODEC.fieldOf("blockState").forGetter(Entry::blockState),
                        Codec.FLOAT.fieldOf("probability").forGetter(Entry::probability))
                .apply(instance, Entry::new));
    }
}
