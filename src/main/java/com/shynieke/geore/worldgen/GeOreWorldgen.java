package com.shynieke.geore.worldgen;

import com.shynieke.geore.config.GeOreConfig;
import com.shynieke.geore.features.GeOreFeatures;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GeOreWorldgen {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void biomeLoadingEvent(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder builder = event.getGeneration();

		if(GeOreConfig.COMMON.generateCoalGeore.get()) {
			builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.COAL_GEORE.getGeodeFeature().rarity(GeOreConfig.COMMON.coalGeoreRarity.get()).decorated(GeOreFeatures.RNG_DECORATOR));
		}
		if(GeOreConfig.COMMON.generateCopperGeore.get()) {
			builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.COPPER_GEORE.getGeodeFeature().rarity(GeOreConfig.COMMON.copperGeoreRarity.get()).decorated(GeOreFeatures.RNG_DECORATOR));
		}
		if(GeOreConfig.COMMON.generateDiamondGeore.get()) {
			builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.DIAMOND_GEORE.getGeodeFeature().rarity(GeOreConfig.COMMON.diamondGeoreRarity.get()).decorated(GeOreFeatures.RNG_DECORATOR));
		}
		if(GeOreConfig.COMMON.generateEmeraldGeore.get()) {
			builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.EMERALD_GEORE.getGeodeFeature().rarity(GeOreConfig.COMMON.emeraldGeoreRarity.get()).decorated(GeOreFeatures.RNG_DECORATOR));
		}
		if(GeOreConfig.COMMON.generateGoldGeore.get()) {
			builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.GOLD_GEORE.getGeodeFeature().rarity(GeOreConfig.COMMON.goldGeoreRarity.get()).decorated(GeOreFeatures.RNG_DECORATOR));
		}
		if(GeOreConfig.COMMON.generateIronGeore.get()) {
			builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.IRON_GEORE.getGeodeFeature().rarity(GeOreConfig.COMMON.ironGeoreRarity.get()).decorated(GeOreFeatures.RNG_DECORATOR));
		}
		if(GeOreConfig.COMMON.generateLapisGeore.get()) {
			builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.LAPIS_GEORE.getGeodeFeature().rarity(GeOreConfig.COMMON.lapisGeoreRarity.get()).decorated(GeOreFeatures.RNG_DECORATOR));
		}
		if(GeOreConfig.COMMON.generateQuartzGeore.get()) {
			builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.QUARTZ_GEORE.getGeodeFeature().rarity(GeOreConfig.COMMON.quartzGeoreRarity.get()).decorated(GeOreFeatures.RNG_DECORATOR));
		}
		if(GeOreConfig.COMMON.generateRedstoneGeore.get()) {
			builder.addFeature(Decoration.LOCAL_MODIFICATIONS, GeOreFeatures.REDSTONE_GEORE.getGeodeFeature().rarity(GeOreConfig.COMMON.redstoneGeoreRarity.get()).decorated(GeOreFeatures.RNG_DECORATOR));
		}
	}
}
