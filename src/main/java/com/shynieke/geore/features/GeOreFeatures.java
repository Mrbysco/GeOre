package com.shynieke.geore.features;

import com.shynieke.geore.config.GeOreConfig;
import com.shynieke.geore.registry.GeOreRegistry;

public class GeOreFeatures {
	public static void initialize() {
		//Just to initialize the features no matter what
	}

	public static final GeOreFeatureReg COAL_GEORE = new GeOreFeatureReg("coal", GeOreRegistry.COAL_GEORE, GeOreConfig.COMMON.coalGeOre.GeOreRarity, GeOreConfig.COMMON.coalGeOre.GeOreMinY, GeOreConfig.COMMON.coalGeOre.GeOreMaxY);
	public static final GeOreFeatureReg COPPER_GEORE = new GeOreFeatureReg("copper", GeOreRegistry.COPPER_GEORE, GeOreConfig.COMMON.copperGeOre.GeOreRarity, GeOreConfig.COMMON.copperGeOre.GeOreMinY, GeOreConfig.COMMON.copperGeOre.GeOreMaxY);
	public static final GeOreFeatureReg DIAMOND_GEORE = new GeOreFeatureReg("diamond", GeOreRegistry.DIAMOND_GEORE, GeOreConfig.COMMON.diamondGeOre.GeOreRarity, GeOreConfig.COMMON.diamondGeOre.GeOreMinY, GeOreConfig.COMMON.diamondGeOre.GeOreMaxY);
	public static final GeOreFeatureReg EMERALD_GEORE = new GeOreFeatureReg("emerald", GeOreRegistry.EMERALD_GEORE, GeOreConfig.COMMON.emeraldGeOre.GeOreRarity, GeOreConfig.COMMON.emeraldGeOre.GeOreMinY, GeOreConfig.COMMON.emeraldGeOre.GeOreMaxY);
	public static final GeOreFeatureReg GOLD_GEORE = new GeOreFeatureReg("gold", GeOreRegistry.GOLD_GEORE, GeOreConfig.COMMON.goldGeOre.GeOreRarity, GeOreConfig.COMMON.goldGeOre.GeOreMinY, GeOreConfig.COMMON.goldGeOre.GeOreMaxY);
	public static final GeOreFeatureReg IRON_GEORE = new GeOreFeatureReg("iron", GeOreRegistry.IRON_GEORE, GeOreConfig.COMMON.ironGeOre.GeOreRarity, GeOreConfig.COMMON.ironGeOre.GeOreMinY, GeOreConfig.COMMON.ironGeOre.GeOreMaxY);
	public static final GeOreFeatureReg LAPIS_GEORE = new GeOreFeatureReg("lapis", GeOreRegistry.LAPIS_GEORE, GeOreConfig.COMMON.lapisGeOre.GeOreRarity, GeOreConfig.COMMON.lapisGeOre.GeOreMinY, GeOreConfig.COMMON.lapisGeOre.GeOreMaxY);
	public static final GeOreFeatureReg QUARTZ_GEORE = new GeOreFeatureReg("quartz", GeOreRegistry.QUARTZ_GEORE, GeOreConfig.COMMON.quartzGeOre.GeOreRarity, GeOreConfig.COMMON.quartzGeOre.GeOreMinY, GeOreConfig.COMMON.quartzGeOre.GeOreMaxY);
	public static final GeOreFeatureReg REDSTONE_GEORE = new GeOreFeatureReg("redstone", GeOreRegistry.REDSTONE_GEORE, GeOreConfig.COMMON.redstoneGeOre.GeOreRarity, GeOreConfig.COMMON.redstoneGeOre.GeOreMinY, GeOreConfig.COMMON.redstoneGeOre.GeOreMaxY);

	public static final GeOreFeatureReg RUBY_GEORE = new GeOreFeatureReg("ruby", GeOreRegistry.RUBY_GEORE, GeOreConfig.COMMON.rubyGeOre.GeOreRarity, GeOreConfig.COMMON.rubyGeOre.GeOreMinY, GeOreConfig.COMMON.rubyGeOre.GeOreMaxY);
	public static final GeOreFeatureReg SAPPHIRE_GEORE = new GeOreFeatureReg("sapphire", GeOreRegistry.SAPPHIRE_GEORE, GeOreConfig.COMMON.sapphireGeOre.GeOreRarity, GeOreConfig.COMMON.sapphireGeOre.GeOreMinY, GeOreConfig.COMMON.sapphireGeOre.GeOreMaxY);
	public static final GeOreFeatureReg TOPAZ_GEORE = new GeOreFeatureReg("topaz", GeOreRegistry.TOPAZ_GEORE, GeOreConfig.COMMON.topazGeOre.GeOreRarity, GeOreConfig.COMMON.topazGeOre.GeOreMinY, GeOreConfig.COMMON.topazGeOre.GeOreMaxY);
}
