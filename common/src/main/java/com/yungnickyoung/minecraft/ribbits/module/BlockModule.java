package com.yungnickyoung.minecraft.ribbits.module;

import com.yungnickyoung.minecraft.ribbits.block.GiantLilyPadBlock;
import com.yungnickyoung.minecraft.ribbits.block.SwampDaisyBlock;
import com.yungnickyoung.minecraft.ribbits.block.SwampLanternBlock;
import com.yungnickyoung.minecraft.ribbits.block.ToadstoolBlock;
import com.yungnickyoung.minecraft.ribbits.block.UmbrellaLeafBlock;
import com.yungnickyoung.minecraft.ribbits.mixin.mixins.accessor.DoorBlockAccessor;
import com.yungnickyoung.minecraft.ribbits.platform.PlatformHelper;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsBlock;
import com.yungnickyoung.minecraft.ribbits.util.RegisterHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BlockModule {
    public static final RibbitsBlock BROWN_TOADSTOOL = RibbitsBlock.of("brown_toadstool", () -> new HugeMushroomBlock(
                    BlockBehaviour.Properties
                            .of()
                            .mapColor(MapColor.DIRT)
                            .strength(0.2f)
                            .instrument(NoteBlockInstrument.BASS)
                            .ignitedByLava()
                            .sound(SoundType.WOOD)
                            .setId(RegisterHelper.blockKey("brown_toadstool"))
            ))
            .withItem(Item.Properties::new);

    public static final RibbitsBlock RED_TOADSTOOL = RibbitsBlock.of("red_toadstool", () -> new HugeMushroomBlock(
                    BlockBehaviour.Properties
                            .of()
                            .mapColor(MapColor.COLOR_RED)
                            .strength(0.2f)
                            .instrument(NoteBlockInstrument.BASS)
                            .ignitedByLava()
                            .sound(SoundType.WOOD)
                            .setId(RegisterHelper.blockKey("red_toadstool"))
            ))
            .withItem(Item.Properties::new);

    public static final RibbitsBlock TOADSTOOL_STEM = RibbitsBlock.of("toadstool_stem", () -> new HugeMushroomBlock(
                    BlockBehaviour.Properties
                            .of()
                            .mapColor(MapColor.WOOL)
                            .strength(0.2f)
                            .instrument(NoteBlockInstrument.BASS)
                            .ignitedByLava()
                            .sound(SoundType.WOOD)
                            .setId(RegisterHelper.blockKey("toadstool_stem"))
            ))
            .withItem(Item.Properties::new);

    public static final RibbitsBlock SWAMP_LANTERN = RibbitsBlock.of("swamp_lantern", () -> new SwampLanternBlock(
                    BlockBehaviour.Properties
                            .of()
                            .mapColor(MapColor.METAL)
                            .requiresCorrectToolForDrops()
                            .strength(2.0f)
                            .sound(SoundType.LANTERN)
                            .lightLevel(ignored -> 15)
                            .noOcclusion()
                            .pushReaction(PushReaction.POPPED)
                            .setId(RegisterHelper.blockKey("swamp_lantern"))
            ))
            .withItem(Item.Properties::new);

    public static final RibbitsBlock GIANT_LILYPAD = RibbitsBlock.of("giant_lilypad", () -> new GiantLilyPadBlock(
            BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.PLANT)
                    .instabreak()
                    .sound(SoundType.LILY_PAD)
                    .noOcclusion()
                    .pushReaction(PushReaction.POPPED)
                    .setId(RegisterHelper.blockKey("giant_lilypad"))
    ));

    public static final RibbitsBlock SWAMP_DAISY = RibbitsBlock.of("swamp_daisy", () -> new SwampDaisyBlock(
                    BlockBehaviour.Properties
                            .of()
                            .mapColor(MapColor.PLANT)
                            .instabreak()
                            .noCollision()
                            .noOcclusion()
                            .sound(SoundType.BIG_DRIPLEAF)
                            .ignitedByLava()
                            .setId(RegisterHelper.blockKey("swamp_daisy"))
            ))
            .withItem(Item.Properties::new);

    public static final RibbitsBlock TOADSTOOL = RibbitsBlock.of("toadstool", () -> new ToadstoolBlock(
                    BlockBehaviour.Properties
                            .of()
                            .mapColor(MapColor.PLANT)
                            .instabreak()
                            .noCollision()
                            .sound(SoundType.SMALL_DRIPLEAF)
                            .ignitedByLava()
                            .setId(RegisterHelper.blockKey("toadstool"))
            ))
            .withItem(Item.Properties::new);

    public static final RibbitsBlock MOSSY_OAK_PLANKS = RibbitsBlock.of("mossy_oak_planks", () -> new Block(
                    BlockBehaviour.Properties
                            .of()
                            .mapColor(Blocks.OAK_PLANKS.defaultMapColor())
                            .instrument(NoteBlockInstrument.BASS)
                            .strength(2.0f, 3.0f)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()
                            .setId(RegisterHelper.blockKey("mossy_oak_planks"))
            ))
            .withItem(Item.Properties::new)
            .withSlab()
            .withStairs()
            .withFence()
            .withFenceGate(WoodType.OAK);

    public static final RibbitsBlock MOSSY_OAK_DOOR = RibbitsBlock.of("mossy_oak_door", () ->
                    DoorBlockAccessor.createDoorBlock(
                            BlockSetType.OAK,
                            BlockBehaviour.Properties
                                    .of()
                                    .mapColor(Blocks.OAK_PLANKS.defaultMapColor())
                                    .instrument(NoteBlockInstrument.BASS)
                                    .strength(3.0f)
                                    .noOcclusion()
                                    .ignitedByLava()
                                    .pushReaction(PushReaction.POPPED)
                                    .sound(SoundType.WOOD)
                                    .setId(RegisterHelper.blockKey("mossy_oak_door"))
                    ))
            .withItem(Item.Properties::new);

    public static final RibbitsBlock UMBRELLA_LEAF = RibbitsBlock.of("umbrella_leaf", () -> new UmbrellaLeafBlock(
                    BlockBehaviour.Properties
                            .of()
                            .mapColor(MapColor.PLANT)
                            .instabreak()
                            .noCollision()
                            .ignitedByLava()
                            .sound(SoundType.SMALL_DRIPLEAF)
                            .setId(RegisterHelper.blockKey("umbrella_leaf"))
            ))
            .withItem(Item.Properties::new);

    /**
     * Called once the block registry has been populated (from RibbitsCommon.init()).
     */
    public static void registerFlammability() {
        PlatformHelper.setBlockAsFlammable(MOSSY_OAK_PLANKS.get(), 5, 20);
        PlatformHelper.setBlockAsFlammable(MOSSY_OAK_PLANKS.getSlab(), 5, 20);
        PlatformHelper.setBlockAsFlammable(MOSSY_OAK_PLANKS.getStairs(), 5, 20);
        PlatformHelper.setBlockAsFlammable(MOSSY_OAK_PLANKS.getFence(), 5, 20);
        PlatformHelper.setBlockAsFlammable(MOSSY_OAK_PLANKS.getFenceGate(), 5, 20);
        PlatformHelper.setBlockAsFlammable(MOSSY_OAK_DOOR.get(), 5, 20);
        PlatformHelper.setBlockAsFlammable(UMBRELLA_LEAF.get(), 60, 100);
        PlatformHelper.setBlockAsFlammable(SWAMP_DAISY.get(), 60, 100);
    }
}
