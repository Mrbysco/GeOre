package com.shynieke.geore.worldgen.decorator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import java.util.Random;
import java.util.stream.Stream;

public class RNGDecorator extends FeatureDecorator<NoneDecoratorConfiguration> {
	public RNGDecorator() {
		super(NoneDecoratorConfiguration.CODEC);
	}

	@Override
	public Stream<BlockPos> getPositions(DecorationContext context, Random random, NoneDecoratorConfiguration config, BlockPos pos) {
		long a = random.nextLong() | 1L;
		long b = random.nextLong() | 1L;
		random.setSeed(((pos.getX() * a * 341873128712L + 12412146) * (pos.getZ() * b * 132897987541L + 5813717)) ^ 423487234);
		return Stream.of(pos);
	}
}
