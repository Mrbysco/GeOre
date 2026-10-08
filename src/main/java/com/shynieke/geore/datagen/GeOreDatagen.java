package com.shynieke.geore.datagen;

import com.shynieke.geore.datagen.client.GeOreBlockModelProvider;
import com.shynieke.geore.datagen.client.GeOreBlockStateProvider;
import com.shynieke.geore.datagen.client.GeOreItemModelProvider;
import com.shynieke.geore.datagen.client.GeOreLanguageProvider;
import com.shynieke.geore.datagen.server.GeOreBiomeTagsProvider;
import com.shynieke.geore.datagen.server.GeOreBlockTagsProvider;
import com.shynieke.geore.datagen.server.GeOreItemTagsProvider;
import com.shynieke.geore.datagen.server.GeOreLootProvider;
import com.shynieke.geore.datagen.server.GeOreRecipeProvider;
import com.shynieke.geore.features.GeOreConfiguredFeatures;
import com.shynieke.geore.features.GeOrePlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GeOreDatagen {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, GeOreConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, GeOrePlacedFeatures::bootstrap)
			.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, GeOreBiomeModifiers::bootstrap);

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		event.createDatapackRegistryObjects(BUILDER);

		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new GeOreLootProvider(packOutput, lookupProvider));
			generator.addProvider(event.includeServer(), new GeOreRecipeProvider(packOutput, lookupProvider));
			BlockTagsProvider blockTagsProvider;
			generator.addProvider(event.includeServer(), blockTagsProvider = new GeOreBlockTagsProvider(packOutput, lookupProvider, helper));
			generator.addProvider(event.includeServer(), new GeOreItemTagsProvider(packOutput, lookupProvider, blockTagsProvider, helper));
			generator.addProvider(event.includeServer(), new GeOreBiomeTagsProvider(packOutput, lookupProvider, helper));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new GeOreLanguageProvider(packOutput));
			generator.addProvider(event.includeClient(), new GeOreBlockModelProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new GeOreItemModelProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new GeOreBlockStateProvider(packOutput, helper));
		}
	}
}
