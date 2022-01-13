package com.shynieke.geore.features;

import com.shynieke.geore.registry.GeOreBlockReg;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;
import java.util.function.Supplier;

public class GeOreFeatureReg {
	protected final String NAME;

	protected final Block BLOCK;
	protected final Block BUDDING;
	protected final ConfiguredFeature<GeodeConfiguration, ?> GEODE;
	protected final Supplier<Integer> RARITY;

	public String getName() {
		return NAME;
	}

	public PlacedFeature getPlacedFeature() {
		return GEODE.placed(RarityFilter.onAverageOnceEvery(RARITY.get()), GeOreFeatures.RNG_DECORATOR, InSquarePlacement.spread(),
				HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(30)), BiomeFilter.biome());
	}

	public GeOreFeatureReg(String name, GeOreBlockReg blockReg, Supplier<Integer> rarityConfig) {
		NAME = name;
		BLOCK = blockReg.getBlock().get();
		BUDDING = blockReg.getBudding().get();
		RARITY = rarityConfig;

		GEODE = FeatureUtils.register(name + "_geode", Feature.GEODE.configured(new GeodeConfiguration(
				new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), SimpleStateProvider.simple(BLOCK),
						SimpleStateProvider.simple(BUDDING), BlockStateProvider.simple(Blocks.CALCITE),
						SimpleStateProvider.simple(Blocks.SMOOTH_BASALT),
						List.of(blockReg.getSmallBud().get().defaultBlockState(), blockReg.getMediumBud().get().defaultBlockState(),
								blockReg.getLargeBud().get().defaultBlockState(), blockReg.getCluster().get().defaultBlockState()),
						BlockTags.FEATURES_CANNOT_REPLACE.getName(), BlockTags.GEODE_INVALID_BLOCKS.getName()),
				new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
				new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D, true,
				UniformInt.of(4, 6), UniformInt.of(3, 4),
				UniformInt.of(1, 2), -16, 16, 0.05D, 1)));
	}
}
