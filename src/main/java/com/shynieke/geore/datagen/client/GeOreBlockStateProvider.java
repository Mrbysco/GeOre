package com.shynieke.geore.datagen.client;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GeOreBlockStateProvider extends BlockStateProvider {
	public GeOreBlockStateProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Reference.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		for (GeOreBlockReg reg : GeOreRegistry.getGeOres()) {
			generateGeoreModels(reg);
		}
	}

	protected void generateGeoreModels(GeOreBlockReg blockReg) {
		simpleExistingBlock(blockReg.getBlock().get());
		simpleExistingBlock(blockReg.getBudding().get());
		simpleExistingBlock(blockReg.getTintedGlass().get());
		clusterBlock(blockReg.getCluster().get());
		clusterBlock(blockReg.getLargeBud().get());
		clusterBlock(blockReg.getMediumBud().get());
		clusterBlock(blockReg.getSmallBud().get());
	}

	private void simpleExistingBlock(Block block) {
		ModelFile blockModel = models().getExistingFile(modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath()));
		getVariantBuilder(block)
				.partialState().modelForState().modelFile(blockModel).addModel();
	}

	private void clusterBlock(Block block) {
		ModelFile clusterBlock = models().getExistingFile(modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath()));
		getVariantBuilder(block).partialState().with(BlockStateProperties.FACING, Direction.DOWN).modelForState().modelFile(clusterBlock).rotationX(180).addModel().partialState().with(BlockStateProperties.FACING, Direction.EAST).modelForState().modelFile(clusterBlock).rotationX(90).rotationY(90).addModel().partialState().with(BlockStateProperties.FACING, Direction.NORTH).modelForState().modelFile(clusterBlock).rotationX(90).addModel().partialState().with(BlockStateProperties.FACING, Direction.SOUTH).modelForState().modelFile(clusterBlock).rotationX(90).rotationY(180).addModel().partialState().with(BlockStateProperties.FACING, Direction.UP).modelForState().modelFile(clusterBlock).addModel().partialState().with(BlockStateProperties.FACING, Direction.WEST).modelForState().modelFile(clusterBlock).rotationX(90).rotationY(270).addModel();
	}
}
