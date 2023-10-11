package com.shynieke.geore.features;

import com.shynieke.geore.config.GeOreConfig;

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
	RUBY_GEORE("ruby", GeOreConfig.COMMON.generateRubyGeore),
	SAPPHIRE_GEORE("sapphire", GeOreConfig.COMMON.generateSapphireGeore),
	TOPAZ_GEORE("topaz", GeOreConfig.COMMON.generateTopazGeore),
	ZINC_GEORE("zinc", GeOreConfig.COMMON.generateZincGeore);

	public final String name;
	public final Supplier<Boolean> configValue;

	ConfigFeature(String name, Supplier<Boolean> configValue) {
		this.name = name;
		this.configValue = configValue;
	}

	public boolean getValue() {
		return configValue.get();
	}

	@Nullable
	public static ConfigFeature getByName(@Nullable String value) {
		for (ConfigFeature captcha : values()) {
			if (captcha.name.equals(value)) {
				return captcha;
			}
		}
		return FALSE;
	}
}
