package com.shynieke.geore.registry;

import com.shynieke.geore.block.BuddingGeoreBlock;
import com.shynieke.geore.item.GeoreSpyglassItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class GeOreBlockReg {
	protected final String name;
	protected final RegistryObject<AmethystBlock> block;
	protected final RegistryObject<BuddingGeoreBlock> budding;
	protected final RegistryObject<AmethystClusterBlock> cluster;
	protected final RegistryObject<AmethystClusterBlock> large_bud;
	protected final RegistryObject<AmethystClusterBlock> medium_bud;
	protected final RegistryObject<AmethystClusterBlock> small_bud;
	protected final RegistryObject<Item> shard;
	protected final RegistryObject<Item> spyglass;

	@Nonnull
	public String getName() {
		return name;
	}

	public RegistryObject<AmethystBlock> getBlock() {
		return block;
	}

	public RegistryObject<BuddingGeoreBlock> getBudding() {
		return budding;
	}

	public RegistryObject<AmethystClusterBlock> getCluster() {
		return cluster;
	}

	public RegistryObject<AmethystClusterBlock> getLargeBud() {
		return large_bud;
	}

	public RegistryObject<AmethystClusterBlock> getMediumBud() {
		return medium_bud;
	}

	public RegistryObject<AmethystClusterBlock> getSmallBud() {
		return small_bud;
	}

	public RegistryObject<Item> getShard() {
		return shard;
	}

	public RegistryObject<Item> getSpyglass() {
		return spyglass;
	}

	public GeOreBlockReg(String name, MaterialColor color, int spyglassHex) {
		this(name, color, () -> new Item(new Item.Properties().tab(GeOreTabs.TAB_GEORE)), spyglassHex);
	}

	public GeOreBlockReg(String name, MaterialColor color, Supplier<Item> customShard, int spyglassHex) {
		this.name = name;
		block = GeOreRegistry.BLOCKS.register(name + "_block", () -> new AmethystBlock(BlockBehaviour.Properties.of(Material.AMETHYST, color).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()));
		GeOreRegistry.ITEMS.register(getBlock().getId().getPath(), () -> new BlockItem(getBlock().get(), new Item.Properties().tab(GeOreTabs.TAB_GEORE)));

		large_bud = GeOreRegistry.BLOCKS.register("large_" + name + "_bud", () -> new AmethystClusterBlock(5, 3,
				BlockBehaviour.Properties.of(Material.AMETHYST, color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((p_152651_) -> 4)));
		GeOreRegistry.ITEMS.register(getLargeBud().getId().getPath(), () -> new BlockItem(getLargeBud().get(), new Item.Properties().tab(GeOreTabs.TAB_GEORE)));

		medium_bud = GeOreRegistry.BLOCKS.register("medium_" + name + "_bud", () -> new AmethystClusterBlock(4, 3,
				BlockBehaviour.Properties.of(Material.AMETHYST, color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((p_152651_) -> 2)));
		GeOreRegistry.ITEMS.register(getMediumBud().getId().getPath(), () -> new BlockItem(getMediumBud().get(), new Item.Properties().tab(GeOreTabs.TAB_GEORE)));

		small_bud = GeOreRegistry.BLOCKS.register("small_" + name + "_bud", () -> new AmethystClusterBlock(3, 4,
				BlockBehaviour.Properties.of(Material.AMETHYST, color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((p_152651_) -> 1)));
		GeOreRegistry.ITEMS.register(getSmallBud().getId().getPath(), () -> new BlockItem(getSmallBud().get(), new Item.Properties().tab(GeOreTabs.TAB_GEORE)));

		cluster = GeOreRegistry.BLOCKS.register(name + "_cluster", () -> new AmethystClusterBlock(7, 3,
				BlockBehaviour.Properties.of(Material.AMETHYST, color).noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).lightLevel((p_152651_) -> 5)));
		GeOreRegistry.ITEMS.register(getCluster().getId().getPath(), () -> new BlockItem(getCluster().get(), new Item.Properties().tab(GeOreTabs.TAB_GEORE)));

		budding = GeOreRegistry.BLOCKS.register("budding_" + name, () ->
				new BuddingGeoreBlock(BlockBehaviour.Properties.of(Material.AMETHYST, color).randomTicks().strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops(),
					getSmallBud(), getMediumBud(), getLargeBud(), getCluster()));
		GeOreRegistry.ITEMS.register(getBudding().getId().getPath(), () -> new BlockItem(getBudding().get(), new Item.Properties().tab(GeOreTabs.TAB_GEORE)));

		shard = GeOreRegistry.ITEMS.register(name + "_shard", customShard);

		spyglass = GeOreRegistry.ITEMS.register(name + "_spyglass", () -> new GeoreSpyglassItem(new Item.Properties().tab(GeOreTabs.TAB_GEORE), spyglassHex));
	}
}
