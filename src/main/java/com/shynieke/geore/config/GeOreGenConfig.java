package com.shynieke.geore.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.List;
import java.util.Locale;

public class GeOreGenConfig {
	public final BooleanValue generateGeOre;
	public final IntValue GeOreRarity;
	public final IntValue GeOreMinY;
	public final IntValue GeOreMaxY;
	public final ConfigValue<List<? extends String>> GeOreDictionaryType;

	public GeOreGenConfig(ForgeConfigSpec.Builder builder, String geOre, boolean generate, int minY, int maxY, int rarity, String... dictionaryType) {
		String lowerCase = geOre.toLowerCase(Locale.ROOT);

		builder.comment(geOre + " settings")
				.push(geOre);

		generateGeOre = builder
				.comment("Generate " + geOre + " GeOre [Default: " + generate + "]")
				.define("generate" + geOre + "Geore", generate);

		GeOreMinY = builder
				.comment("The Min Y level that " + geOre + " GeOre will generate at (above minimum world height) [Default: " + minY + "]")
				.defineInRange(lowerCase + "GeoreMinY", minY, 0, Integer.MAX_VALUE);

		GeOreMaxY = builder
				.comment("The max Y level that " + geOre + " GeOre will generate at [Default: " + maxY + "]")
				.defineInRange(lowerCase + "GeoreMaxY", maxY, 0, Integer.MAX_VALUE);

		GeOreRarity = builder
				.comment(geOre + " GeOre Rarity [Default: " + rarity + "] (The higher the value the rarer)")
				.defineInRange(lowerCase + "GeoreRarity", rarity, 0, Integer.MAX_VALUE);

		GeOreDictionaryType = builder
				.comment("Biomes that have any of these Biome Dictionary Tags will be able to generate " + geOre + " GeOre [Default: " +
						List.of(dictionaryType).toString().replace("[", "").replace("]", "") + "]")
				.defineListAllowEmpty(List.of(lowerCase + "GeoreDictionaryTypes"), () -> List.of(dictionaryType), o -> o instanceof String);

		builder.pop();
	}
}
