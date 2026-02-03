package com.shynieke.geore.datagen;

import com.shynieke.geore.datagen.client.GeOreBlockModelProvider;
import com.shynieke.geore.datagen.client.GeOreBlockStateProvider;
import com.shynieke.geore.datagen.client.GeOreItemModelProvider;
import com.shynieke.geore.datagen.client.GeOreLanguageProvider;
import com.shynieke.geore.datagen.server.GeOreBiomeTagsProvider;
import com.shynieke.geore.datagen.server.GeOreBlockTagsProvider;
import com.shynieke.geore.datagen.server.GeOreDatapackProvider;
import com.shynieke.geore.datagen.server.GeOreItemTagsProvider;
import com.shynieke.geore.datagen.server.GeOreLootProvider;
import com.shynieke.geore.datagen.server.GeOreRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeOreDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new GeOreLootProvider(packOutput));
			generator.addProvider(event.includeServer(), new GeOreRecipeProvider(packOutput));
			BlockTagsProvider blockTagsProvider;
			generator.addProvider(event.includeServer(), blockTagsProvider = new GeOreBlockTagsProvider(packOutput, lookupProvider, helper));
			generator.addProvider(event.includeServer(), new GeOreItemTagsProvider(packOutput, lookupProvider, blockTagsProvider, helper));
			generator.addProvider(event.includeServer(), new GeOreBiomeTagsProvider(packOutput, lookupProvider, helper));

			generator.addProvider(event.includeClient(), new GeOreDatapackProvider(
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
