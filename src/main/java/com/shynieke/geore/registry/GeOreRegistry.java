package com.shynieke.geore.registry;

import com.shynieke.geore.Reference;
import com.shynieke.geore.item.CoalShardItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class GeOreRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);

	public static final GeOreBlockReg COAL_GEORE = new GeOreBlockReg("coal", MapColor.COLOR_BLACK, () -> new CoalShardItem(new Item.Properties()), 0x2e2e2e);
	public static final GeOreBlockReg COPPER_GEORE = new GeOreBlockReg("copper", MapColor.COLOR_ORANGE, 0xc26b4c);
	public static final GeOreBlockReg DIAMOND_GEORE = new GeOreBlockReg("diamond", MapColor.DIAMOND, 0x239698);
	public static final GeOreBlockReg EMERALD_GEORE = new GeOreBlockReg("emerald", MapColor.EMERALD, 0x1c9829);
	public static final GeOreBlockReg GOLD_GEORE = new GeOreBlockReg("gold", MapColor.GOLD, 0xeb9d0e);
	public static final GeOreBlockReg IRON_GEORE = new GeOreBlockReg("iron", MapColor.RAW_IRON, 0x887455);
	public static final GeOreBlockReg LAPIS_GEORE = new GeOreBlockReg("lapis", MapColor.LAPIS, 0x2a53c8);
	public static final GeOreBlockReg QUARTZ_GEORE = new GeOreBlockReg("quartz", MapColor.QUARTZ, 0xb6a48e);
	public static final GeOreBlockReg REDSTONE_GEORE = new GeOreBlockReg("redstone", MapColor.FIRE, 0x960606);

	public static final GeOreBlockReg RUBY_GEORE = new GeOreBlockReg("ruby", MapColor.COLOR_RED, 0xc81d2e);
	public static final GeOreBlockReg SAPPHIRE_GEORE = new GeOreBlockReg("sapphire", MapColor.COLOR_BLUE, 0xe00e0);
	public static final GeOreBlockReg TOPAZ_GEORE = new GeOreBlockReg("topaz", MapColor.GOLD, 0xffb856);

	public static final RegistryObject<CreativeModeTab> GEORE_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(() -> new ItemStack(Items.AMETHYST_SHARD))
			.title(Component.translatable("itemGroup.geore"))
			.displayItems((displayParameters, output) -> {
				List<ItemStack> stacks = GeOreRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());
}
