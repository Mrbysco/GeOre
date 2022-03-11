package com.shynieke.geore.item;

import net.minecraft.world.item.SpyglassItem;

public class GeoreSpyglassItem extends SpyglassItem {
	private final int hexColor;

	public GeoreSpyglassItem(Properties properties, int hexColor) {
		super(properties);
		this.hexColor = hexColor;
	}

	public int getOverlayHex() {
		return hexColor;
	}
}
