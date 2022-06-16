//package com.shynieke.geore.worldgen;
//
//import com.shynieke.geore.config.GeOreConfig;
//import com.shynieke.geore.features.GeOreFeatures;
//import net.minecraft.core.Registry;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
//import net.minecraftforge.common.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary.Type;
//import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
//import net.minecraftforge.event.world.BiomeLoadingEvent;
//import net.minecraftforge.eventbus.api.EventPriority;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//
//public class GeOreWorldgen {
//	@SubscribeEvent(priority = EventPriority.HIGH)
//	public void biomeLoadingEvent(BiomeLoadingEvent event) {
//		ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
//		BiomeGenerationSettingsBuilder builder = event.getGeneration();
//
//		if (BiomeDictionary.hasType(biomeKey, Type.OVERWORLD)) {
//			if (GeOreConfig.COMMON.generateCoalGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.COAL_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateCopperGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.COPPER_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateDiamondGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.DIAMOND_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateEmeraldGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.EMERALD_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateGoldGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.GOLD_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateIronGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.IRON_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateLapisGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.LAPIS_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateRedstoneGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.REDSTONE_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateQuartzGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.QUARTZ_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateRubyGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.RUBY_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateSapphireGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.SAPPHIRE_GEORE.getPlacedFeature());
//			}
//			if (GeOreConfig.COMMON.generateTopazGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.TOPAZ_GEORE.getPlacedFeature());
//			}
//		}
//		if (BiomeDictionary.hasType(biomeKey, Type.NETHER)) {
//			if (GeOreConfig.COMMON.generateQuartzInNetherGeore.get()) {
//				builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.QUARTZ_GEORE.getPlacedFeature());
//			}
//		}
//	}
//}
