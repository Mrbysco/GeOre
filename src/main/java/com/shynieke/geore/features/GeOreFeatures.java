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

	public static GeOreFeatureReg COAL_GEORE = new GeOreFeatureReg("coal", GeOreRegistry.COAL_GEORE, GeOreConfig.COMMON.coalGeoreRarity::get);
	public static GeOreFeatureReg COPPER_GEORE = new GeOreFeatureReg("copper", GeOreRegistry.COPPER_GEORE, GeOreConfig.COMMON.copperGeoreRarity::get);
	public static GeOreFeatureReg DIAMOND_GEORE = new GeOreFeatureReg("diamond", GeOreRegistry.DIAMOND_GEORE, GeOreConfig.COMMON.diamondGeoreRarity::get);
	public static GeOreFeatureReg EMERALD_GEORE = new GeOreFeatureReg("emerald", GeOreRegistry.EMERALD_GEORE, GeOreConfig.COMMON.emeraldGeoreRarity::get);
	public static GeOreFeatureReg GOLD_GEORE = new GeOreFeatureReg("gold", GeOreRegistry.GOLD_GEORE, GeOreConfig.COMMON.goldGeoreRarity::get);
	public static GeOreFeatureReg IRON_GEORE = new GeOreFeatureReg("iron", GeOreRegistry.IRON_GEORE, GeOreConfig.COMMON.ironGeoreRarity::get);
	public static GeOreFeatureReg LAPIS_GEORE = new GeOreFeatureReg("lapis", GeOreRegistry.LAPIS_GEORE, GeOreConfig.COMMON.lapisGeoreRarity::get);
	public static GeOreFeatureReg QUARTZ_GEORE = new GeOreFeatureReg("quartz", GeOreRegistry.QUARTZ_GEORE, GeOreConfig.COMMON.quartzGeoreRarity::get);
	public static GeOreFeatureReg REDSTONE_GEORE = new GeOreFeatureReg("redstone", GeOreRegistry.REDSTONE_GEORE, GeOreConfig.COMMON.redstoneGeoreRarity::get);
}
