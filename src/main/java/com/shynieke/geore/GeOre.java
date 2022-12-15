package com.shynieke.geore;

import com.mojang.logging.LogUtils;
import com.shynieke.geore.config.GeOreConfig;
import com.shynieke.geore.features.GeOreFeatures;
import com.shynieke.geore.registry.GeOreModifiers;
import com.shynieke.geore.registry.GeOreRegistry;
import com.shynieke.geore.registry.GeOreTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class GeOre {
	public static final Logger LOGGER = LogUtils.getLogger();

	public GeOre() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GeOreConfig.commonSpec);
		eventBus.register(GeOreConfig.class);

		eventBus.addListener(this::setup);
		eventBus.register(new GeOreTabs());

		GeOreRegistry.BLOCKS.register(eventBus);
		GeOreRegistry.ITEMS.register(eventBus);
		GeOreModifiers.BIOME_MODIFIER_SERIALIZERS.register(eventBus);
	}

	private void setup(final FMLCommonSetupEvent event) {
		GeOreFeatures.initialize();
	}
}
