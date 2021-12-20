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

	public static final PlacementModifierType<RNGPlacement> RNG_DECORATOR = register("rng_initializer", RNGPlacement.CODEC);

	public static final GeOreBlockReg COAL_GEORE = new GeOreBlockReg("coal", MaterialColor.STONE, () -> new CoalShardItem(new Item.Properties().tab(GeOreTabs.TAB_GEORE)), 0x02e2e2e);
	public static final GeOreBlockReg COPPER_GEORE = new GeOreBlockReg("copper", MaterialColor.STONE, 0x0c26b4c);
	public static final GeOreBlockReg DIAMOND_GEORE = new GeOreBlockReg("diamond", MaterialColor.STONE, 0x0239698);
	public static final GeOreBlockReg EMERALD_GEORE = new GeOreBlockReg("emerald", MaterialColor.STONE, 0x01c9829);
	public static final GeOreBlockReg GOLD_GEORE = new GeOreBlockReg("gold", MaterialColor.STONE, 0x0eb9d0e);
	public static final GeOreBlockReg IRON_GEORE = new GeOreBlockReg("iron", MaterialColor.STONE, 0x0887455);
	public static final GeOreBlockReg LAPIS_GEORE = new GeOreBlockReg("lapis", MaterialColor.STONE, 0x02a53c8);
	public static final GeOreBlockReg QUARTZ_GEORE = new GeOreBlockReg("quartz", MaterialColor.STONE, 0x0b6a48e);
	public static final GeOreBlockReg REDSTONE_GEORE = new GeOreBlockReg("redstone", MaterialColor.STONE, 0x0960606);

	private static <P extends PlacementModifier> PlacementModifierType<P> register(String name, Codec<P> codec) {
		return Registry.register(Registry.PLACEMENT_MODIFIERS, name, () -> {
			return codec;
		});
	}
}
