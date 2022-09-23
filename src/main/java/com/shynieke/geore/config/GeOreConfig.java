package com.shynieke.geore.config;

import com.shynieke.geore.GeOre;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class GeOreConfig {
	public static class Common {
		public final GeOreGenConfig coalGeOre;
		public final GeOreGenConfig copperGeOre;
		public final GeOreGenConfig diamondGeOre;
		public final GeOreGenConfig emeraldGeOre;
		public final GeOreGenConfig goldGeOre;
		public final GeOreGenConfig ironGeOre;
		public final GeOreGenConfig lapisGeOre;
		public final GeOreGenConfig quartzGeOre;
		public final GeOreGenConfig redstoneGeOre;

		public final GeOreGenConfig rubyGeOre;
		public final GeOreGenConfig sapphireGeOre;
		public final GeOreGenConfig topazGeOre;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Generation settings")
					.push("Generation");

			coalGeOre = new GeOreGenConfig(builder, "Coal", true, 6, 30, 60, "OVERWORLD");
			copperGeOre = new GeOreGenConfig(builder, "Copper", true, 6, 30, 90, "OVERWORLD");
			diamondGeOre = new GeOreGenConfig(builder, "Diamond", true, 6, 30, 330, "OVERWORLD");
			emeraldGeOre = new GeOreGenConfig(builder, "Emerald", true, 6, 30, 420, "OVERWORLD");
			goldGeOre = new GeOreGenConfig(builder, "Gold", true, 6, 30, 180, "OVERWORLD");
			ironGeOre = new GeOreGenConfig(builder, "Iron", true, 6, 30, 120, "OVERWORLD");
			lapisGeOre = new GeOreGenConfig(builder, "Lapis", true, 6, 30, 210, "OVERWORLD");
			quartzGeOre = new GeOreGenConfig(builder, "Quartz", true, 6, 30, 150, "OVERWORLD", "NETHER");
			redstoneGeOre = new GeOreGenConfig(builder, "Redstone", true, 6, 30, 240, "OVERWORLD");

			builder.pop();
			builder.comment("Modded settings")
					.push("Modded");

			rubyGeOre = new GeOreGenConfig(builder, "Ruby", false, 6, 30, 240, "OVERWORLD");
			sapphireGeOre = new GeOreGenConfig(builder, "Sapphire", false, 6, 30, 240, "OVERWORLD");
			topazGeOre = new GeOreGenConfig(builder, "Topaz", false, 6, 30, 240, "OVERWORLD");

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
