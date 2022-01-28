package com.shynieke.geore.config;

import com.shynieke.geore.GeOre;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class GeOreConfig {
	public static class Common {
		public final BooleanValue generateCoalGeore;
		public final BooleanValue generateCopperGeore;
		public final BooleanValue generateDiamondGeore;
		public final BooleanValue generateEmeraldGeore;
		public final BooleanValue generateGoldGeore;
		public final BooleanValue generateIronGeore;
		public final BooleanValue generateLapisGeore;
		public final BooleanValue generateQuartzGeore;
		public final BooleanValue generateQuartzInNetherGeore;
		public final BooleanValue generateRedstoneGeore;

		public final IntValue coalGeoreRarity;
		public final IntValue copperGeoreRarity;
		public final IntValue diamondGeoreRarity;
		public final IntValue emeraldGeoreRarity;
		public final IntValue goldGeoreRarity;
		public final IntValue ironGeoreRarity;
		public final IntValue lapisGeoreRarity;
		public final IntValue quartzGeoreRarity;
		public final IntValue redstoneGeoreRarity;

		public final IntValue coalGeoreMinY;
		public final IntValue coalGeoreMaxY;
		public final IntValue copperGeoreMinY;
		public final IntValue copperGeoreMaxY;
		public final IntValue diamondGeoreMinY;
		public final IntValue diamondGeoreMaxY;
		public final IntValue emeraldGeoreMinY;
		public final IntValue emeraldGeoreMaxY;
		public final IntValue goldGeoreMinY;
		public final IntValue goldGeoreMaxY;
		public final IntValue ironGeoreMinY;
		public final IntValue ironGeoreMaxY;
		public final IntValue lapisGeoreMinY;
		public final IntValue lapisGeoreMaxY;
		public final IntValue quartzGeoreMinY;
		public final IntValue quartzGeoreMaxY;
		public final IntValue redstoneGeoreMinY;
		public final IntValue redstoneGeoreMaxY;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Generation settings")
					.push("Generation");

			generateCoalGeore = builder
					.comment("Generate Coal GeOre [Default: true]")
					.define("generateCoalGeore", true);

			coalGeoreMinY = builder
					.comment("The Min Y level that Coal GeOre will generate at (above minimum world height) [Default: 6]")
					.defineInRange("coalGeoreMinY", 6, 0, Integer.MAX_VALUE);

			coalGeoreMaxY = builder
					.comment("The max Y level that Coal GeOre will generate at [Default: 30]")
					.defineInRange("coalGeoreMaxY", 30, 0, Integer.MAX_VALUE);

			generateCopperGeore = builder
					.comment("Generate Copper GeOre [Default: true]")
					.define("generateCopperGeore", true);

			copperGeoreMinY = builder
					.comment("The Min Y level that Copper GeOre will generate at (above minimum world height) [Default: 6]")
					.defineInRange("copperGeoreMinY", 6, 0, Integer.MAX_VALUE);

			copperGeoreMaxY = builder
					.comment("The max Y level that Copper GeOre will generate at [Default: 30]")
					.defineInRange("copperGeoreMaxY", 30, 0, Integer.MAX_VALUE);

			generateDiamondGeore = builder
					.comment("Generate Diamond GeOre [Default: true]")
					.define("generateDiamondGeore", true);
			diamondGeoreMinY = builder
					.comment("The Min Y level that Diamond GeOre will generate at (above minimum world height) [Default: 6]")
					.defineInRange("diamondGeoreMinY", 6, 0, Integer.MAX_VALUE);

			diamondGeoreMaxY = builder
					.comment("The max Y level that Diamond GeOre will generate at [Default: 30]")
					.defineInRange("diamondGeoreMaxY", 30, 0, Integer.MAX_VALUE);

			generateEmeraldGeore = builder
					.comment("Generate Emerald GeOre [Default: true]")
					.define("generateEmeraldGeore", true);

			emeraldGeoreMinY = builder
					.comment("The Min Y level that Emerald GeOre will generate at (above minimum world height) [Default: 6]")
					.defineInRange("emeraldGeoreMinY", 6, 0, Integer.MAX_VALUE);

			emeraldGeoreMaxY = builder
					.comment("The max Y level that Emerald GeOre will generate at [Default: 30]")
					.defineInRange("emeraldGeoreMaxY", 30, 0, Integer.MAX_VALUE);

			generateGoldGeore = builder
					.comment("Generate Gold GeOre [Default: true]")
					.define("generateGoldGeore", true);

			goldGeoreMinY = builder
					.comment("The Min Y level that Gold GeOre will generate at (above minimum world height) [Default: 6]")
					.defineInRange("goldGeoreMinY", 6, 0, Integer.MAX_VALUE);

			goldGeoreMaxY = builder
					.comment("The max Y level that Gold GeOre will generate at [Default: 30]")
					.defineInRange("goldGeoreMaxY", 30, 0, Integer.MAX_VALUE);

			generateIronGeore = builder
					.comment("Generate Iron GeOre [Default: true]")
					.define("generateIronGeore", true);

			ironGeoreMinY = builder
					.comment("The Min Y level that Iron GeOre will generate at (above minimum world height) [Default: 6]")
					.defineInRange("ironGeoreMinY", 6, 0, Integer.MAX_VALUE);

			ironGeoreMaxY = builder
					.comment("The max Y level that Iron GeOre will generate at [Default: 30]")
					.defineInRange("ironGeoreMaxY", 30, 0, Integer.MAX_VALUE);

			generateLapisGeore = builder
					.comment("Generate Lapis GeOre [Default: true]")
					.define("generateLapisGeore", true);

			lapisGeoreMinY = builder
					.comment("The Min Y level that Lapis GeOre will generate at (above minimum world height) [Default: 6]")
					.defineInRange("lapisGeoreMinY", 6, 0, Integer.MAX_VALUE);

			lapisGeoreMaxY = builder
					.comment("The max Y level that Lapis GeOre will generate at [Default: 30]")
					.defineInRange("lapisGeoreMaxY", 30, 0, Integer.MAX_VALUE);

			generateQuartzGeore = builder
					.comment("Generate Quartz GeOre [Default: true]")
					.define("generateQuartzGeore", true);

			quartzGeoreMinY = builder
					.comment("The Min Y level that Quartz GeOre will generate at (above minimum world height) [Default: 6]")
					.defineInRange("quartzGeoreMinY", 6, 0, Integer.MAX_VALUE);

			quartzGeoreMaxY = builder
					.comment("The max Y level that Quartz GeOre will generate at [Default: 30]")
					.defineInRange("quartzGeoreMaxY", 30, 0, Integer.MAX_VALUE);

			generateQuartzInNetherGeore = builder
					.comment("Generate Quartz GeOre in the Nether [Default: true]")
					.define("generateQuartzInNetherGeore", true);

			generateRedstoneGeore = builder
					.comment("Generate Redstone GeOre [Default: true]")
					.define("generateRedstoneGeore", true);

			redstoneGeoreMinY = builder
					.comment("The Min Y level that Redstone GeOre will generate at (above minimum world height) [Default: 6]")
					.defineInRange("redstoneGeoreMinY", 6, 0, Integer.MAX_VALUE);

			redstoneGeoreMaxY = builder
					.comment("The max Y level that Redstone GeOre will generate at [Default: 30]")
					.defineInRange("redstoneGeoreMaxY", 30, 0, Integer.MAX_VALUE);

			builder.pop();
			builder.comment("Rarity settings")
					.push("Rarity");

			coalGeoreRarity = builder
					.comment("Coal GeOre Rarity [Default: 60] (The higher the value the rarer)")
					.defineInRange("coalGeoreRarity", 60, 0, Integer.MAX_VALUE);

			copperGeoreRarity = builder
					.comment("Copper GeOre Rarity [Default: 90] (The higher the value the rarer)")
					.defineInRange("copperGeoreRarity", 90, 0, Integer.MAX_VALUE);

			diamondGeoreRarity = builder
					.comment("Diamond GeOre Rarity [Default: 270] (The higher the value the rarer)")
					.defineInRange("diamondGeoreRarity", 330, 0, Integer.MAX_VALUE);

			emeraldGeoreRarity = builder
					.comment("Emerald GeOre Rarity [Default: 300] (The higher the value the rarer)")
					.defineInRange("emeraldGeoreRarity", 420, 0, Integer.MAX_VALUE);

			goldGeoreRarity = builder
					.comment("Gold GeOre Rarity [Default: 180] (The higher the value the rarer)")
					.defineInRange("goldGeoreRarity", 180, 0, Integer.MAX_VALUE);

			ironGeoreRarity = builder
					.comment("Iron GeOre Rarity [Default: 120] (The higher the value the rarer)")
					.defineInRange("ironGeoreRarity", 120, 0, Integer.MAX_VALUE);

			lapisGeoreRarity = builder
					.comment("Lapis GeOre Rarity [Default: 210] (The higher the value the rarer)")
					.defineInRange("lapisGeoreRarity", 210, 0, Integer.MAX_VALUE);

			quartzGeoreRarity = builder
					.comment("Quartz GeOre Rarity [Default: 150] (The higher the value the rarer)")
					.defineInRange("quartzGeoreRarity", 150, 0, Integer.MAX_VALUE);

			redstoneGeoreRarity = builder
					.comment("Redstone GeOre Rarity [Default: 240] (The higher the value the rarer)")
					.defineInRange("redstoneGeoreRarity", 240, 0, Integer.MAX_VALUE);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		GeOre.LOGGER.debug("Loaded GeOre's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		GeOre.LOGGER.debug("GeOre's config just got changed on the file system!");
	}
}
