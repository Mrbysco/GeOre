package com.shynieke.geore.block;

import com.shynieke.geore.config.GeOreConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BuddingGeoreBlock extends BuddingAmethystBlock {
	private final Supplier<? extends AmethystClusterBlock> smallSupplier;
	private final Supplier<? extends AmethystClusterBlock> mediumSupplier;
	private final Supplier<? extends AmethystClusterBlock> largeSupplier;
	private final Supplier<? extends AmethystClusterBlock> clusterSupplier;

	public BuddingGeoreBlock(Properties properties, Supplier<? extends AmethystClusterBlock> smallSupplier,
	                         Supplier<? extends AmethystClusterBlock> mediumSupplier,
	                         Supplier<? extends AmethystClusterBlock> largeSupplier,
	                         Supplier<? extends AmethystClusterBlock> clusterSupplier) {
		super(properties);
		this.smallSupplier = smallSupplier;
		this.mediumSupplier = mediumSupplier;
		this.largeSupplier = largeSupplier;
		this.clusterSupplier = clusterSupplier;
	}

	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (random.nextInt(5) == 0) {
			Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
			BlockPos blockpos = pos.relative(direction);
			BlockState blockstate = level.getBlockState(blockpos);
			Block block = null;
			if (canClusterGrowAtState(blockstate)) {
				block = smallSupplier.get();
			} else if (blockstate.is(smallSupplier.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
				block = mediumSupplier.get();
			} else if (blockstate.is(mediumSupplier.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
				block = largeSupplier.get();
			} else if (blockstate.is(largeSupplier.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
				block = clusterSupplier.get();
			}

			if (block != null) {
				BlockState budState = block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction).setValue(AmethystClusterBlock.WATERLOGGED, blockstate.getFluidState().getType() == Fluids.WATER);
				level.setBlockAndUpdate(blockpos, budState);
			}
		}
	}

	@Override
	public @Nullable PushReaction getPistonPushReaction(BlockState state) {
		if (GeOreConfig.COMMON.disablePistonPushForBuddingGeOre.get()) {
			return PushReaction.BLOCK;
		}
		return super.getPistonPushReaction(state);
	}
}
