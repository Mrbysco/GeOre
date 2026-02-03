package com.shynieke.geore.datagen.server;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class GeOreBlockTagsProvider extends BlockTagsProvider {
	public GeOreBlockTagsProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(packOutput, lookupProvider, Reference.MOD_ID, existingFileHelper);
	}

	private static final TagKey<Block> BUDDING_GEORE = modTag("budding_geore");
	public static final TagKey<Block> NON_MOVABLE = BlockTags.create(new ResourceLocation("create", "non_movable"));
	public static final TagKey<Block> RELOCATION_NOT_SUPPORTED = forgeTag("relocation_not_supported");

	public static final TagKey<Block> BUDDING = forgeTag("budding");
	public static final TagKey<Block> BUDS = forgeTag("buds");
	public static final TagKey<Block> CLUSTERS = forgeTag("clusters");

	private static TagKey<Block> modTag(String name) {
		return BlockTags.create(Reference.modLoc(name));
	}

	private static TagKey<Block> forgeTag(String name) {
		return BlockTags.create(new ResourceLocation("forge", name));
	}

	@Override
	protected void addTags(Provider provider) {
		for (GeOreBlockReg reg : GeOreRegistry.getGeOres()) {
			this.addMineable(reg);
			this.addCrystalSounds(reg);
			this.addGeore(reg);
			this.tag(Tags.Blocks.GLASS_TINTED).add(reg.getTintedGlass().get());
		}
		this.tag(RELOCATION_NOT_SUPPORTED).addTag(BUDDING_GEORE);
		this.tag(NON_MOVABLE).addTag(BUDDING_GEORE);
	}

	private void addMineable(GeOreBlockReg blockReg) {
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(blockReg.getCluster().get()).add(blockReg.getSmallBud().get()).add(blockReg.getMediumBud().get()).add(blockReg.getLargeBud().get()).add(blockReg.getBlock().get()).add(blockReg.getBudding().get());
	}

	private void addCrystalSounds(GeOreBlockReg blockReg) {
		this.tag(BlockTags.CRYSTAL_SOUND_BLOCKS).add(blockReg.getBlock().get()).add(blockReg.getBudding().get());
	}

	private void addGeore(GeOreBlockReg blockReg) {
		TagKey<Block> budsTag = forgeTag("buds/" + "geore_" + blockReg.getName());
		this.tag(budsTag).add(blockReg.getSmallBud().get(), blockReg.getMediumBud().get(), blockReg.getLargeBud().get());
		this.tag(BUDS).addTag(budsTag);

		TagKey<Block> clustersTag = forgeTag("clusters/" + "geore_" + blockReg.getName());
		this.tag(clustersTag).add(blockReg.getCluster().get());
		this.tag(CLUSTERS).addTag(clustersTag);
		this.tag(BUDDING).add(blockReg.getBudding().get());
		this.tag(BUDDING_GEORE).add(blockReg.getBudding().get());

		TagKey<Block> blockTag = modTag("storage_blocks/" + "geore_" + blockReg.getName());
		this.tag(blockTag).add(blockReg.getBlock().get());
	}
}
