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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class GeOreBlockReg {
	protected final String name;
	protected final DeferredHolder<Block, AmethystBlock> block;
	protected final DeferredHolder<Block, BuddingGeoreBlock> budding;
	protected final DeferredHolder<Block, AmethystClusterBlock> cluster;
	protected final DeferredHolder<Block, AmethystClusterBlock> large_bud;
	protected final DeferredHolder<Block, AmethystClusterBlock> medium_bud;
	protected final DeferredHolder<Block, AmethystClusterBlock> small_bud;
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

	public GeOreBlockReg(String name, MapColor color, int spyglassHex) {
		this(name, color, () -> new Item(new Item.Properties()), spyglassHex);
	}

	public GeOreBlockReg(String name, MapColor color, Supplier<Item> customShard, int spyglassHex) {
		this.name = name;
		block = GeOreRegistry.BLOCKS.register(name + "_block", () -> new AmethystBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).mapColor(color).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
		GeOreRegistry.ITEMS.register(getBlock().getId().getPath(), () -> new BlockItem(getBlock().get(), new Item.Properties()));

		large_bud = GeOreRegistry.BLOCKS.register("large_" + name + "_bud", () -> new AmethystClusterBlock(5, 3,
				BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).mapColor(color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((p_152651_) -> 4)));
		GeOreRegistry.ITEMS.register(getLargeBud().getId().getPath(), () -> new BlockItem(getLargeBud().get(), new Item.Properties()));

		medium_bud = GeOreRegistry.BLOCKS.register("medium_" + name + "_bud", () -> new AmethystClusterBlock(4, 3,
				BlockBehaviour.Properties.copy(Blocks.MEDIUM_AMETHYST_BUD).mapColor(color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((p_152651_) -> 2)));
		GeOreRegistry.ITEMS.register(getMediumBud().getId().getPath(), () -> new BlockItem(getMediumBud().get(), new Item.Properties()));

		small_bud = GeOreRegistry.BLOCKS.register("small_" + name + "_bud", () -> new AmethystClusterBlock(3, 4,
				BlockBehaviour.Properties.copy(Blocks.SMALL_AMETHYST_BUD).mapColor(color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((p_152651_) -> 1)));
		GeOreRegistry.ITEMS.register(getSmallBud().getId().getPath(), () -> new BlockItem(getSmallBud().get(), new Item.Properties()));

		cluster = GeOreRegistry.BLOCKS.register(name + "_cluster", () -> new AmethystClusterBlock(7, 3,
				BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((p_152651_) -> 5)));
		GeOreRegistry.ITEMS.register(getCluster().getId().getPath(), () -> new BlockItem(getCluster().get(), new Item.Properties()));

		budding = GeOreRegistry.BLOCKS.register("budding_" + name, () ->
				new BuddingGeoreBlock(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST).mapColor(color).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops(),
						getSmallBud(), getMediumBud(), getLargeBud(), getCluster()));
		GeOreRegistry.ITEMS.register(getBudding().getId().getPath(), () -> new BlockItem(getBudding().get(), new Item.Properties()));

		shard = GeOreRegistry.ITEMS.register(name + "_shard", customShard);

		spyglass = GeOreRegistry.ITEMS.register(name + "_spyglass", () -> new GeoreSpyglassItem(new Item.Properties(), spyglassHex));
	}
}
