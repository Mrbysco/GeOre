package com.shynieke.geore.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.geore.config.GeOreConfig;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ConfigurableRarityFilter extends PlacementFilter {
	public static final MapCodec<ConfigurableRarityFilter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.STRING.fieldOf("ore").forGetter(ConfigurableRarityFilter::ore),
			Codec.intRange(1, Integer.MAX_VALUE).fieldOf("base_chance").forGetter(ConfigurableRarityFilter::baseChance)
	).apply(instance, ConfigurableRarityFilter::new));

	public static final PlacementModifierType<ConfigurableRarityFilter> TYPE = () -> CODEC;

	private final String ore;
	private final int baseChance;

	public ConfigurableRarityFilter(String ore, int baseChance) {
		this.ore = ore;
		this.baseChance = baseChance;
	}

	public String ore() {
		return ore;
	}

	public int baseChance() {
		return baseChance;
	}

	@Override
	protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
		double spawnRate = GeOreConfig.COMMON.getGeodeSpawnRate(ore);
		return random.nextDouble() < spawnRate / baseChance;
	}

	@Override
	public PlacementModifierType<?> type() {
		return GeOreRegistry.CONFIGURABLE_RARITY_FILTER.get();
	}
}
