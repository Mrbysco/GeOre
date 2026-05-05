package com.shynieke.geore.datagen.client;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
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
		generateSpyglass(itemModels, blockReg.getSpyglass().get());

		blockModels.createTrivialCube(blockReg.getBlock().get());
		blockModels.createTrivialCube(blockReg.getBudding().get());
		blockModels.createTrivialBlock(blockReg.getTintedGlass().get(), TRANSLUCENT_CUBE);

		createAmethystCluster(blockModels, blockReg.getCluster().get());
		createAmethystCluster(blockModels, blockReg.getSmallBud().get());
		createAmethystCluster(blockModels, blockReg.getMediumBud().get());
		createAmethystCluster(blockModels, blockReg.getLargeBud().get());
	}

	public static final TextureSlot SPYGLASS = TextureSlot.create("spyglass");
	public static final ModelTemplate SPYGLASS_IN_HAND = ModelTemplates.createItem("spyglass_in_hand", "_in_hand", SPYGLASS);

	public void generateSpyglass(ItemModelGenerators itemModels, Item item) {
		ItemModel.Unbaked flatModel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
		ItemModel.Unbaked inHandModel = ItemModelUtils.plainModel(
				SPYGLASS_IN_HAND.create(item,
						new TextureMapping().put(SPYGLASS, getSpyglassModelTexture(item))
						, itemModels.modelOutput)
		);
		itemModels.itemModelOutput.accept(item, ItemModelGenerators.createFlatModelDispatch(flatModel, inHandModel));
	}

	public static Material getSpyglassModelTexture(Item block) {
		Identifier id = BuiltInRegistries.ITEM.getKey(block);
		return new Material(id.withPrefix("item/").withSuffix("_model"));
	}

	public void createAmethystCluster(BlockModelGenerators blockModels, Block amethystBlock) {
		MultiVariant multivariant = BlockModelGenerators.plainVariant(ModelTemplates.CROSS.create(amethystBlock, TextureMapping.cross(amethystBlock), blockModels.modelOutput));
		blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(amethystBlock, multivariant).with(BlockModelGenerators.ROTATIONS_COLUMN_WITH_FACING));
	}
}
