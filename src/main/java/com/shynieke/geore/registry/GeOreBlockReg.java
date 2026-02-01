package com.shynieke.geore.registry;

import com.shynieke.geore.block.BuddingGeoreBlock;
import com.shynieke.geore.item.GeoreSpyglassItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TintedGlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class GeOreBlockReg {
	protected final String name;
	protected final DeferredHolder<Block, AmethystBlock> block;
	protected final DeferredHolder<Block, BuddingGeoreBlock> budding;
	protected final DeferredHolder<Block, AmethystClusterBlock> cluster;
	protected final DeferredHolder<Block, AmethystClusterBlock> large_bud;
	protected final DeferredHolder<Block, AmethystClusterBlock> medium_bud;
	protected final DeferredHolder<Block, AmethystClusterBlock> small_bud;
	protected final DeferredHolder<Block, TintedGlassBlock> tinted_glass;
	protected final DeferredHolder<Item, Item> shard;
	protected final DeferredHolder<Item, GeoreSpyglassItem> spyglass;

	@Nonnull
	public String getName() {
		return name;
	}

	public DeferredHolder<Block, AmethystBlock> getBlock() {
		return block;
	}

	public DeferredHolder<Block, BuddingGeoreBlock> getBudding() {
		return budding;
	}

	public DeferredHolder<Block, AmethystClusterBlock> getCluster() {
		return cluster;
	}

	public DeferredHolder<Block, AmethystClusterBlock> getLargeBud() {
		return large_bud;
	}

	public DeferredHolder<Block, AmethystClusterBlock> getMediumBud() {
		return medium_bud;
	}

	public DeferredHolder<Block, AmethystClusterBlock> getSmallBud() {
		return small_bud;
	}

	public DeferredHolder<Item, Item> getShard() {
		return shard;
	}

	public DeferredHolder<Item, GeoreSpyglassItem> getSpyglass() {
		return spyglass;
	}

	public DeferredHolder<Block, TintedGlassBlock> getTintedGlass() {
		return tinted_glass;
	}

	public GeOreBlockReg(String name, MapColor color, int spyglassHex) {
		this(name, color, Item::new, spyglassHex);
	}

	public GeOreBlockReg(String name, MapColor color, Function<Item.Properties, ? extends Item> customShard, int spyglassHex) {
		this.name = name;
		block = GeOreRegistry.BLOCKS.registerBlock(name + "_block", AmethystBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).mapColor(color).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops());
		GeOreRegistry.ITEMS.registerItem(getBlock().getId().getPath(), (properties) -> new BlockItem(getBlock().get(), properties.useBlockDescriptionPrefix()));

		large_bud = GeOreRegistry.BLOCKS.registerBlock("large_" + name + "_bud", (properties) -> new AmethystClusterBlock(5, 3, properties),
				() -> BlockBehaviour.Properties.ofFullCopy(Blocks.LARGE_AMETHYST_BUD).mapColor(color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 4));
		GeOreRegistry.ITEMS.registerItem(getLargeBud().getId().getPath(), (properties) -> new BlockItem(getLargeBud().get(), properties.useBlockDescriptionPrefix()));

		medium_bud = GeOreRegistry.BLOCKS.registerBlock("medium_" + name + "_bud", (properties) -> new AmethystClusterBlock(4, 3, properties),
				() -> BlockBehaviour.Properties.ofFullCopy(Blocks.MEDIUM_AMETHYST_BUD).mapColor(color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 2));
		GeOreRegistry.ITEMS.registerItem(getMediumBud().getId().getPath(), (properties) -> new BlockItem(getMediumBud().get(), properties.useBlockDescriptionPrefix()));

		small_bud = GeOreRegistry.BLOCKS.registerBlock("small_" + name + "_bud", (properties) -> new AmethystClusterBlock(3, 4, properties),
				() -> BlockBehaviour.Properties.ofFullCopy(Blocks.SMALL_AMETHYST_BUD).mapColor(color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 1));
		GeOreRegistry.ITEMS.registerItem(getSmallBud().getId().getPath(), (properties) -> new BlockItem(getSmallBud().get(), properties.useBlockDescriptionPrefix()));

		cluster = GeOreRegistry.BLOCKS.registerBlock(name + "_cluster", (properties) -> new AmethystClusterBlock(7, 3, properties),
				() -> BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_CLUSTER).mapColor(color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((state) -> 5));
		GeOreRegistry.ITEMS.registerItem(getCluster().getId().getPath(), (properties) -> new BlockItem(getCluster().get(), properties.useBlockDescriptionPrefix()));

		budding = GeOreRegistry.BLOCKS.registerBlock("budding_" + name, (properties) ->
						new BuddingGeoreBlock(properties, getSmallBud(), getMediumBud(), getLargeBud(), getCluster()),
				() -> BlockBehaviour.Properties.ofFullCopy(Blocks.BUDDING_AMETHYST).mapColor(color).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops());
		GeOreRegistry.ITEMS.registerItem(getBudding().getId().getPath(), (properties) -> new BlockItem(getBudding().get(), properties.useBlockDescriptionPrefix()));

		tinted_glass = GeOreRegistry.BLOCKS.registerBlock(name + "_tinted_glass", TintedGlassBlock::new, () ->
				BlockBehaviour.Properties.ofFullCopy(Blocks.TINTED_GLASS).mapColor(color).strength(0.3F).sound(SoundType.GLASS).noOcclusion());
		GeOreRegistry.ITEMS.registerItem(getTintedGlass().getId().getPath(), (properties) -> new BlockItem(getTintedGlass().get(), properties.useBlockDescriptionPrefix()));

		shard = GeOreRegistry.ITEMS.registerItem(name + "_shard", customShard);

		spyglass = GeOreRegistry.ITEMS.registerItem(name + "_spyglass", (properties) -> new GeoreSpyglassItem(properties, spyglassHex));
	}
}
