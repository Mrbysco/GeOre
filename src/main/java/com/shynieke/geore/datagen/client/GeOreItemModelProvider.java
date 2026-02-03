package com.shynieke.geore.datagen.client;

import com.shynieke.geore.Reference;
import com.shynieke.geore.item.GeoreSpyglassItem;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;


public class GeOreItemModelProvider extends ItemModelProvider {
	public GeOreItemModelProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Reference.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		for (GeOreBlockReg reg : GeOreRegistry.getGeOres()) {
			generateGeoreModels(reg);
		}
	}

	protected void generateGeoreModels(GeOreBlockReg blockReg) {
		singleTexture(blockReg.getShard().getId().getPath(), new ResourceLocation("item/generated"),
				"layer0", Reference.modLoc("item/" + blockReg.getShard().getId().getPath()));

		withExistingParent(blockReg.getBlock().getId().getPath(), Reference.modLoc(BLOCK_FOLDER + "/" + blockReg.getBlock().getId().getPath()));
		withExistingParent(blockReg.getBudding().getId().getPath(), Reference.modLoc(BLOCK_FOLDER + "/" + blockReg.getBudding().getId().getPath()));
		withExistingParent(blockReg.getTintedGlass().getId().getPath(), Reference.modLoc(BLOCK_FOLDER + "/" + blockReg.getTintedGlass().getId().getPath()));

		makeCluster(blockReg.getCluster());
		makeSmallBud(blockReg.getSmallBud());
		makeMediumBud(blockReg.getMediumBud());
		makeLargeBud(blockReg.getLargeBud());
		makeSpyglass(blockReg.getSpyglass());
	}

	private void makeSpyglass(RegistryObject<GeoreSpyglassItem> spyglass) {
		String path = spyglass.getId().getPath();

		ModelFile spyglass_gui = withExistingParent(path + "_gui", mcLoc("spyglass"))
				.texture("layer0", modLoc(ITEM_FOLDER + "/" + path));
		ModelFile spyglass_hand = withExistingParent(path + "_in_hand", mcLoc("spyglass_in_hand"))
				.texture("spyglass", modLoc(ITEM_FOLDER + "/" + path + "_model"));

		withExistingParent(path, "forge:item/default").customLoader(SeparateTransformsModelBuilder::begin)
				.base(nested().parent(spyglass_hand))
				.perspective(ItemDisplayContext.GUI, nested().parent(spyglass_gui))
				.perspective(ItemDisplayContext.GROUND, nested().parent(spyglass_gui))
				.perspective(ItemDisplayContext.FIXED, nested().parent(spyglass_gui));
	}

	private void makeCluster(RegistryObject<AmethystClusterBlock> deferredHolder) {
		String path = deferredHolder.getId().getPath();
		getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
				.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
				.transforms()
				.transform(ItemDisplayContext.HEAD).translation(0, 14, -5).end();
	}

	private void makeSmallBud(RegistryObject<AmethystClusterBlock> deferredHolder) {
		String path = deferredHolder.getId().getPath();
		getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
				.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
				.transforms()
				.transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0, -90, 25)
				.translation(0, 6, 0).scale(0.68F, 0.68F, 0.68F).end()
				.transform(ItemDisplayContext.FIXED).translation(0, 7, 0).end();
	}

	private void makeMediumBud(RegistryObject<AmethystClusterBlock> deferredHolder) {
		String path = deferredHolder.getId().getPath();
		getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
				.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
				.transforms()
				.transform(ItemDisplayContext.FIXED).translation(0, 6, 0).end();
	}

	private void makeLargeBud(RegistryObject<AmethystClusterBlock> deferredHolder) {
		String path = deferredHolder.getId().getPath();
		getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
				.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
				.transforms()
				.transform(ItemDisplayContext.FIXED).translation(0, 4, 0).end();
	}
}
