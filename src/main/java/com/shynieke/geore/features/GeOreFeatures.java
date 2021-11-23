package com.shynieke.geore.features;

import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;

public class GeOreFeatures {
	//Just to initialize the features no matter what
	public static void initialize() {}

	public static final ConfiguredDecorator<NoneDecoratorConfiguration> RNG_DECORATOR = GeOreRegistry.RNG_DECORATOR.get().configured(DecoratorConfiguration.NONE);

	public static GeOreFeatureReg COAL_GEORE = new GeOreFeatureReg("coal", GeOreRegistry.COAL_GEORE);
	public static GeOreFeatureReg COPPER_GEORE = new GeOreFeatureReg("copper", GeOreRegistry.COPPER_GEORE);
	public static GeOreFeatureReg DIAMOND_GEORE = new GeOreFeatureReg("diamond", GeOreRegistry.DIAMOND_GEORE);
	public static GeOreFeatureReg EMERALD_GEORE = new GeOreFeatureReg("emerald", GeOreRegistry.EMERALD_GEORE);
	public static GeOreFeatureReg GOLD_GEORE = new GeOreFeatureReg("gold", GeOreRegistry.GOLD_GEORE);
	public static GeOreFeatureReg IRON_GEORE = new GeOreFeatureReg("iron", GeOreRegistry.IRON_GEORE);
	public static GeOreFeatureReg LAPIS_GEORE = new GeOreFeatureReg("lapis", GeOreRegistry.LAPIS_GEORE);
	public static GeOreFeatureReg QUARTZ_GEORE = new GeOreFeatureReg("quartz", GeOreRegistry.QUARTZ_GEORE);
	public static GeOreFeatureReg REDSTONE_GEORE = new GeOreFeatureReg("redstone", GeOreRegistry.REDSTONE_GEORE);

	public static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, name, configuredFeature);
	}
}
