package com.shynieke.geore.datagen;

import com.shynieke.geore.datagen.client.GeOreLanguageProvider;
import com.shynieke.geore.datagen.client.GeOreModelProvider;
import com.shynieke.geore.datagen.server.GeOreBiomeTagsProvider;
import com.shynieke.geore.datagen.server.GeOreBlockTagsProvider;
import com.shynieke.geore.datagen.server.GeOreDatapackProvider;
import com.shynieke.geore.datagen.server.GeOreItemTagsProvider;
import com.shynieke.geore.datagen.server.GeOreLootProvider;
import com.shynieke.geore.datagen.server.GeOreRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class GeOreDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new GeOreLootProvider(packOutput, lookupProvider));
		generator.addProvider(true, new GeOreRecipeProvider.Runner(packOutput, lookupProvider));
		generator.addProvider(true, new GeOreBlockTagsProvider(packOutput, lookupProvider));
		generator.addProvider(true, new GeOreItemTagsProvider(packOutput, lookupProvider));
		generator.addProvider(true, new GeOreBiomeTagsProvider(packOutput, lookupProvider));

		generator.addProvider(true, new GeOreDatapackProvider(
				packOutput,
				event.getLookupProvider(),
				Set.of("geore")
		));

		generator.addProvider(true, new GeOreLanguageProvider(packOutput));
		generator.addProvider(true, new GeOreModelProvider(packOutput));

	}
}
