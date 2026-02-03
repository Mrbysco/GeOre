package com.shynieke.geore.datagen.server;

import com.shynieke.geore.Reference;
import com.shynieke.geore.datagen.GeOreBiomeModifiers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GeOreBiomeTagsProvider extends BiomeTagsProvider {
	public GeOreBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider,
	                              @Nullable ExistingFileHelper existingFileHelper) {
		super(output, provider, Reference.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(GeOreBiomeModifiers.IS_DEEP_DARK).add(Biomes.DEEP_DARK);
	}
}
