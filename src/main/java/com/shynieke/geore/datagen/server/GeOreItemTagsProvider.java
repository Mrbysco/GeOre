package com.shynieke.geore.datagen.server;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class GeOreItemTagsProvider extends ItemTagsProvider {
	public GeOreItemTagsProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider,
	                             BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
		super(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), Reference.MOD_ID, existingFileHelper);
	}

	public static final TagKey<Item> GEORE_BUDDING = modTag("geore_budding");
	public static final TagKey<Item> GEORE_CLUSTERS = modTag("geore_clusters");
	public static final TagKey<Item> GEORE_SMALL_BUDS = modTag("geore_small_buds");
	public static final TagKey<Item> GEORE_MEDIUM_BUDS = modTag("geore_medium_buds");
	public static final TagKey<Item> GEORE_LARGE_BUDS = modTag("geore_large_buds");
	public static final TagKey<Item> GEORE_SHARDS = modTag("geore_shards");
	public static final TagKey<Item> GEORE_BLOCKS = modTag("geore_blocks");

	public static final TagKey<Item> BUDDING = forgeTag("budding");
	public static final TagKey<Item> BUDS = forgeTag("buds");
	public static final TagKey<Item> CLUSTERS = forgeTag("clusters");

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(Provider provider) {
		this.tag(ItemTags.COALS).add(GeOreRegistry.COAL_GEORE.getShard().get());

		for (GeOreBlockReg reg : GeOreRegistry.getGeOres()) {
			this.addGeore(reg);
			this.addStorage(reg);
			this.tag(Tags.Items.GLASS_TINTED).add(reg.getTintedGlass().get().asItem());
		}

		this.tag(BUDDING).addTag(GEORE_BUDDING);
		this.tag(BUDS).addTags(GEORE_SMALL_BUDS, GEORE_MEDIUM_BUDS, GEORE_LARGE_BUDS);
		this.tag(CLUSTERS).addTag(GEORE_CLUSTERS);
	}

	private void addStorage(GeOreBlockReg blockReg) {
		TagKey<Item> itemTag = modTag("storage_blocks/" + "geore_" + blockReg.getName());
		this.tag(itemTag).add(blockReg.getBlock().get().asItem());
	}

	private void addGeore(GeOreBlockReg blockReg) {
		this.tag(GEORE_BUDDING).add(blockReg.getBudding().get().asItem());

		TagKey<Item> smallBudsTag = modTag("geore_small_buds/" + blockReg.getName());
		this.tag(GEORE_SMALL_BUDS).addTag(smallBudsTag);
		this.tag(smallBudsTag).add(blockReg.getSmallBud().get().asItem());

		TagKey<Item> mediumBudsTag = modTag("geore_medium_buds/" + blockReg.getName());
		this.tag(GEORE_MEDIUM_BUDS).addTag(mediumBudsTag);
		this.tag(mediumBudsTag).add(blockReg.getMediumBud().get().asItem());

		TagKey<Item> largeBudsTag = modTag("geore_large_buds/" + blockReg.getName());
		this.tag(GEORE_LARGE_BUDS).addTag(largeBudsTag);
		this.tag(largeBudsTag).add(blockReg.getLargeBud().get().asItem());

		TagKey<Item> clusterTag = modTag("geore_clusters/" + blockReg.getName());
		this.tag(GEORE_CLUSTERS).addTag(clusterTag);
		this.tag(clusterTag).add(blockReg.getCluster().get().asItem());

		TagKey<Item> shardTag = modTag("geore_shards/" + blockReg.getName());
		this.tag(GEORE_SHARDS).addTag(shardTag);
		this.tag(shardTag).add(blockReg.getShard().get());

		TagKey<Item> blockTag = modTag("geore_blocks/" + blockReg.getName());
		this.tag(GEORE_BLOCKS).addTag(blockTag);
		this.tag(blockTag).add(blockReg.getBlock().get().asItem());
	}

	private static TagKey<Item> modTag(String name) {
		return ItemTags.create(Reference.modLoc(name));
	}

	private static TagKey<Item> forgeTag(String name) {
		return ItemTags.create(new ResourceLocation("forge", name));
	}
}
