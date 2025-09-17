package com.shynieke.geore.features;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class GeOreConfiguredFeatures {

	public static final ResourceKey<ConfiguredFeature<?, ?>> ANCIENT_DEBRIS_CONFIGURED_KEY = createConfiguredKey("budding_ancient_debris");

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
		GeOreFeatures.URANINITE_GEORE.setupConfigured(context, GeOreRegistry.URANINITE_GEORE);
		GeOreFeatures.BLACK_QUARTZ_GEORE.setupConfigured(context, GeOreRegistry.BLACK_QUARTZ_GEORE);
		GeOreFeatures.MONAZITE_GEORE.setupConfigured(context, GeOreRegistry.MONAZITE_GEORE);
		GeOreFeatures.ALUMINUM_GEORE.setupConfigured(context, GeOreRegistry.ALUMINUM_GEORE);
		GeOreFeatures.LEAD_GEORE.setupConfigured(context, GeOreRegistry.LEAD_GEORE);
		GeOreFeatures.NICKEL_GEORE.setupConfigured(context, GeOreRegistry.NICKEL_GEORE);
		GeOreFeatures.OSMIUM_GEORE.setupConfigured(context, GeOreRegistry.OSMIUM_GEORE);
		GeOreFeatures.PLATINUM_GEORE.setupConfigured(context, GeOreRegistry.PLATINUM_GEORE);
		GeOreFeatures.SILVER_GEORE.setupConfigured(context, GeOreRegistry.SILVER_GEORE);
		GeOreFeatures.TIN_GEORE.setupConfigured(context, GeOreRegistry.TIN_GEORE);
		GeOreFeatures.TUNGSTEN_GEORE.setupConfigured(context, GeOreRegistry.TUNGSTEN_GEORE);
		GeOreFeatures.URANIUM_GEORE.setupConfigured(context, GeOreRegistry.URANIUM_GEORE);

		GeOreFeatures.ALLTHEMODIUM_GEORE.setupConfigured(context, GeOreRegistry.ALLTHEMODIUM_GEORE);
		GeOreFeatures.VIBRANIUM_GEORE.setupConfigured(context, GeOreRegistry.VIBRANIUM_GEORE);
		GeOreFeatures.UNOBTAINIUM_GEORE.setupConfigured(context, GeOreRegistry.UNOBTAINIUM_GEORE);

		RuleTest netherrackRule = new BlockMatchTest(Blocks.NETHERRACK);
		FeatureUtils.register(
				context, ANCIENT_DEBRIS_CONFIGURED_KEY, Feature.SCATTERED_ORE, new OreConfiguration(netherrackRule,
						GeOreRegistry.ANCIENT_DEBRIS_GEORE.getBudding().get().defaultBlockState(), 1, 1.0F)
		);
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredKey(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, Reference.modLoc(path));
	}
}
