package com.shynieke.geore.config;

import com.shynieke.geore.GeOre;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class GeOreConfig {
	public static class Client {
		public final ModConfigSpec.DoubleValue spyglassIntensity;

		Client(ModConfigSpec.Builder builder) {
			builder.comment("Client settings")
					.push("Client");

			spyglassIntensity = builder
					.comment("The intensity of the spyglass color overlay (0.5 = 50%) [Default: 0.5]")
					.defineInRange("spyglassIntensity", 0.5, 0.01, 1.0);

			builder.pop();
		}
	}

	public static final ModConfigSpec clientSpec;
	public static final Client CLIENT;

	static {
		final Pair<Client, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Client::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}


	public static class Common {
		public final ModConfigSpec.BooleanValue generateCoalGeore;
		public final ModConfigSpec.BooleanValue generateCopperGeore;
		public final ModConfigSpec.BooleanValue generateDiamondGeore;
		public final ModConfigSpec.BooleanValue generateEmeraldGeore;
		public final ModConfigSpec.BooleanValue generateGoldGeore;
		public final ModConfigSpec.BooleanValue generateIronGeore;
		public final ModConfigSpec.BooleanValue generateLapisGeore;
		public final ModConfigSpec.BooleanValue generateQuartzGeore;
		public final ModConfigSpec.BooleanValue generateQuartzInNetherGeore;
		public final ModConfigSpec.BooleanValue generateRedstoneGeore;
		public final ModConfigSpec.BooleanValue disablePistonPushForBuddingGeOre;
		//Mod support
		public final ModConfigSpec.BooleanValue generateRubyGeore;
		public final ModConfigSpec.BooleanValue generateSapphireGeore;
		public final ModConfigSpec.BooleanValue generateTopazGeore;
		public final ModConfigSpec.BooleanValue generateZincGeore;

		Common(ModConfigSpec.Builder builder) {
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

			disablePistonPushForBuddingGeOre = builder
					.comment("Disable piston push for budding GeOre (Overrides the vanilla behavior of breaking the block upon being pushed) [Default: false]")
					.define("disablePistonPushForBuddingGeOre", false);

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

			generateZincGeore = builder
					.comment("Generate Zinc GeOre [Default: false]")
					.define("generateZincGeore", false);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
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
