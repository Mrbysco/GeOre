package com.shynieke.geore.config;

import com.shynieke.geore.GeOre;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class GeOreConfig {
	public static class Client {
		public final ForgeConfigSpec.DoubleValue spyglassIntensity;

		Client(ForgeConfigSpec.Builder builder) {
			builder.comment("Client settings")
					.push("Client");

			spyglassIntensity = builder
					.comment("The intensity of the spyglass color overlay (0.5 = 50%) [Default: 0.5]")
					.defineInRange("spyglassIntensity", 0.5, 0.01, 1.0);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec clientSpec;
	public static final Client CLIENT;

	static {
		final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}


	public static class Common {
		public final ForgeConfigSpec.BooleanValue generateCoalGeore;
		public final ForgeConfigSpec.BooleanValue generateCopperGeore;
		public final ForgeConfigSpec.BooleanValue generateDiamondGeore;
		public final ForgeConfigSpec.BooleanValue generateEmeraldGeore;
		public final ForgeConfigSpec.BooleanValue generateGoldGeore;
		public final ForgeConfigSpec.BooleanValue generateIronGeore;
		public final ForgeConfigSpec.BooleanValue generateLapisGeore;
		public final ForgeConfigSpec.BooleanValue generateQuartzGeore;
		public final ForgeConfigSpec.BooleanValue generateQuartzInNetherGeore;
		public final ForgeConfigSpec.BooleanValue generateRedstoneGeore;
		public final ForgeConfigSpec.BooleanValue generateBuddingAncientDebris;
		public final ForgeConfigSpec.BooleanValue disablePistonPushForBuddingGeOre;
		//Mod support
		public final ForgeConfigSpec.BooleanValue generateRubyGeore;
		public final ForgeConfigSpec.BooleanValue generateSapphireGeore;
		public final ForgeConfigSpec.BooleanValue generateTopazGeore;
		public final ForgeConfigSpec.BooleanValue generateZincGeore;
		public final ForgeConfigSpec.BooleanValue generateUraniniteGeore;
		public final ForgeConfigSpec.BooleanValue generateBlackQuartzGeore;
		public final ForgeConfigSpec.BooleanValue generateMonaziteGeore;
		public final ForgeConfigSpec.BooleanValue generateAluminumGeore;
		public final ForgeConfigSpec.BooleanValue generateLeadGeore;
		public final ForgeConfigSpec.BooleanValue generateNickelGeore;
		public final ForgeConfigSpec.BooleanValue generateOsmiumGeore;
		public final ForgeConfigSpec.BooleanValue generatePlatinumGeore;
		public final ForgeConfigSpec.BooleanValue generateSilverGeore;
		public final ForgeConfigSpec.BooleanValue generateTinGeore;
		public final ForgeConfigSpec.BooleanValue generateTungstenGeore;
		public final ForgeConfigSpec.BooleanValue generateUraniumGeore;
		public final ForgeConfigSpec.BooleanValue generateAllthemodiumGeore;
		public final ForgeConfigSpec.BooleanValue generateVibraniumGeore;
		public final ForgeConfigSpec.BooleanValue generateUnobtainiumGeore;

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

			generateBuddingAncientDebris = builder
					.comment("Generate Budding Ancient Debris [Default: true]")
					.define("generateBuddingAncientDebris", true);

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

			generateUraniniteGeore = builder
					.comment("Generate Uraninite GeOre [Default: false]")
					.define("generateUraniniteGeore", false);

			generateBlackQuartzGeore = builder
					.comment("Generate Black Quartz GeOre [Default: false]")
					.define("generateBlackQuartzGeore", false);

			generateMonaziteGeore = builder
					.comment("Generate Monazite GeOre [Default: false]")
					.define("generateMonaziteGeore", false);

			generateAluminumGeore = builder
					.comment("Generate Aluminum GeOre [Default: false]")
					.define("generateAluminumGeore", false);

			generateLeadGeore = builder
					.comment("Generate Lead GeOre [Default: false]")
					.define("generateLeadGeore", false);

			generateNickelGeore = builder
					.comment("Generate Nickel GeOre [Default: false]")
					.define("generateNickelGeore", false);

			generateOsmiumGeore = builder
					.comment("Generate Osmium GeOre [Default: false]")
					.define("generateOsmiumGeore", false);

			generatePlatinumGeore = builder
					.comment("Generate Platinum GeOre [Default: false]")
					.define("generatePlatinumGeore", false);

			generateSilverGeore = builder
					.comment("Generate Silver GeOre [Default: false]")
					.define("generateSilverGeore", false);

			generateTinGeore = builder
					.comment("Generate Tin GeOre [Default: false]")
					.define("generateTinGeore", false);

			generateTungstenGeore = builder
					.comment("Generate Tungsten GeOre [Default: false]")
					.define("generateTungstenGeore", false);

			generateUraniumGeore = builder
					.comment("Generate Uranium GeOre [Default: false]")
					.define("generateUraniumGeore", false);

			generateAllthemodiumGeore = builder
					.comment("Generate Allthemodium GeOre [Default: false]")
					.define("generateAllthemodiumGeore", false);

			generateVibraniumGeore = builder
					.comment("Generate Vibranium GeOre [Default: false]")
					.define("generateVibraniumGeore", false);

			generateUnobtainiumGeore = builder
					.comment("Generate Unobtainium GeOre [Default: false]")
					.define("generateUnobtainiumGeore", false);

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
