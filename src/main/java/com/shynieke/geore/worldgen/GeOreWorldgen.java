package com.shynieke.geore.worldgen;

import com.shynieke.geore.config.GeOreConfig;
import com.shynieke.geore.config.GeOreGenConfig;
import com.shynieke.geore.features.GeOreFeatures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GeOreWorldgen {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void biomeLoadingEvent(BiomeLoadingEvent event) {
		if (event.getName() == null)
			return;

		ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
		BiomeGenerationSettingsBuilder builder = event.getGeneration();

		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.coalGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.COAL_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.COAL_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.copperGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.COPPER_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.COPPER_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.diamondGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.DIAMOND_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.DIAMOND_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.emeraldGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.EMERALD_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.EMERALD_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.goldGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.GOLD_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.GOLD_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.ironGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.IRON_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.IRON_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.lapisGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.LAPIS_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.LAPIS_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.redstoneGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.REDSTONE_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.REDSTONE_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.quartzGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.QUARTZ_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.QUARTZ_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.rubyGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.RUBY_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.RUBY_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.sapphireGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.SAPPHIRE_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.SAPPHIRE_GEORE.getPlacedFeature());
			}
		}
		if (shouldAddGeOre(biomeKey, GeOreConfig.COMMON.topazGeOre)) {
			if (!builder.getFeatures(Decoration.LOCAL_MODIFICATIONS).contains(GeOreFeatures.TOPAZ_GEORE.getPlacedFeature())) {
				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.TOPAZ_GEORE.getPlacedFeature());
			}
		}
	}

	@SuppressWarnings("deprecation")
	public boolean shouldAddGeOre(ResourceKey<Biome> biomeKey, GeOreGenConfig config) {
		if (config.generateGeOre.get()) {
			for (String type : config.GeOreDictionaryType.get()) {
				if (BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.getType(type))) {
					return true;
				}
			}
		}
		return false;
	}
}
