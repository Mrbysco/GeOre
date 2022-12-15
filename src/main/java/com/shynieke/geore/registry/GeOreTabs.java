package com.shynieke.geore.registry;

import com.shynieke.geore.Reference;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class GeOreTabs {
	private static CreativeModeTab TAB_GEORE;

	@SubscribeEvent
	public void registerCreativeTabs(final CreativeModeTabEvent.Register event) {
		TAB_GEORE = event.registerCreativeModeTab(new ResourceLocation(Reference.MOD_ID, "items"), builder ->
				builder.icon(() -> new ItemStack(Items.AMETHYST_SHARD))
						.title(Component.translatable("itemGroup.statues.items"))
						.displayItems((features, output, hasPermissions) -> {
							List<ItemStack> stacks = GeOreRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
							output.acceptAll(stacks);
						}));
	}
}
