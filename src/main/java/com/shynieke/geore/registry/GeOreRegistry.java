package com.shynieke.geore.registry;

import com.shynieke.geore.Reference;
import com.shynieke.geore.item.CoalShardItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class GeOreRegistry {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Reference.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);
	private static final List<GeOreBlockReg> GEORES = new ArrayList<>();

	public static final GeOreBlockReg COAL_GEORE = createGeOre("coal", MapColor.COLOR_BLACK, CoalShardItem::new, 0x2e2e2e);
	public static final GeOreBlockReg COPPER_GEORE = createGeOre("copper", MapColor.COLOR_ORANGE, 0xc26b4c);
	public static final GeOreBlockReg DIAMOND_GEORE = createGeOre("diamond", MapColor.DIAMOND, 0x239698);
	public static final GeOreBlockReg EMERALD_GEORE = createGeOre("emerald", MapColor.EMERALD, 0x1c9829);
	public static final GeOreBlockReg GOLD_GEORE = createGeOre("gold", MapColor.GOLD, 0xeb9d0e);
	public static final GeOreBlockReg IRON_GEORE = createGeOre("iron", MapColor.RAW_IRON, 0x887455);
	public static final GeOreBlockReg LAPIS_GEORE = createGeOre("lapis", MapColor.LAPIS, 0x2a53c8);
	public static final GeOreBlockReg QUARTZ_GEORE = createGeOre("quartz", MapColor.QUARTZ, 0xb6a48e);
	public static final GeOreBlockReg REDSTONE_GEORE = createGeOre("redstone", MapColor.FIRE, 0x960606);
	public static final GeOreBlockReg ANCIENT_DEBRIS_GEORE = createGeOre("ancient_debris", MapColor.COLOR_BROWN, 0x5d342c);

	public static final GeOreBlockReg RUBY_GEORE = createGeOre("ruby", MapColor.COLOR_RED, 0xc81d2e);
	public static final GeOreBlockReg SAPPHIRE_GEORE = createGeOre("sapphire", MapColor.COLOR_BLUE, 0x0e00e0);
	public static final GeOreBlockReg TOPAZ_GEORE = createGeOre("topaz", MapColor.GOLD, 0xffb856);
	public static final GeOreBlockReg ZINC_GEORE = createGeOre("zinc", MapColor.GOLD, 0xa7bdac);
	public static final GeOreBlockReg URANINITE_GEORE = createGeOre("uraninite", MapColor.COLOR_GREEN, 0x00d512);
	public static final GeOreBlockReg BLACK_QUARTZ_GEORE = createGeOre("black_quartz", MapColor.COLOR_BLACK, 0x415764);
	public static final GeOreBlockReg MONAZITE_GEORE = createGeOre("monazite", MapColor.COLOR_PURPLE, 0x91358b);
	public static final GeOreBlockReg ALUMINUM_GEORE = createGeOre("aluminum", MapColor.COLOR_GREEN, 0x8fb2cb);
	public static final GeOreBlockReg LEAD_GEORE = createGeOre("lead", MapColor.COLOR_LIGHT_GRAY, 0x797285);
	public static final GeOreBlockReg NICKEL_GEORE = createGeOre("nickel", MapColor.TERRACOTTA_YELLOW, 0x8b8e68);
	public static final GeOreBlockReg OSMIUM_GEORE = createGeOre("osmium", MapColor.COLOR_LIGHT_BLUE, 0x7a9e96);
	public static final GeOreBlockReg PLATINUM_GEORE = createGeOre("platinum", MapColor.TERRACOTTA_LIGHT_BLUE, 0x5f6c80);
	public static final GeOreBlockReg SILVER_GEORE = createGeOre("silver", MapColor.COLOR_GRAY, 0x727272);
	public static final GeOreBlockReg TIN_GEORE = createGeOre("tin", MapColor.TERRACOTTA_LIGHT_BLUE, 0xb0c4bd);
	public static final GeOreBlockReg TUNGSTEN_GEORE = createGeOre("tungsten", MapColor.TERRACOTTA_PURPLE, 0x584a73);
	public static final GeOreBlockReg URANIUM_GEORE = createGeOre("uranium", MapColor.COLOR_GREEN, 0x00fc15);

	public static final GeOreBlockReg ALLTHEMODIUM_GEORE = createGeOre("allthemodium", MapColor.COLOR_YELLOW, 0xf2a61d);
	public static final GeOreBlockReg VIBRANIUM_GEORE = createGeOre("vibranium", MapColor.COLOR_GREEN, 0x26de88);
	public static final GeOreBlockReg UNOBTAINIUM_GEORE = createGeOre("unobtainium", MapColor.COLOR_PURPLE, 0xa82ce3);

	private static GeOreBlockReg createGeOre(String name, MapColor mapColor, Function<Item.Properties, ? extends Item> item, int color) {
		GeOreBlockReg geOre = new GeOreBlockReg(name, mapColor, item, color);
		GEORES.add(geOre);
		return geOre;
	}

	private static GeOreBlockReg createGeOre(String name, MapColor mapColor, int color) {
		return createGeOre(name, mapColor, Item::new, color);
	}

	public static List<GeOreBlockReg> getGeOres() {
		return Collections.unmodifiableList(GEORES);
	}

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GEORE_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(() -> new ItemStack(Items.AMETHYST_SHARD))
			.title(Component.translatable("itemGroup.geore"))
			.displayItems((displayParameters, output) -> {
				List<ItemStack> stacks = GeOreRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());
}
