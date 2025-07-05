package com.shynieke.geore.datagen.server;

import com.shynieke.geore.datagen.GeOreBiomeModifiers;
import com.shynieke.geore.features.GeOreConfiguredFeatures;
import com.shynieke.geore.features.GeOrePlacedFeatures;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GeOreDatapackProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, GeOreConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, GeOrePlacedFeatures::bootstrap)
			.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, GeOreBiomeModifiers::bootstrap);

	public GeOreDatapackProvider(PackOutput output, CompletableFuture<Provider> registries, Set<String> modIds) {
		super(output, registries, BUILDER, modIds);
	}
}
