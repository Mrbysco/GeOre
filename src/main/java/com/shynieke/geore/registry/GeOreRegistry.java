package com.shynieke.geore.registry;

import com.mojang.serialization.Codec;
import com.shynieke.geore.Reference;
import com.shynieke.geore.item.CoalShardItem;
import com.shynieke.geore.worldgen.decorator.RNGPlacement;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GeOreRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
//	public static final DeferredRegister<FeatureDecorator<?>> DECORATORS = DeferredRegister.create(ForgeRegistries.DECORATORS, Reference.MOD_ID);

	public static final PlacementModifierType<RNGPlacement> RNG_DECORATOR = register("rng_initializer", RNGPlacement.CODEC);

	public static final GeOreBlockReg COAL_GEORE = new GeOreBlockReg("coal", MaterialColor.STONE, () -> new CoalShardItem(new Item.Properties().tab(GeOreTabs.TAB_GEORE)));
	public static final GeOreBlockReg COPPER_GEORE = new GeOreBlockReg("copper", MaterialColor.STONE);
	public static final GeOreBlockReg DIAMOND_GEORE = new GeOreBlockReg("diamond", MaterialColor.STONE);
	public static final GeOreBlockReg EMERALD_GEORE = new GeOreBlockReg("emerald", MaterialColor.STONE);
	public static final GeOreBlockReg GOLD_GEORE = new GeOreBlockReg("gold", MaterialColor.STONE);
	public static final GeOreBlockReg IRON_GEORE = new GeOreBlockReg("iron", MaterialColor.STONE);
	public static final GeOreBlockReg LAPIS_GEORE = new GeOreBlockReg("lapis", MaterialColor.STONE);
	public static final GeOreBlockReg QUARTZ_GEORE = new GeOreBlockReg("quartz", MaterialColor.STONE);
	public static final GeOreBlockReg REDSTONE_GEORE = new GeOreBlockReg("redstone", MaterialColor.STONE);

	private static <P extends PlacementModifier> PlacementModifierType<P> register(String name, Codec<P> codec) {
		return Registry.register(Registry.PLACEMENT_MODIFIERS, name, () -> {
			return codec;
		});
	}
}
