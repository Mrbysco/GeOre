package com.shynieke.geore.datagen.client;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class GeOreBlockStateProvider extends BlockStateProvider {
	public GeOreBlockStateProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Reference.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		generateGeoreModels(GeOreRegistry.COAL_GEORE);
		generateGeoreModels(GeOreRegistry.COPPER_GEORE);
		generateGeoreModels(GeOreRegistry.DIAMOND_GEORE);
		generateGeoreModels(GeOreRegistry.EMERALD_GEORE);
		generateGeoreModels(GeOreRegistry.GOLD_GEORE);
		generateGeoreModels(GeOreRegistry.IRON_GEORE);
		generateGeoreModels(GeOreRegistry.LAPIS_GEORE);
		generateGeoreModels(GeOreRegistry.QUARTZ_GEORE);
		generateGeoreModels(GeOreRegistry.REDSTONE_GEORE);
		generateGeoreModels(GeOreRegistry.ANCIENT_DEBRIS_GEORE);
		generateGeoreModels(GeOreRegistry.RUBY_GEORE);
		generateGeoreModels(GeOreRegistry.SAPPHIRE_GEORE);
		generateGeoreModels(GeOreRegistry.TOPAZ_GEORE);
		generateGeoreModels(GeOreRegistry.ZINC_GEORE);
		generateGeoreModels(GeOreRegistry.URANINITE_GEORE);
		generateGeoreModels(GeOreRegistry.BLACK_QUARTZ_GEORE);
		generateGeoreModels(GeOreRegistry.MONAZITE_GEORE);
		generateGeoreModels(GeOreRegistry.ALUMINUM_GEORE);
		generateGeoreModels(GeOreRegistry.LEAD_GEORE);
		generateGeoreModels(GeOreRegistry.NICKEL_GEORE);
		generateGeoreModels(GeOreRegistry.OSMIUM_GEORE);
		generateGeoreModels(GeOreRegistry.PLATINUM_GEORE);
		generateGeoreModels(GeOreRegistry.SILVER_GEORE);
		generateGeoreModels(GeOreRegistry.TIN_GEORE);
		generateGeoreModels(GeOreRegistry.TUNGSTEN_GEORE);
		generateGeoreModels(GeOreRegistry.URANIUM_GEORE);
	}

	protected void generateGeoreModels(GeOreBlockReg blockReg) {
		simpleBlock(blockReg.getBlock().get());
		simpleBlock(blockReg.getBudding().get());
		clusterBlock(blockReg.getCluster().get());
		clusterBlock(blockReg.getLargeBud().get());
		clusterBlock(blockReg.getMediumBud().get());
		clusterBlock(blockReg.getSmallBud().get());
	}

	private void clusterBlock(Block block) {
		ModelFile clusterBlock = models().getExistingFile(modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath()));
		getVariantBuilder(block).partialState().with(BlockStateProperties.FACING, Direction.DOWN).modelForState().modelFile(clusterBlock).rotationX(180).addModel().partialState().with(BlockStateProperties.FACING, Direction.EAST).modelForState().modelFile(clusterBlock).rotationX(90).rotationY(90).addModel().partialState().with(BlockStateProperties.FACING, Direction.NORTH).modelForState().modelFile(clusterBlock).rotationX(90).addModel().partialState().with(BlockStateProperties.FACING, Direction.SOUTH).modelForState().modelFile(clusterBlock).rotationX(90).rotationY(180).addModel().partialState().with(BlockStateProperties.FACING, Direction.UP).modelForState().modelFile(clusterBlock).addModel().partialState().with(BlockStateProperties.FACING, Direction.WEST).modelForState().modelFile(clusterBlock).rotationX(90).rotationY(270).addModel();
	}
}
