package com.shynieke.geore.datagen.client;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class GeOreBlockModelProvider extends BlockModelProvider {
	public GeOreBlockModelProvider(PackOutput packOutput, ExistingFileHelper helper) {
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
		cubeAll(blockReg.getBlock().getId().getPath(), modLoc(BLOCK_FOLDER + "/" + blockReg.getBlock().getId().getPath()));
		cubeAll(blockReg.getBudding().getId().getPath(), modLoc(BLOCK_FOLDER + "/" + blockReg.getBudding().getId().getPath()));

		crossBlock(blockReg.getCluster().get());
		crossBlock(blockReg.getSmallBud().get());
		crossBlock(blockReg.getMediumBud().get());
		crossBlock(blockReg.getLargeBud().get());
	}

	private void crossBlock(Block block) {
		String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
		cross(path, modLoc(BLOCK_FOLDER + "/" + path)).renderType("cutout");
	}
}
