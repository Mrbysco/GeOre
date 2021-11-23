package com.shynieke.geore.features;

import com.google.common.collect.ImmutableList;
import com.shynieke.geore.registry.GeOreBlockReg;
import net.minecraft.data.worldgen.Features;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

public class GeOreFeatureReg {
	protected final BlockState BLOCK;
	protected final BlockState BUDDING;
	protected final ConfiguredFeature<?, ?> GEODE;

	public ConfiguredFeature<?, ?> getGeodeFeature() {
		return GEODE;
	}

	public GeOreFeatureReg(String name, GeOreBlockReg blockReg) {
		BLOCK = blockReg.getBlock().get().defaultBlockState();
		BUDDING = blockReg.getBudding().get().defaultBlockState();

		GEODE = GeOreFeatures.register(name + "_geode", Feature.GEODE.configured(new GeodeConfiguration(
				new GeodeBlockSettings(new SimpleStateProvider(Features.States.AIR), new SimpleStateProvider(BLOCK),
						new SimpleStateProvider(BUDDING), new SimpleStateProvider(Features.States.CALCITE),
						new SimpleStateProvider(Features.States.SMOOTH_BASALT),
						ImmutableList.of(blockReg.getSmallBud().get().defaultBlockState(), blockReg.getMediumBud().get().defaultBlockState(),
								blockReg.getLargeBud().get().defaultBlockState(), blockReg.getCluster().get().defaultBlockState()),
						BlockTags.FEATURES_CANNOT_REPLACE.getName(), BlockTags.GEODE_INVALID_BLOCKS.getName()),
				new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
				new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D, true,
				UniformInt.of(4, 6), UniformInt.of(3, 4),
				UniformInt.of(1, 2), -16, 16, 0.05D, 1))
					.rangeUniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(46)).squared());
	}
}
