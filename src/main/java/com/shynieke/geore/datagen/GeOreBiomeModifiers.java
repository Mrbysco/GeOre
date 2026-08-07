package com.shynieke.geore.datagen;

import com.shynieke.geore.Reference;
import com.shynieke.geore.features.GeOreFeatures;
import com.shynieke.geore.features.GeOrePlacedFeatures;
import com.shynieke.geore.modifier.AddConfigFeatureBiomeModifier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class GeOreBiomeModifiers {
	public static final TagKey<Biome> IS_DEEP_DARK = TagKey.create(Registries.BIOME, Reference.modLoc("is_deep_dark"));

	public static void bootstrap(BootstrapContext<BiomeModifier> context) {
		final HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
		final HolderGetter<PlacedFeature> placedHolderGetter = context.lookup(Registries.PLACED_FEATURE);

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
		GeOreFeatures.GLOWSTONE_GEORE.setupBiomeModifier(context, BiomeTags.IS_NETHER, "glowstone");
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

		GeOreFeatures.ALLTHEMODIUM_GEORE.setupBiomeModifier(context, IS_DEEP_DARK, "allthemodium");
		GeOreFeatures.VIBRANIUM_GEORE.setupBiomeModifier(context, BiomeTags.IS_NETHER, "vibranium");
		GeOreFeatures.UNOBTAINIUM_GEORE.setupBiomeModifier(context, BiomeTags.IS_END, "unobtainium");

		final AddConfigFeatureBiomeModifier addBuddingAncientDebris = new AddConfigFeatureBiomeModifier(
				biomeHolderGetter.getOrThrow(BiomeTags.IS_NETHER),
				HolderSet.direct(placedHolderGetter.getOrThrow(GeOrePlacedFeatures.ANCIENT_DEBRIS_PLACEMENT_KEY)),
				Decoration.LOCAL_MODIFICATIONS, "ancient_debris");

		context.register(createModifierKey("budding_ancient_debris"), addBuddingAncientDebris);
	}

	public static ResourceKey<BiomeModifier> createModifierKey(String name) {
		return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Reference.modLoc(name));
	}
}
