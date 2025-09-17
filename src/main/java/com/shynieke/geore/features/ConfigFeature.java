package com.shynieke.geore.features;

import com.shynieke.geore.config.GeOreConfig;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum ConfigFeature {
	FALSE("invalid", () -> false),
	COAL_GEORE("coal", GeOreConfig.COMMON.generateCoalGeore),
	COPPER_GEORE("copper", GeOreConfig.COMMON.generateCopperGeore),
	DIAMOND_GEORE("diamond", GeOreConfig.COMMON.generateDiamondGeore),
	EMERALD_GEORE("emerald", GeOreConfig.COMMON.generateEmeraldGeore),
	GOLD_GEORE("gold", GeOreConfig.COMMON.generateGoldGeore),
	IRON_GEORE("iron", GeOreConfig.COMMON.generateIronGeore),
	LAPIS_GEORE("lapis", GeOreConfig.COMMON.generateLapisGeore),
	QUARTZ_GEORE("quartz", GeOreConfig.COMMON.generateQuartzGeore),
	QUARTZ_NETHER_GEORE("quartz_nether", GeOreConfig.COMMON.generateQuartzInNetherGeore),
	REDSTONE_GEORE("redstone", GeOreConfig.COMMON.generateRedstoneGeore),
	ANCIENT_DEBRIS_GEORE("ancient_debris", GeOreConfig.COMMON.generateBuddingAncientDebris),
	RUBY_GEORE("ruby", GeOreConfig.COMMON.generateRubyGeore),
	SAPPHIRE_GEORE("sapphire", GeOreConfig.COMMON.generateSapphireGeore),
	TOPAZ_GEORE("topaz", GeOreConfig.COMMON.generateTopazGeore),
	ZINC_GEORE("zinc", GeOreConfig.COMMON.generateZincGeore),
	URANINITE_GEORE("uraninite", GeOreConfig.COMMON.generateUraniniteGeore),
	BLACK_QUARTZ_GEORE("black_quartz", GeOreConfig.COMMON.generateBlackQuartzGeore),
	MONAZITE_GEORE("monazite", GeOreConfig.COMMON.generateMonaziteGeore),
	ALUMINUM_GEORE("aluminum", GeOreConfig.COMMON.generateAluminumGeore),
	LEAD_GEORE("lead", GeOreConfig.COMMON.generateLeadGeore),
	NICKEL_GEORE("nickel", GeOreConfig.COMMON.generateNickelGeore),
	OSMIUM_GEORE("osmium", GeOreConfig.COMMON.generateOsmiumGeore),
	PLATINUM_GEORE("platinum", GeOreConfig.COMMON.generatePlatinumGeore),
	SILVER_GEORE("silver", GeOreConfig.COMMON.generateSilverGeore),
	TIN_GEORE("tin", GeOreConfig.COMMON.generateTinGeore),
	TUNGSTEN_GEORE("tungsten", GeOreConfig.COMMON.generateTungstenGeore),
	URANIUM_GEORE("uranium", GeOreConfig.COMMON.generateUraniumGeore),
	ALLTHEMODIUM_GEORE("allthemodium", GeOreConfig.COMMON.generateAllthemodiumGeore),
	VIBRANIUM_GEORE("vibranium", GeOreConfig.COMMON.generateVibraniumGeore),
	UNOBTAINIUM_GEORE("unobtainium", GeOreConfig.COMMON.generateUnobtainiumGeore);

	public final String name;
	public final Supplier<Boolean> configValue;

	ConfigFeature(String name, Supplier<Boolean> configValue) {
		this.name = name;
		this.configValue = configValue;
	}

	public boolean getValue() {
		return configValue.get();
	}

	@NotNull
	public static ConfigFeature getByName(@Nullable String value) {
		for (ConfigFeature captcha : values()) {
			if (captcha.name.equals(value)) {
				return captcha;
			}
		}
		return FALSE;
	}
}
