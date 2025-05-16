package com.shynieke.geore.datagen;

import com.shynieke.geore.features.GeOreFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BiomeTags;
import net.neoforged.neoforge.common.world.BiomeModifier;

public class GeOreBiomeModifiers {

	public static void bootstrap(BootstrapContext<BiomeModifier> context) {
		GeOreFeatures.COAL_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "coal");
		GeOreFeatures.COPPER_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "copper");
		GeOreFeatures.DIAMOND_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "diamond");
		GeOreFeatures.EMERALD_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "emerald");
		GeOreFeatures.GOLD_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "gold");
		GeOreFeatures.IRON_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "iron");
		GeOreFeatures.LAPIS_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "lapis");
		GeOreFeatures.QUARTZ_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "quartz");
		GeOreFeatures.QUARTZ_GEORE.setupBiomeModifier(context, BiomeTags.IS_NETHER, "quartz_nether");
		GeOreFeatures.REDSTONE_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "redstone");
		GeOreFeatures.RUBY_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "ruby");
		GeOreFeatures.SAPPHIRE_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "sapphire");
		GeOreFeatures.TOPAZ_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "topaz");
		GeOreFeatures.ZINC_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "zinc");
		GeOreFeatures.URANINITE_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "uraninite");
		GeOreFeatures.BLACK_QUARTZ_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "black_quartz");
		GeOreFeatures.MONAZITE_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "monazite");
		GeOreFeatures.ALUMINUM_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "aluminum");
		GeOreFeatures.LEAD_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "lead");
		GeOreFeatures.NICKEL_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "nickel");
		GeOreFeatures.OSMIUM_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "osmium");
		GeOreFeatures.PLATINUM_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "platinum");
		GeOreFeatures.SILVER_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "silver");
		GeOreFeatures.TIN_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "tin");
		GeOreFeatures.TUNGSTEN_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "tungsten");
		GeOreFeatures.URANIUM_GEORE.setupBiomeModifier(context, BiomeTags.IS_OVERWORLD, "uranium");
	}
}
