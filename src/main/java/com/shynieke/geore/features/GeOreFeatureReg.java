package com.shynieke.geore.features;

import com.shynieke.geore.Reference;
import com.shynieke.geore.modifier.AddConfigFeatureBiomeModifier;
import com.shynieke.geore.registry.GeOreBlockReg;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
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
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class GeOreFeatureReg {
	protected final String NAME;

	protected final Block BLOCK;
	protected final Block BUDDING;
	protected final ResourceKey<ConfiguredFeature<?, ?>> GEODE_CONFIGURED_KEY;
	protected final ResourceKey<PlacedFeature> GEODE_PLACEMENT_KEY;

	public String getName() {
		return NAME;
	}

	public GeOreFeatureReg(String name, GeOreBlockReg blockReg) {
		NAME = name;
		BLOCK = blockReg.getBlock().get();
		BUDDING = blockReg.getBudding().get();

		GEODE_CONFIGURED_KEY = FeatureUtils.createKey(Reference.MOD_PREFIX + name + "_geode");
		GEODE_PLACEMENT_KEY = PlacementUtils.createKey(Reference.MOD_PREFIX + name + "_geode");
	}

	public void setupConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context, GeOreBlockReg blockReg) {
		FeatureUtils.register(context, GEODE_CONFIGURED_KEY, Feature.GEODE, new GeodeConfiguration(
				new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), SimpleStateProvider.simple(BLOCK),
						SimpleStateProvider.simple(BUDDING), BlockStateProvider.simple(Blocks.CALCITE),
						SimpleStateProvider.simple(Blocks.SMOOTH_BASALT),
						List.of(blockReg.getSmallBud().get().defaultBlockState(), blockReg.getMediumBud().get().defaultBlockState(),
								blockReg.getLargeBud().get().defaultBlockState(), blockReg.getCluster().get().defaultBlockState()),
						BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
				new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
				new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D, true,
				UniformInt.of(4, 6), UniformInt.of(3, 4),
				UniformInt.of(1, 2), -16, 16, 0.05D, 1));
	}

	public void setupPlaced(BootstrapContext<PlacedFeature> context, int rarity, int aboveBottom, int absolute) {
		HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);
		Holder<ConfiguredFeature<?, ?>> geodeHolder = holdergetter.getOrThrow(GEODE_CONFIGURED_KEY);
		PlacementUtils.register(context, GEODE_PLACEMENT_KEY, geodeHolder,
				RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(),
				HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(aboveBottom), VerticalAnchor.absolute(absolute)), BiomeFilter.biome());
	}

	public void setupBiomeModifier(BootstrapContext<BiomeModifier> context, TagKey<Biome> tag, String configName) {
		final HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
		final HolderGetter<PlacedFeature> placedHolderGetter = context.lookup(Registries.PLACED_FEATURE);

		final AddConfigFeatureBiomeModifier addGeore = new AddConfigFeatureBiomeModifier(
				biomeHolderGetter.getOrThrow(tag),
				HolderSet.direct(placedHolderGetter.getOrThrow(GEODE_PLACEMENT_KEY)),
				Decoration.LOCAL_MODIFICATIONS, configName);

		context.register(createKey(configName + "_geode"), addGeore);
	}

	private ResourceKey<BiomeModifier> createKey(String name) {
		return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Reference.MOD_ID, name));
	}
}
