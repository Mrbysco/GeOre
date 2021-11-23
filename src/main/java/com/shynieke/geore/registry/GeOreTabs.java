package com.shynieke.geore.registry;

import com.shynieke.geore.Reference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GeOreTabs {
	public static final CreativeModeTab TAB_GEORE = new CreativeModeTab(Reference.MOD_ID) {
		public ItemStack makeIcon() {
			return new ItemStack(Items.AMETHYST_SHARD);
		}
	};
}
