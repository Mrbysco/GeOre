package com.shynieke.geore.datagen;

import com.shynieke.geore.datagen.client.GeOreBlockModelProvider;
import com.shynieke.geore.datagen.client.GeOreBlockStateProvider;
import com.shynieke.geore.datagen.client.GeOreItemModelProvider;
import com.shynieke.geore.datagen.client.GeOreLanguageProvider;
import com.shynieke.geore.datagen.server.GeOreBlockTagsProvider;
import com.shynieke.geore.datagen.server.GeOreItemTagsProvider;
import com.shynieke.geore.datagen.server.GeOreLootProvider;
import com.shynieke.geore.datagen.server.GeOreRecipeProvider;
import com.shynieke.geore.datagen.server.GeoreDatapackProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GeOreDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
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

			generator.addProvider(event.includeClient(), new GeoreDatapackProvider(
					packOutput,
					event.getLookupProvider(),
					Set.of("geore")
			));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new GeOreLanguageProvider(packOutput));
			generator.addProvider(event.includeClient(), new GeOreBlockModelProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new GeOreItemModelProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new GeOreBlockStateProvider(packOutput, helper));
		}
	}
}
