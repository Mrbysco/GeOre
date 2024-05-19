package com.shynieke.geore.features;

import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class GeOreConfiguredFeatures {

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		GeOreFeatures.COAL_GEORE.setupConfigured(context, GeOreRegistry.COAL_GEORE);
		GeOreFeatures.COPPER_GEORE.setupConfigured(context, GeOreRegistry.COPPER_GEORE);
		GeOreFeatures.DIAMOND_GEORE.setupConfigured(context, GeOreRegistry.DIAMOND_GEORE);
		GeOreFeatures.EMERALD_GEORE.setupConfigured(context, GeOreRegistry.EMERALD_GEORE);
		GeOreFeatures.GOLD_GEORE.setupConfigured(context, GeOreRegistry.GOLD_GEORE);
		GeOreFeatures.IRON_GEORE.setupConfigured(context, GeOreRegistry.IRON_GEORE);
		GeOreFeatures.LAPIS_GEORE.setupConfigured(context, GeOreRegistry.LAPIS_GEORE);
		GeOreFeatures.QUARTZ_GEORE.setupConfigured(context, GeOreRegistry.QUARTZ_GEORE);
		GeOreFeatures.REDSTONE_GEORE.setupConfigured(context, GeOreRegistry.REDSTONE_GEORE);
		GeOreFeatures.RUBY_GEORE.setupConfigured(context, GeOreRegistry.RUBY_GEORE);
		GeOreFeatures.SAPPHIRE_GEORE.setupConfigured(context, GeOreRegistry.SAPPHIRE_GEORE);
		GeOreFeatures.TOPAZ_GEORE.setupConfigured(context, GeOreRegistry.TOPAZ_GEORE);
		GeOreFeatures.ZINC_GEORE.setupConfigured(context, GeOreRegistry.ZINC_GEORE);
	}
}
