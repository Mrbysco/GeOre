package com.shynieke.geore.client;

import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		setupLayer(GeOreRegistry.COAL_GEORE);
		setupLayer(GeOreRegistry.COPPER_GEORE);
		setupLayer(GeOreRegistry.DIAMOND_GEORE);
		setupLayer(GeOreRegistry.EMERALD_GEORE);
		setupLayer(GeOreRegistry.GOLD_GEORE);
		setupLayer(GeOreRegistry.IRON_GEORE);
		setupLayer(GeOreRegistry.LAPIS_GEORE);
		setupLayer(GeOreRegistry.QUARTZ_GEORE);
		setupLayer(GeOreRegistry.REDSTONE_GEORE);
		setupLayer(GeOreRegistry.RUBY_GEORE);
		setupLayer(GeOreRegistry.SAPPHIRE_GEORE);
		setupLayer(GeOreRegistry.TOPAZ_GEORE);
	}

	public static void setupLayer(GeOreBlockReg blockReg) {
		ItemBlockRenderTypes.setRenderLayer(blockReg.getCluster().get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(blockReg.getSmallBud().get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(blockReg.getMediumBud().get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(blockReg.getLargeBud().get(), RenderType.cutout());
	}
}
