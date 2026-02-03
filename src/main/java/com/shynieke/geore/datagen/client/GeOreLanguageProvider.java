package com.shynieke.geore.datagen.client;

import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

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
		generateLang("Allthemodium", GeOreRegistry.ALLTHEMODIUM_GEORE);
		generateLang("Vibranium", GeOreRegistry.VIBRANIUM_GEORE);
		generateLang("Unobtainium", GeOreRegistry.UNOBTAINIUM_GEORE);
	}

	public void generateLang(String name, GeOreBlockReg blockReg) {
		addBlock(blockReg.getBlock(), "Block Of " + name + " GeOre");
		addBlock(blockReg.getBudding(), "Budding " + name + " GeOre");
		addBlock(blockReg.getSmallBud(), "Small " + name + " GeOre Bud");
		addBlock(blockReg.getMediumBud(), "Medium " + name + " GeOre Bud");
		addBlock(blockReg.getLargeBud(), "Large " + name + " GeOre Bud");
		addBlock(blockReg.getCluster(), name + " GeOre Cluster");
		addBlock(blockReg.getTintedGlass(), name + " Tinted Glass");
		addItem(blockReg.getShard(), name + " GeOre Shard");
		addItem(blockReg.getSpyglass(), name + " GeOre Spyglass");
	}
}
