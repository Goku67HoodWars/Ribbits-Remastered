package com.yungnickyoung.minecraft.ribbits.registry;

import com.yungnickyoung.minecraft.ribbits.util.RegisterHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A block registration, optionally together with its item form and the usual family of derived
 * blocks. Derived blocks copy the base block's properties and are named {@code <base>_slab},
 * {@code <base>_stairs}, {@code <base>_fence} and {@code <base>_fence_gate}.
 */
public final class RibbitsBlock implements Supplier<Block> {
    private final String name;
    private final RegistrySupplier<Block> block;

    private @Nullable RegistrySupplier<Item> item;
    private @Nullable RegistrySupplier<Block> slab;
    private @Nullable RegistrySupplier<Block> stairs;
    private @Nullable RegistrySupplier<Block> fence;
    private @Nullable RegistrySupplier<Block> fenceGate;

    private RibbitsBlock(String name, Supplier<Block> factory) {
        this.name = name;
        this.block = RibbitsRegistries.BLOCKS.add(name, factory);
    }

    public static RibbitsBlock of(String name, Supplier<Block> factory) {
        return new RibbitsBlock(name, factory);
    }

    public RibbitsBlock withItem(Supplier<Item.Properties> properties) {
        this.item = RibbitsRegistries.ITEMS.add(this.name, () -> new BlockItem(
                this.block.get(),
                properties.get().useBlockDescriptionPrefix().setId(RegisterHelper.itemKey(this.name))));
        return this;
    }

    public RibbitsBlock withSlab() {
        this.slab = this.derived("slab", SlabBlock::new);
        return this;
    }

    public RibbitsBlock withStairs() {
        this.stairs = this.derived("stairs", properties -> new StairBlock(this.block.get().defaultBlockState(), properties));
        return this;
    }

    public RibbitsBlock withFence() {
        this.fence = this.derived("fence", FenceBlock::new);
        return this;
    }

    public RibbitsBlock withFenceGate(WoodType woodType) {
        this.fenceGate = this.derived("fence_gate", properties -> new FenceGateBlock(woodType, properties));
        return this;
    }

    private RegistrySupplier<Block> derived(String suffix, Function<BlockBehaviour.Properties, Block> factory) {
        String derivedName = this.name + "_" + suffix;
        RegistrySupplier<Block> derived = RibbitsRegistries.BLOCKS.add(derivedName, () -> factory.apply(
                BlockBehaviour.Properties.ofFullCopy(this.block.get()).setId(RegisterHelper.blockKey(derivedName))));
        RibbitsRegistries.ITEMS.add(derivedName, () -> new BlockItem(
                derived.get(),
                new Item.Properties().useBlockDescriptionPrefix().setId(RegisterHelper.itemKey(derivedName))));
        return derived;
    }

    @Override
    public Block get() {
        return this.block.get();
    }

    public Item getItem() {
        return require(this.item, "item").get();
    }

    public Block getSlab() {
        return require(this.slab, "slab").get();
    }

    public Block getStairs() {
        return require(this.stairs, "stairs").get();
    }

    public Block getFence() {
        return require(this.fence, "fence").get();
    }

    public Block getFenceGate() {
        return require(this.fenceGate, "fence gate").get();
    }

    private <T> RegistrySupplier<T> require(@Nullable RegistrySupplier<T> entry, String what) {
        if (entry == null) {
            throw new IllegalStateException(this.name + " was not declared with a " + what);
        }
        return entry;
    }
}
