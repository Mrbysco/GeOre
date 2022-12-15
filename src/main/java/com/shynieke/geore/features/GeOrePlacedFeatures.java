package com.shynieke.geore.features;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class GeOrePlacedFeatures {

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		GeOreFeatures.COAL_GEORE.setupPlaced(context, 60, 6, 30);
		GeOreFeatures.COPPER_GEORE.setupPlaced(context, 90, 6, 30);
		GeOreFeatures.DIAMOND_GEORE.setupPlaced(context, 330, 6, 30);
		GeOreFeatures.EMERALD_GEORE.setupPlaced(context, 420, 6, 30);
		GeOreFeatures.GOLD_GEORE.setupPlaced(context, 180, 6, 30);
		GeOreFeatures.IRON_GEORE.setupPlaced(context, 120, 6, 30);
		GeOreFeatures.LAPIS_GEORE.setupPlaced(context, 210, 6, 30);
		GeOreFeatures.QUARTZ_GEORE.setupPlaced(context, 150, 6, 30);
		GeOreFeatures.REDSTONE_GEORE.setupPlaced(context, 240, 6, 30);
		GeOreFeatures.RUBY_GEORE.setupPlaced(context, 240, 6, 30);
		GeOreFeatures.SAPPHIRE_GEORE.setupPlaced(context, 240, 6, 30);
		GeOreFeatures.TOPAZ_GEORE.setupPlaced(context, 240, 6, 30);
	}
}
