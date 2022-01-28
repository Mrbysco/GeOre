package com.shynieke.geore.features;

import com.shynieke.geore.config.GeOreConfig;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class GeOreFeatures {
	//Just to initialize the features no matter what
	public static void initialize() {}

	public static final PlacementModifier RNG_DECORATOR = HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top());

//	public static final PlacementModifier<NoneDecoratorConfiguration> RNG_DECORATOR = GeOreRegistry.RNG_DECORATOR.get().configured(DecoratorConfiguration.NONE);

	public static GeOreFeatureReg COAL_GEORE = new GeOreFeatureReg("coal", GeOreRegistry.COAL_GEORE, GeOreConfig.COMMON.coalGeoreRarity::get, GeOreConfig.COMMON.coalGeoreMinY::get, GeOreConfig.COMMON.coalGeoreMaxY::get);
	public static GeOreFeatureReg COPPER_GEORE = new GeOreFeatureReg("copper", GeOreRegistry.COPPER_GEORE, GeOreConfig.COMMON.copperGeoreRarity::get, GeOreConfig.COMMON.copperGeoreMinY::get, GeOreConfig.COMMON.copperGeoreMaxY::get);
	public static GeOreFeatureReg DIAMOND_GEORE = new GeOreFeatureReg("diamond", GeOreRegistry.DIAMOND_GEORE, GeOreConfig.COMMON.diamondGeoreRarity::get, GeOreConfig.COMMON.diamondGeoreMinY::get, GeOreConfig.COMMON.diamondGeoreMaxY::get);
	public static GeOreFeatureReg EMERALD_GEORE = new GeOreFeatureReg("emerald", GeOreRegistry.EMERALD_GEORE, GeOreConfig.COMMON.emeraldGeoreRarity::get, GeOreConfig.COMMON.emeraldGeoreMinY::get, GeOreConfig.COMMON.emeraldGeoreMaxY::get);
	public static GeOreFeatureReg GOLD_GEORE = new GeOreFeatureReg("gold", GeOreRegistry.GOLD_GEORE, GeOreConfig.COMMON.goldGeoreRarity::get, GeOreConfig.COMMON.goldGeoreMinY::get, GeOreConfig.COMMON.goldGeoreMaxY::get);
	public static GeOreFeatureReg IRON_GEORE = new GeOreFeatureReg("iron", GeOreRegistry.IRON_GEORE, GeOreConfig.COMMON.ironGeoreRarity::get, GeOreConfig.COMMON.ironGeoreMinY::get, GeOreConfig.COMMON.ironGeoreMaxY::get);
	public static GeOreFeatureReg LAPIS_GEORE = new GeOreFeatureReg("lapis", GeOreRegistry.LAPIS_GEORE, GeOreConfig.COMMON.lapisGeoreRarity::get, GeOreConfig.COMMON.lapisGeoreMinY::get, GeOreConfig.COMMON.lapisGeoreMaxY::get);
	public static GeOreFeatureReg QUARTZ_GEORE = new GeOreFeatureReg("quartz", GeOreRegistry.QUARTZ_GEORE, GeOreConfig.COMMON.quartzGeoreRarity::get, GeOreConfig.COMMON.quartzGeoreMinY::get, GeOreConfig.COMMON.quartzGeoreMaxY::get);
	public static GeOreFeatureReg REDSTONE_GEORE = new GeOreFeatureReg("redstone", GeOreRegistry.REDSTONE_GEORE, GeOreConfig.COMMON.redstoneGeoreRarity::get, GeOreConfig.COMMON.redstoneGeoreMinY::get, GeOreConfig.COMMON.redstoneGeoreMaxY::get);
}
