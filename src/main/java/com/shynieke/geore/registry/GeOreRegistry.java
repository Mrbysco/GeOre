package com.shynieke.geore.registry;

import com.shynieke.geore.Reference;
import com.shynieke.geore.item.CoalShardItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GeOreRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

	public static final GeOreBlockReg COAL_GEORE = new GeOreBlockReg("coal", MaterialColor.STONE, () -> new CoalShardItem(new Item.Properties().tab(GeOreTabs.TAB_GEORE)), 0x2e2e2e);
	public static final GeOreBlockReg COPPER_GEORE = new GeOreBlockReg("copper", MaterialColor.STONE, 0xc26b4c);
	public static final GeOreBlockReg DIAMOND_GEORE = new GeOreBlockReg("diamond", MaterialColor.STONE, 0x239698);
	public static final GeOreBlockReg EMERALD_GEORE = new GeOreBlockReg("emerald", MaterialColor.STONE, 0x1c9829);
	public static final GeOreBlockReg GOLD_GEORE = new GeOreBlockReg("gold", MaterialColor.STONE, 0xeb9d0e);
	public static final GeOreBlockReg IRON_GEORE = new GeOreBlockReg("iron", MaterialColor.STONE, 0x887455);
	public static final GeOreBlockReg LAPIS_GEORE = new GeOreBlockReg("lapis", MaterialColor.STONE, 0x2a53c8);
	public static final GeOreBlockReg QUARTZ_GEORE = new GeOreBlockReg("quartz", MaterialColor.STONE, 0xb6a48e);
	public static final GeOreBlockReg REDSTONE_GEORE = new GeOreBlockReg("redstone", MaterialColor.STONE, 0x960606);

	public static final GeOreBlockReg RUBY_GEORE = new GeOreBlockReg("ruby", MapColor.COLOR_RED, 0xc81d2e);
	public static final GeOreBlockReg SAPPHIRE_GEORE = new GeOreBlockReg("sapphire", MapColor.COLOR_BLUE, 0xe00e0);
	public static final GeOreBlockReg TOPAZ_GEORE = new GeOreBlockReg("topaz", MapColor.GOLD, 0xffb856);
	public static final GeOreBlockReg ZINC_GEORE = new GeOreBlockReg("zinc", MapColor.GOLD, 0xa7bdac);
}
