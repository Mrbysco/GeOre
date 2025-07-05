package com.shynieke.geore.datagen.client;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class GeOreLanguageProvider extends LanguageProvider {
	public GeOreLanguageProvider(PackOutput packOutput) {
		super(packOutput, Reference.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup.geore", "GeOre");

		generateLang("Coal", GeOreRegistry.COAL_GEORE);
		generateLang("Copper", GeOreRegistry.COPPER_GEORE);
		generateLang("Diamond", GeOreRegistry.DIAMOND_GEORE);
		generateLang("Emerald", GeOreRegistry.EMERALD_GEORE);
		generateLang("Gold", GeOreRegistry.GOLD_GEORE);
		generateLang("Iron", GeOreRegistry.IRON_GEORE);
		generateLang("Lapis", GeOreRegistry.LAPIS_GEORE);
		generateLang("Quartz", GeOreRegistry.QUARTZ_GEORE);
		generateLang("Redstone", GeOreRegistry.REDSTONE_GEORE);
		generateLang("Ancient Debris", GeOreRegistry.ANCIENT_DEBRIS_GEORE);
		generateLang("Ruby", GeOreRegistry.RUBY_GEORE);
		generateLang("Sapphire", GeOreRegistry.SAPPHIRE_GEORE);
		generateLang("Topaz", GeOreRegistry.TOPAZ_GEORE);
		generateLang("Zinc", GeOreRegistry.ZINC_GEORE);
		generateLang("Uraninite", GeOreRegistry.URANINITE_GEORE);
		generateLang("Black Quartz", GeOreRegistry.BLACK_QUARTZ_GEORE);
		generateLang("Monazite", GeOreRegistry.MONAZITE_GEORE);
		generateLang("Aluminum", GeOreRegistry.ALUMINUM_GEORE);
		generateLang("Lead", GeOreRegistry.LEAD_GEORE);
		generateLang("Nickel", GeOreRegistry.NICKEL_GEORE);
		generateLang("Osmium", GeOreRegistry.OSMIUM_GEORE);
		generateLang("Platinum", GeOreRegistry.PLATINUM_GEORE);
		generateLang("Silver", GeOreRegistry.SILVER_GEORE);
		generateLang("Tin", GeOreRegistry.TIN_GEORE);
		generateLang("Tungsten", GeOreRegistry.TUNGSTEN_GEORE);
		generateLang("Uranium", GeOreRegistry.URANIUM_GEORE);


		//Config
		add("geore.configuration.title", "Grimoire of Gaia Settings");
		addConfig("Client", "Client", "Client Settings");
		addConfig("spyglassIntensity", "Spyglass Intensity", "The intensity of the spyglass color overlay (0.5 = 50%) [Default: 0.5]");

		addConfig("General", "General", "General settings");
		addConfig("generateCoalGeore", "Generate Coal GeOre", "Generate Coal GeOre [Default: true]");
		addConfig("generateCopperGeore", "Generate Copper GeOre", "Generate Copper GeOre [Default: true]");
		addConfig("generateDiamondGeore", "Generate Diamond GeOre", "Generate Diamond GeOre [Default: true]");
		addConfig("generateEmeraldGeore", "Generate Emerald GeOre", "Generate Emerald GeOre [Default: true]");
		addConfig("generateGoldGeore", "Generate Gold GeOre", "Generate Gold GeOre [Default: true]");
		addConfig("generateIronGeore", "Generate Iron GeOre", "Generate Iron GeOre [Default: true]");
		addConfig("generateLapisGeore", "Generate Lapis GeOre", "Generate Lapis GeOre [Default: true]");
		addConfig("generateQuartzGeore", "Generate Quartz GeOre", "Generate Quartz GeOre [Default: true]");
		addConfig("generateQuartzInNetherGeore", "Generate Quartz In Nether GeOre", "Generate Quartz In Nether GeOre [Default: true]");
		addConfig("generateRedstoneGeore", "Generate Redstone GeOre", "Generate Redstone GeOre [Default: true]");
		addConfig("generateBuddingAncientDebris", "Generate Budding Ancient Debris", "Generate Budding Ancient Debris [Default: true]");
		addConfig("disablePistonPushForBuddingGeOre", "Disable Piston Push For Budding GeOre", "Disable piston push for budding GeOre (Overrides the vanilla behavior of breaking the block upon being pushed) [Default: false]");

		addConfig("ModdedGeneration", "Modded Generation", "Modded Generation Settings");
		addConfig("generateRubyGeore", "Generate Ruby GeOre", "Generate Ruby GeOre [Default: false]");
		addConfig("generateSapphireGeore", "Generate Sapphire GeOre", "Generate Sapphire GeOre [Default: false]");
		addConfig("generateTopazGeore", "Generate Topaz GeOre", "Generate Topaz GeOre [Default: false]");
		addConfig("generateZincGeore", "Generate Zinc GeOre", "Generate Zinc GeOre [Default: false]");
		addConfig("generateUraniniteGeore", "Generate Uraninite GeOre", "Generate Uraninite GeOre [Default: false]");
		addConfig("generateBlackQuartzGeore", "Generate Black Quartz GeOre", "Generate Black Quartz GeOre [Default: false]");
		addConfig("generateMonaziteGeore", "Generate Monazite GeOre", "Generate Monazite GeOre [Default: false]");
		addConfig("generateAluminumGeore", "Generate Aluminum GeOre", "Generate Aluminum GeOre [Default: false]");
		addConfig("generateLeadGeore", "Generate Lead GeOre", "Generate Lead GeOre [Default: false]");
		addConfig("generateNickelGeore", "Generate Nickel GeOre", "Generate Nickel GeOre [Default: false]");
		addConfig("generateOsmiumGeore", "Generate Osmium GeOre", "Generate Osmium GeOre [Default: false]");
		addConfig("generatePlatinumGeore", "Generate Platinum GeOre", "Generate Platinum GeOre [Default: false]");
		addConfig("generateSilverGeore", "Generate Silver GeOre", "Generate Silver GeOre [Default: false]");
		addConfig("generateTinGeore", "Generate Tin GeOre", "Generate Tin GeOre [Default: false]");
		addConfig("generateTungstenGeore", "Generate Tungsten GeOre", "Generate Tungsten GeOre [Default: false]");
		addConfig("generateUraniumGeore", "Generate Uranium GeOre", "Generate Uranium GeOre [Default: false]");
	}

	public void generateLang(String name, GeOreBlockReg blockReg) {
		addBlock(blockReg.getBlock(), "Block Of " + name + " Geore");
		addBlock(blockReg.getBudding(), "Budding " + name + " Geore");
		addBlock(blockReg.getSmallBud(), "Small " + name + " Geore Bud");
		addBlock(blockReg.getMediumBud(), "Medium " + name + " Geore Bud");
		addBlock(blockReg.getLargeBud(), "Large " + name + " Geore Bud");
		addBlock(blockReg.getCluster(), name + " Geore Cluster");
		addBlock(blockReg.getTintedGlass(), name + " Tinted Glass");
		addItem(blockReg.getShard(), name + " Geore Shard");
		addItem(blockReg.getSpyglass(), name + " Geore Spyglass");
	}

	/**
	 * Add the translation for a config entry
	 *
	 * @param path        The path of the config entry
	 * @param name        The name of the config entry
	 * @param description The description of the config entry
	 */
	private void addConfig(String path, String name, String description) {
		this.add("geore.configuration." + path, name);
		this.add("geore.configuration." + path + ".tooltip", description);
	}
}
