package com.shynieke.geore.features;

import com.google.gson.JsonElement;
import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.RegistryOps;
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
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;

import java.util.List;
import java.util.Map;

public class GeOreFeatureReg {
	protected final String NAME;

	protected final Block BLOCK;
	protected final Block BUDDING;
	protected final Holder<ConfiguredFeature<GeodeConfiguration, ?>> GEODE;

	public String getName() {
		return NAME;
	}

	public GeOreFeatureReg(String name, GeOreBlockReg blockReg) {
		NAME = name;
		BLOCK = blockReg.getBlock().get();
		BUDDING = blockReg.getBudding().get();

		GEODE = FeatureUtils.register(name + "_geode", Feature.GEODE, new GeodeConfiguration(
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

	public void fillPlacedFeatureMap(RegistryOps<JsonElement> ops, Map<ResourceLocation, PlacedFeature> map, int rarity, int minY, int maxY) {
		final ResourceKey<ConfiguredFeature<?, ?>> georeFeatureKey = GEODE.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get();
		final Holder<ConfiguredFeature<?, ?>> georeFeatureKeyHolder = ops.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(georeFeatureKey);
		final PlacedFeature georeFeature = new PlacedFeature(
				georeFeatureKeyHolder,
				List.of(RarityFilter.onAverageOnceEvery(rarity), GeOreFeatures.RNG_DECORATOR, InSquarePlacement.spread(),
						HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(minY), VerticalAnchor.absolute(maxY)), BiomeFilter.biome()));
		map.put(new ResourceLocation(Reference.MOD_ID, NAME + "_geode"), georeFeature);
	}

	public void fillModifierMap(RegistryOps<JsonElement> ops, Map<ResourceLocation, BiomeModifier> map, TagKey<Biome> tag) {
		final HolderSet.Named<Biome> tagHolder = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), tag);
		final BiomeModifier addGeore = new AddFeaturesBiomeModifier(
				tagHolder,
				HolderSet.direct(ops.registry(Registry.PLACED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
						new ResourceLocation(Reference.MOD_ID, NAME + "_geode")))),
				Decoration.LOCAL_MODIFICATIONS);

		map.put(new ResourceLocation(Reference.MOD_ID, NAME + "_geode"), addGeore);
	}
}
