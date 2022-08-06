package com.shynieke.geore.config;

import com.shynieke.geore.GeOre;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
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
		//Mod support
		public final BooleanValue generateRubyGeore;
		public final BooleanValue generateSapphireGeore;
		public final BooleanValue generateTopazGeore;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("General");

			generateCoalGeore = builder
					.comment("Generate Coal GeOre [Default: true]")
					.define("generateCoalGeore", true);

			generateCopperGeore = builder
					.comment("Generate Copper GeOre [Default: true]")
					.define("generateCopperGeore", true);

			generateDiamondGeore = builder
					.comment("Generate Diamond GeOre [Default: true]")
					.define("generateDiamondGeore", true);

			generateEmeraldGeore = builder
					.comment("Generate Emerald GeOre [Default: true]")
					.define("generateEmeraldGeore", true);

			generateGoldGeore = builder
					.comment("Generate Gold GeOre [Default: true]")
					.define("generateGoldGeore", true);

			generateIronGeore = builder
					.comment("Generate Iron GeOre [Default: true]")
					.define("generateIronGeore", true);

			generateLapisGeore = builder
					.comment("Generate Lapis GeOre [Default: true]")
					.define("generateLapisGeore", true);

			generateQuartzGeore = builder
					.comment("Generate Quartz GeOre [Default: true]")
					.define("generateQuartzGeore", true);

			generateQuartzInNetherGeore = builder
					.comment("Generate Quartz GeOre in the Nether [Default: true]")
					.define("generateQuartzInNetherGeore", true);

			generateRedstoneGeore = builder
					.comment("Generate Redstone GeOre [Default: true]")
					.define("generateRedstoneGeore", true);

			builder.pop();
			builder.comment("Modded Generation settings")
					.push("ModdedGeneration");

			generateRubyGeore = builder
					.comment("Generate Ruby GeOre [Default: false]")
					.define("generateRubyGeore", false);

			generateSapphireGeore = builder
					.comment("Generate Sapphire GeOre [Default: false]")
					.define("generateSapphireGeore", false);

			generateTopazGeore = builder
					.comment("Generate Topaz GeOre [Default: false]")
					.define("generateTopazGeore", false);

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
		GeOre.LOGGER.debug("Loaded GeOre's enabledConfig file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		GeOre.LOGGER.debug("GeOre's enabledConfig just got changed on the file system!");
	}
}
