package com.shynieke.geore.client;

import com.shynieke.geore.Reference;
import com.shynieke.geore.config.GeOreConfig;
import com.shynieke.geore.item.GeoreSpyglassItem;
import net.minecraft.client.CameraType;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class SpyglassHandler {
	public static void registerGuiLayer(RegisterGuiLayersEvent event) {
		event.registerBelowAll(Reference.modLoc("geore_spyglass"), SpyglassHandler::drawSpyglass);
	}

	private static void drawSpyglass(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		final Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;

		if (player != null && player.isUsingItem() && player.getUseItem().getItem() instanceof GeoreSpyglassItem georeSpyglassItem
				&& minecraft.options.getCameraType() == CameraType.FIRST_PERSON) {
			int color = ARGB.color(ARGB.as8BitChannel(GeOreConfig.CLIENT.spyglassIntensity.get().floatValue()), georeSpyglassItem.getOverlayHex());
			graphics.fill(0, 0, graphics.guiWidth(), graphics.guiHeight(), color);
		}
	}
}
