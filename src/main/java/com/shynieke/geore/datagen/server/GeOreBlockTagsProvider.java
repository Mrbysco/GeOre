package com.shynieke.geore.datagen.server;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class GeOreBlockTagsProvider extends BlockTagsProvider {
	public GeOreBlockTagsProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(packOutput, lookupProvider, Reference.MOD_ID, existingFileHelper);
	}

	private static final TagKey<Block> BUDDING_GEORE = modTag("budding_geore");
	public static final TagKey<Block> NON_MOVABLE = BlockTags.create(ResourceLocation.fromNamespaceAndPath("create", "non_movable"));

	private static TagKey<Block> modTag(String name) {
		return BlockTags.create(Reference.modLoc(name));
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		for (GeOreBlockReg reg : GeOreRegistry.getGeOres()) {
			this.addMineable(reg);
			this.addCrystalSounds(reg);
			this.addGeore(reg);
			this.tag(Tags.Blocks.GLASS_BLOCKS_TINTED).add(reg.getTintedGlass().get());
		}
		this.tag(Tags.Blocks.RELOCATION_NOT_SUPPORTED).addTag(BUDDING_GEORE);
		this.tag(NON_MOVABLE).addTag(BUDDING_GEORE);
	}

	private void addMineable(GeOreBlockReg blockReg) {
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(blockReg.getCluster().get()).add(blockReg.getSmallBud().get()).add(blockReg.getMediumBud().get()).add(blockReg.getLargeBud().get()).add(blockReg.getBlock().get()).add(blockReg.getBudding().get());
	}

	private void addCrystalSounds(GeOreBlockReg blockReg) {
		this.tag(BlockTags.CRYSTAL_SOUND_BLOCKS).add(blockReg.getBlock().get()).add(blockReg.getBudding().get());
	}

	private void addGeore(GeOreBlockReg blockReg) {
		TagKey<Block> budsTag = modTag("buds/" + "geore_" + blockReg.getName());
		this.tag(budsTag).add(blockReg.getSmallBud().get(), blockReg.getMediumBud().get(), blockReg.getLargeBud().get());
		this.tag(Tags.Blocks.BUDS).addTag(budsTag);

		TagKey<Block> clustersTag = modTag("clusters/" + "geore_" + blockReg.getName());
		this.tag(clustersTag).add(blockReg.getCluster().get());
		this.tag(Tags.Blocks.CLUSTERS).addTag(clustersTag);
		this.tag(Tags.Blocks.BUDDING_BLOCKS).add(blockReg.getBudding().get());
		this.tag(BUDDING_GEORE).add(blockReg.getBudding().get());

		TagKey<Block> blockTag = modTag("storage_blocks/" + "geore_" + blockReg.getName());
		this.tag(blockTag).add(blockReg.getBlock().get());
	}
}
