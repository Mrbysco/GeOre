package com.shynieke.geore.datagen.client;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

public class GeOreModelProvider extends ModelProvider {
	public GeOreModelProvider(PackOutput packOutput) {
		super(packOutput, Reference.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		for (GeOreBlockReg reg : GeOreRegistry.getGeOres()) {
			generateGeoreModels(blockModels, itemModels, reg);
		}
	}

	public static final TexturedModel.Provider TRANSLUCENT_CUBE = TexturedModel.createDefault(TextureMapping::cube, ModelTemplates.CUBE_ALL);

	protected void generateGeoreModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels, GeOreBlockReg blockReg) {
		itemModels.generateFlatItem(blockReg.getShard().get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateSpyglass(blockReg.getSpyglass().get());

		blockModels.createTrivialCube(blockReg.getBlock().get());
		blockModels.createTrivialCube(blockReg.getBudding().get());
		blockModels.createTrivialBlock(blockReg.getTintedGlass().get(), TRANSLUCENT_CUBE);

		createAmethystCluster(blockModels, blockReg.getCluster().get());
		createAmethystCluster(blockModels, blockReg.getSmallBud().get());
		createAmethystCluster(blockModels, blockReg.getMediumBud().get());
		createAmethystCluster(blockModels, blockReg.getLargeBud().get());
	}

	public void createAmethystCluster(BlockModelGenerators blockModels, Block amethystBlock) {
		MultiVariant multivariant = BlockModelGenerators.plainVariant(ModelTemplates.CROSS.create(amethystBlock, TextureMapping.cross(amethystBlock), blockModels.modelOutput));
		blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(amethystBlock, multivariant).with(BlockModelGenerators.ROTATIONS_COLUMN_WITH_FACING));
	}
}
