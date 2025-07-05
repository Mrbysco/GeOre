package com.shynieke.geore.datagen.client;

import com.shynieke.geore.Reference;
import com.shynieke.geore.item.GeoreSpyglassItem;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class GeOreItemModelProvider extends ItemModelProvider {
	public GeOreItemModelProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Reference.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
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
		singleTexture(blockReg.getShard().getId().getPath(), ResourceLocation.withDefaultNamespace("item/generated"),
				"layer0", Reference.modLoc("item/" + blockReg.getShard().getId().getPath()));

		withExistingParent(blockReg.getBlock().getId().getPath(), Reference.modLoc(BLOCK_FOLDER + "/" + blockReg.getBlock().getId().getPath()));
		withExistingParent(blockReg.getBudding().getId().getPath(), Reference.modLoc(BLOCK_FOLDER + "/" + blockReg.getBudding().getId().getPath()));

		makeCluster(blockReg.getCluster());
		makeSmallBud(blockReg.getSmallBud());
		makeMediumBud(blockReg.getMediumBud());
		makeLargeBud(blockReg.getLargeBud());
		makeSpyglass(blockReg.getSpyglass());
	}

	private void makeSpyglass(DeferredHolder<Item, GeoreSpyglassItem> spyglass) {
		String path = spyglass.getId().getPath();

		ModelFile spyglass_gui = withExistingParent(path + "_gui", mcLoc("spyglass"))
				.texture("layer0", modLoc(ITEM_FOLDER + "/" + path));
		ModelFile spyglass_hand = withExistingParent(path + "_in_hand", mcLoc("spyglass_in_hand"))
				.texture("spyglass", modLoc(ITEM_FOLDER + "/" + path + "_model"));

		withExistingParent(path, "neoforge:item/default").customLoader(SeparateTransformsModelBuilder::begin)
				.base(nested().parent(spyglass_hand))
				.perspective(ItemDisplayContext.GUI, nested().parent(spyglass_gui))
				.perspective(ItemDisplayContext.GROUND, nested().parent(spyglass_gui))
				.perspective(ItemDisplayContext.FIXED, nested().parent(spyglass_gui));
	}

	private void makeCluster(DeferredHolder<Block, AmethystClusterBlock> deferredHolder) {
		String path = deferredHolder.getId().getPath();
		getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
				.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
				.transforms()
				.transform(ItemDisplayContext.HEAD).translation(0, 14, -5).end();
	}

	private void makeSmallBud(DeferredHolder<Block, AmethystClusterBlock> deferredHolder) {
		String path = deferredHolder.getId().getPath();
		getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
				.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
				.transforms()
				.transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0, -90, 25)
				.translation(0, 6, 0).scale(0.68F, 0.68F, 0.68F).end()
				.transform(ItemDisplayContext.FIXED).translation(0, 7, 0).end();
	}

	private void makeMediumBud(DeferredHolder<Block, AmethystClusterBlock> deferredHolder) {
		String path = deferredHolder.getId().getPath();
		getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
				.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
				.transforms()
				.transform(ItemDisplayContext.FIXED).translation(0, 6, 0).end();
	}

	private void makeLargeBud(DeferredHolder<Block, AmethystClusterBlock> deferredHolder) {
		String path = deferredHolder.getId().getPath();
		getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
				.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
				.transforms()
				.transform(ItemDisplayContext.FIXED).translation(0, 4, 0).end();
	}
}
