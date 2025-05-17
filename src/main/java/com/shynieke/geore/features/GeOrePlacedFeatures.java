package com.shynieke.geore.features;

import com.shynieke.geore.Reference;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class GeOrePlacedFeatures {
	public static final ResourceKey<PlacedFeature> ANCIENT_DEBRIS_PLACEMENT_KEY = createPlacedKey("budding_ancient_debris");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
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
		GeOreFeatures.ZINC_GEORE.setupPlaced(context, 140, 6, 30);
		GeOreFeatures.URANINITE_GEORE.setupPlaced(context, 260, 6, 30);
		GeOreFeatures.BLACK_QUARTZ_GEORE.setupPlaced(context, 250, 6, 30);
		GeOreFeatures.MONAZITE_GEORE.setupPlaced(context, 270, 6, 30);
		GeOreFeatures.ALUMINUM_GEORE.setupPlaced(context, 220, 6, 30);
		GeOreFeatures.LEAD_GEORE.setupPlaced(context, 230, 6, 30);
		GeOreFeatures.NICKEL_GEORE.setupPlaced(context, 210, 6, 30);
		GeOreFeatures.OSMIUM_GEORE.setupPlaced(context, 280, 6, 30);
		GeOreFeatures.PLATINUM_GEORE.setupPlaced(context, 300, 6, 30);
		GeOreFeatures.SILVER_GEORE.setupPlaced(context, 200, 6, 30);
		GeOreFeatures.TIN_GEORE.setupPlaced(context, 240, 6, 30);
		GeOreFeatures.TUNGSTEN_GEORE.setupPlaced(context, 290, 6, 30);
		GeOreFeatures.URANIUM_GEORE.setupPlaced(context, 320, 6, 30);

		Holder<ConfiguredFeature<?, ?>> buddingAncientDebris = holdergetter.getOrThrow(GeOreConfiguredFeatures.ANCIENT_DEBRIS_CONFIGURED_KEY);
		PlacementUtils.register(context, ANCIENT_DEBRIS_PLACEMENT_KEY, buddingAncientDebris,
				InSquarePlacement.spread(), PlacementUtils.RANGE_8_8, BiomeFilter.biome(), RarityFilter.onAverageOnceEvery(3));

	}

	public static ResourceKey<PlacedFeature> createPlacedKey(String path) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Reference.modLoc(path));
	}
}
