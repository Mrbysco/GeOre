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
		for (GeOreBlockReg reg : GeOreRegistry.getGeOres()) {
			generateGeoreModels(reg);
		}
	}

	protected void generateGeoreModels(GeOreBlockReg blockReg) {
		cubeAll(blockReg.getBlock().getId().getPath(), modLoc(BLOCK_FOLDER + "/" + blockReg.getBlock().getId().getPath()));
		cubeAll(blockReg.getBudding().getId().getPath(), modLoc(BLOCK_FOLDER + "/" + blockReg.getBudding().getId().getPath()));
		cubeAll(blockReg.getTintedGlass().getId().getPath(), modLoc(BLOCK_FOLDER + "/" + blockReg.getTintedGlass().getId().getPath())).renderType("translucent");

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
