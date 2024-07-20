package com.shynieke.geore;

import com.mojang.logging.LogUtils;
import com.shynieke.geore.client.SpyglassHandler;
import com.shynieke.geore.config.GeOreConfig;
import com.shynieke.geore.features.GeOreFeatures;
import com.shynieke.geore.registry.GeOreModifiers;
import com.shynieke.geore.registry.GeOreRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class GeOre {
	public static final Logger LOGGER = LogUtils.getLogger();

	public GeOre(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, GeOreConfig.commonSpec);
		eventBus.register(GeOreConfig.class);

		eventBus.addListener(this::setup);

		GeOreRegistry.BLOCKS.register(eventBus);
		GeOreRegistry.ITEMS.register(eventBus);
		GeOreRegistry.CREATIVE_MODE_TABS.register(eventBus);
		GeOreModifiers.BIOME_MODIFIER_SERIALIZERS.register(eventBus);

		if (dist.isClient()) {
			container.registerConfig(ModConfig.Type.CLIENT, GeOreConfig.clientSpec);
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
			NeoForge.EVENT_BUS.register(new SpyglassHandler());
		}
	}

	private void setup(final FMLCommonSetupEvent event) {
		GeOreFeatures.initialize();
	}
}
