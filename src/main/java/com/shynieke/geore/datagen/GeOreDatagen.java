package com.shynieke.geore.datagen;

import com.shynieke.geore.Reference;
import com.shynieke.geore.datagen.builder.TagSmeltingRecipeBuilder;
import com.shynieke.geore.features.GeOreConfiguredFeatures;
import com.shynieke.geore.features.GeOreFeatures;
import com.shynieke.geore.features.GeOrePlacedFeatures;
import com.shynieke.geore.item.GeoreSpyglassItem;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Cloner;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.shynieke.geore.registry.GeOreRegistry.BLOCKS;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GeOreDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new Loots(packOutput, lookupProvider));
			generator.addProvider(event.includeServer(), new Recipes(packOutput, lookupProvider));
			BlockTagsProvider blockTagsProvider;
			generator.addProvider(event.includeServer(), blockTagsProvider = new GeoreBlockTags(packOutput, lookupProvider, helper));
			generator.addProvider(event.includeServer(), new GeoreItemTags(packOutput, lookupProvider, blockTagsProvider, helper));

			generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, CompletableFuture.supplyAsync(GeOreDatagen::getProvider), Set.of(Reference.MOD_ID)));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new Language(packOutput));
			generator.addProvider(event.includeClient(), new BlockModels(packOutput, helper));
			generator.addProvider(event.includeClient(), new ItemModels(packOutput, helper));
			generator.addProvider(event.includeClient(), new BlockStates(packOutput, helper));
		}
	}

	private static RegistrySetBuilder.PatchedRegistries getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		registryBuilder.add(Registries.CONFIGURED_FEATURE, GeOreConfiguredFeatures::bootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, GeOrePlacedFeatures::bootstrap);
		registryBuilder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, GeOreBiomeModifiers::bootstrap);
		// We need the BIOME registry to be present so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(Registries.BIOME, context -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		Cloner.Factory cloner$factory = new Cloner.Factory();
		net.neoforged.neoforge.registries.DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(p_311524_ -> p_311524_.runWithArguments(cloner$factory::addCodec));
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup(), cloner$factory);
	}

	private static class Loots extends LootTableProvider {
		public Loots(PackOutput packOutput, CompletableFuture<net.minecraft.core.HolderLookup.Provider> lookupProvider) {
			super(packOutput, Set.of(), List.of(new SubProviderEntry(GeOreBlockTables::new, LootContextParamSets.BLOCK)), lookupProvider);
		}

		public static class GeOreBlockTables extends BlockLootSubProvider {

			private final HolderLookup.RegistryLookup<Enchantment> enchantmentLookup;

			protected GeOreBlockTables(HolderLookup.Provider lookupProvider) {
				super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
				this.enchantmentLookup = lookupProvider.lookupOrThrow(Registries.ENCHANTMENT);
			}

			@Override
			protected void generate() {
				for (GeOreBlockReg reg : GeOreRegistry.getGeOres()) {
					addGeOreTables(reg);
				}
			}

			protected void addGeOreTables(GeOreBlockReg blockReg) {
				this.dropSelf(blockReg.getBlock().get());
				this.add(blockReg.getCluster().get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(blockReg.getShard().get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(enchantmentLookup.getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(block, LootItem.lootTableItem(blockReg.getShard().get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
				this.dropWhenSilkTouch(blockReg.getSmallBud().get());
				this.dropWhenSilkTouch(blockReg.getMediumBud().get());
				this.dropWhenSilkTouch(blockReg.getLargeBud().get());
				this.add(blockReg.getBudding().get(), noDrop());
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return BLOCKS.getEntries().stream().map(holder -> (Block) holder.get())::iterator;
			}
		}

		@Override
		protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
			super.validate(writableregistry, validationcontext, problemreporter$collector);
		}
	}

	public static class Recipes extends RecipeProvider {

		public Recipes(PackOutput packOutput, CompletableFuture<net.minecraft.core.HolderLookup.Provider> lookupProvider) {
			super(packOutput, lookupProvider);
		}

		@Override
		protected void buildRecipes(RecipeOutput output, Provider holderLookup) {
			generateRecipes(GeOreRegistry.COAL_GEORE, output);
			ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.TORCH, 2).pattern("X").pattern("#").define('#', Tags.Items.RODS_WOODEN).define('X', GeOreRegistry.COAL_GEORE.getShard().get()).unlockedBy("has_coal_geore_shard", has(GeOreRegistry.COAL_GEORE.getShard().get())).save(output, "geore:torch_from_coal_shard");

			generateRecipe(GeOreRegistry.COPPER_GEORE, 0.7F, Items.COPPER_INGOT, output);
			generateRecipe(GeOreRegistry.DIAMOND_GEORE, 1.0F, Items.DIAMOND, output);
			generateRecipe(GeOreRegistry.EMERALD_GEORE, 1.0F, Items.EMERALD, output);
			generateRecipe(GeOreRegistry.GOLD_GEORE, 1.0F, Items.GOLD_INGOT, output);
			generateRecipe(GeOreRegistry.IRON_GEORE, 0.7F, Items.IRON_INGOT, output);
			generateRecipe(GeOreRegistry.LAPIS_GEORE, 0.2F, Items.LAPIS_LAZULI, output);
			generateRecipe(GeOreRegistry.QUARTZ_GEORE, 0.2F, Items.QUARTZ, output);
			generateRecipe(GeOreRegistry.REDSTONE_GEORE, 0.7F, Items.REDSTONE, output);

			//Mod compat
			generateTagRecipe(GeOreRegistry.RUBY_GEORE, 0.7F, getCommonTag("gems/ruby"), output);
			generateTagRecipe(GeOreRegistry.SAPPHIRE_GEORE, 0.7F, getCommonTag("gems/sapphire"), output);
			generateTagRecipe(GeOreRegistry.TOPAZ_GEORE, 0.7F, getCommonTag("gems/topaz"), output);
			generateTagRecipe(GeOreRegistry.ZINC_GEORE, 0.7F, getCommonTag("ingots/zinc"), output);
			generateTagRecipe(GeOreRegistry.URANINITE_GEORE, 0.7F, getCommonTag("raw_materials/uraninite"), output);
			generateTagRecipe(GeOreRegistry.BLACK_QUARTZ_GEORE, 0.7F, getCommonTag("gems/black_quartz"), output);
			generateTagRecipe(GeOreRegistry.MONAZITE_GEORE, 0.7F, getCommonTag("dusts/monazite"), output);
			generateTagRecipe(GeOreRegistry.ALUMINUM_GEORE, 0.7F, getCommonTag("ingots/aluminum"), output);
			generateTagRecipe(GeOreRegistry.LEAD_GEORE, 0.7F, getCommonTag("ingots/lead"), output);
			generateTagRecipe(GeOreRegistry.NICKEL_GEORE, 0.7F, getCommonTag("ingots/nickel"), output);
			generateTagRecipe(GeOreRegistry.OSMIUM_GEORE, 0.7F, getCommonTag("ingots/osmium"), output);
			generateTagRecipe(GeOreRegistry.PLATINUM_GEORE, 0.7F, getCommonTag("ingots/platinum"), output);
			generateTagRecipe(GeOreRegistry.SILVER_GEORE, 0.7F, getCommonTag("ingots/silver"), output);
			generateTagRecipe(GeOreRegistry.TIN_GEORE, 0.7F, getCommonTag("ingots/tin"), output);
			generateTagRecipe(GeOreRegistry.TUNGSTEN_GEORE, 0.7F, getCommonTag("ingots/tungsten"), output);
			generateTagRecipe(GeOreRegistry.URANIUM_GEORE, 0.7F, getCommonTag("ingots/uranium"), output);
		}

		private void generateRecipe(GeOreBlockReg reg, float xp, ItemLike result, RecipeOutput output) {
			generateRecipes(reg, output);
			smeltToOre(reg, xp, result, output);
		}

		private void generateTagRecipe(GeOreBlockReg reg, float xp, TagKey<Item> result, RecipeOutput output) {
			generateRecipes(reg, output);
			smeltToOre(reg, xp, reg.getName(), result, output);
		}

		private TagKey<Item> getCommonTag(String path) {
			return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", path));
		}

		private void generateRecipes(GeOreBlockReg blockReg, RecipeOutput output) {
			ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockReg.getBlock().get()).define('S', blockReg.getShard().get()).pattern("SS").pattern("SS").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get())).save(output);

			ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, blockReg.getSpyglass().get()).define('#', blockReg.getShard().get()).define('X', Items.COPPER_INGOT).pattern(" # ").pattern(" X ").pattern(" X ").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get())).save(output);
		}

		private void smeltToOre(GeOreBlockReg blockReg, float xp, ItemLike item, RecipeOutput output) {
			SimpleCookingRecipeBuilder.smelting(Ingredient.of(blockReg.getShard().get()), RecipeCategory.MISC, item, xp, 200).group("geore").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get())).save(output, Reference.modLoc(BuiltInRegistries.ITEM.getKey(item.asItem()).getPath() + "_from_smelting_" + blockReg.getShard().getId().getPath()));
			SimpleCookingRecipeBuilder.blasting(Ingredient.of(blockReg.getShard().get()), RecipeCategory.MISC, item, xp, 100).group("geore").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get())).save(output, Reference.modLoc(BuiltInRegistries.ITEM.getKey(item.asItem()).getPath() + "_from_blasting_" + blockReg.getShard().getId().getPath()));
		}

		private void smeltToOre(GeOreBlockReg blockReg, float xp, String type, TagKey<Item> oreTag, RecipeOutput output) {
			RecipeOutput tagOutput = output.withConditions(new NotCondition(new TagEmptyCondition(oreTag.location())));

			Ingredient outputIngredient = Ingredient.of(oreTag);
			TagSmeltingRecipeBuilder.smelting(Ingredient.of(blockReg.getShard().get()), RecipeCategory.MISC, outputIngredient, xp, 200).group("geore").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get())).save(tagOutput, Reference.modLoc(type + "_from_smelting_" + blockReg.getShard().getId().getPath()));
			TagSmeltingRecipeBuilder.blasting(Ingredient.of(blockReg.getShard().get()), RecipeCategory.MISC, outputIngredient, xp, 100).group("geore").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get())).save(tagOutput, Reference.modLoc(type + "_from_blasting_" + blockReg.getShard().getId().getPath()));

		}
	}

	private static class Language extends LanguageProvider {
		public Language(PackOutput packOutput) {
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
			generateLang("Ruby", GeOreRegistry.RUBY_GEORE);
			generateLang("Sapphire", GeOreRegistry.SAPPHIRE_GEORE);
			generateLang("Topaz", GeOreRegistry.TOPAZ_GEORE);
			generateLang("Zinc", GeOreRegistry.ZINC_GEORE);
			generateLang("Uraninite", GeOreRegistry.URANINITE_GEORE);
			generateLang("Black Quartz", GeOreRegistry.BLACK_QUARTZ_GEORE);
			generateLang("Monzanite", GeOreRegistry.MONAZITE_GEORE);
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

	private static class BlockStates extends BlockStateProvider {
		public BlockStates(PackOutput packOutput, ExistingFileHelper helper) {
			super(packOutput, Reference.MOD_ID, helper);
		}

		@Override
		protected void registerStatesAndModels() {
			generateGeoreModels(GeOreRegistry.COAL_GEORE);
			generateGeoreModels(GeOreRegistry.COPPER_GEORE);
			generateGeoreModels(GeOreRegistry.DIAMOND_GEORE);
			generateGeoreModels(GeOreRegistry.EMERALD_GEORE);
			generateGeoreModels(GeOreRegistry.GOLD_GEORE);
			generateGeoreModels(GeOreRegistry.IRON_GEORE);
			generateGeoreModels(GeOreRegistry.LAPIS_GEORE);
			generateGeoreModels(GeOreRegistry.QUARTZ_GEORE);
			generateGeoreModels(GeOreRegistry.REDSTONE_GEORE);
			generateGeoreModels(GeOreRegistry.RUBY_GEORE);
			generateGeoreModels(GeOreRegistry.SAPPHIRE_GEORE);
			generateGeoreModels(GeOreRegistry.TOPAZ_GEORE);
			generateGeoreModels(GeOreRegistry.ZINC_GEORE);
			generateGeoreModels(GeOreRegistry.URANINITE_GEORE);
			generateGeoreModels(GeOreRegistry.BLACK_QUARTZ_GEORE);
			generateGeoreModels(GeOreRegistry.MONAZITE_GEORE);
			generateGeoreModels(GeOreRegistry.ALUMINUM_GEORE);
			generateGeoreModels(GeOreRegistry.LEAD_GEORE);
			generateGeoreModels(GeOreRegistry.NICKEL_GEORE);
			generateGeoreModels(GeOreRegistry.OSMIUM_GEORE);
			generateGeoreModels(GeOreRegistry.PLATINUM_GEORE);
			generateGeoreModels(GeOreRegistry.SILVER_GEORE);
			generateGeoreModels(GeOreRegistry.TIN_GEORE);
			generateGeoreModels(GeOreRegistry.TUNGSTEN_GEORE);
			generateGeoreModels(GeOreRegistry.URANIUM_GEORE);
		}

		protected void generateGeoreModels(GeOreBlockReg blockReg) {
			simpleBlock(blockReg.getBlock().get());
			simpleBlock(blockReg.getBudding().get());
			clusterBlock(blockReg.getCluster().get());
			clusterBlock(blockReg.getLargeBud().get());
			clusterBlock(blockReg.getMediumBud().get());
			clusterBlock(blockReg.getSmallBud().get());
		}

		private void clusterBlock(Block block) {
			ModelFile clusterBlock = models().getExistingFile(modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath()));
			getVariantBuilder(block).partialState().with(BlockStateProperties.FACING, Direction.DOWN).modelForState().modelFile(clusterBlock).rotationX(180).addModel().partialState().with(BlockStateProperties.FACING, Direction.EAST).modelForState().modelFile(clusterBlock).rotationX(90).rotationY(90).addModel().partialState().with(BlockStateProperties.FACING, Direction.NORTH).modelForState().modelFile(clusterBlock).rotationX(90).addModel().partialState().with(BlockStateProperties.FACING, Direction.SOUTH).modelForState().modelFile(clusterBlock).rotationX(90).rotationY(180).addModel().partialState().with(BlockStateProperties.FACING, Direction.UP).modelForState().modelFile(clusterBlock).addModel().partialState().with(BlockStateProperties.FACING, Direction.WEST).modelForState().modelFile(clusterBlock).rotationX(90).rotationY(270).addModel();
		}
	}

	private static class BlockModels extends BlockModelProvider {
		public BlockModels(PackOutput packOutput, ExistingFileHelper helper) {
			super(packOutput, Reference.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			generateGeoreModels(GeOreRegistry.COAL_GEORE);
			generateGeoreModels(GeOreRegistry.COPPER_GEORE);
			generateGeoreModels(GeOreRegistry.DIAMOND_GEORE);
			generateGeoreModels(GeOreRegistry.EMERALD_GEORE);
			generateGeoreModels(GeOreRegistry.GOLD_GEORE);
			generateGeoreModels(GeOreRegistry.IRON_GEORE);
			generateGeoreModels(GeOreRegistry.LAPIS_GEORE);
			generateGeoreModels(GeOreRegistry.QUARTZ_GEORE);
			generateGeoreModels(GeOreRegistry.REDSTONE_GEORE);
			generateGeoreModels(GeOreRegistry.RUBY_GEORE);
			generateGeoreModels(GeOreRegistry.SAPPHIRE_GEORE);
			generateGeoreModels(GeOreRegistry.TOPAZ_GEORE);
			generateGeoreModels(GeOreRegistry.ZINC_GEORE);
			generateGeoreModels(GeOreRegistry.URANINITE_GEORE);
			generateGeoreModels(GeOreRegistry.BLACK_QUARTZ_GEORE);
			generateGeoreModels(GeOreRegistry.MONAZITE_GEORE);
			generateGeoreModels(GeOreRegistry.ALUMINUM_GEORE);
			generateGeoreModels(GeOreRegistry.LEAD_GEORE);
			generateGeoreModels(GeOreRegistry.NICKEL_GEORE);
			generateGeoreModels(GeOreRegistry.OSMIUM_GEORE);
			generateGeoreModels(GeOreRegistry.PLATINUM_GEORE);
			generateGeoreModels(GeOreRegistry.SILVER_GEORE);
			generateGeoreModels(GeOreRegistry.TIN_GEORE);
			generateGeoreModels(GeOreRegistry.TUNGSTEN_GEORE);
			generateGeoreModels(GeOreRegistry.URANIUM_GEORE);
		}

		protected void generateGeoreModels(GeOreBlockReg blockReg) {
			cubeAll(blockReg.getBlock().getId().getPath(), modLoc(BLOCK_FOLDER + "/" + blockReg.getBlock().getId().getPath()));
			cubeAll(blockReg.getBudding().getId().getPath(), modLoc(BLOCK_FOLDER + "/" + blockReg.getBudding().getId().getPath()));

			crossBlock(blockReg.getCluster().get());
			crossBlock(blockReg.getSmallBud().get());
			crossBlock(blockReg.getMediumBud().get());
			crossBlock(blockReg.getLargeBud().get());
		}

		private void crossBlock(Block block) {
			String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
			cross(path, modLoc(BLOCK_FOLDER + "/" + path)).renderType("cutout");
		}
	}

	private static class ItemModels extends ItemModelProvider {
		public ItemModels(PackOutput packOutput, ExistingFileHelper helper) {
			super(packOutput, Reference.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			generateGeoreModels(GeOreRegistry.COAL_GEORE);
			generateGeoreModels(GeOreRegistry.COPPER_GEORE);
			generateGeoreModels(GeOreRegistry.DIAMOND_GEORE);
			generateGeoreModels(GeOreRegistry.EMERALD_GEORE);
			generateGeoreModels(GeOreRegistry.GOLD_GEORE);
			generateGeoreModels(GeOreRegistry.IRON_GEORE);
			generateGeoreModels(GeOreRegistry.LAPIS_GEORE);
			generateGeoreModels(GeOreRegistry.QUARTZ_GEORE);
			generateGeoreModels(GeOreRegistry.REDSTONE_GEORE);
			generateGeoreModels(GeOreRegistry.RUBY_GEORE);
			generateGeoreModels(GeOreRegistry.SAPPHIRE_GEORE);
			generateGeoreModels(GeOreRegistry.TOPAZ_GEORE);
			generateGeoreModels(GeOreRegistry.ZINC_GEORE);
			generateGeoreModels(GeOreRegistry.URANINITE_GEORE);
			generateGeoreModels(GeOreRegistry.BLACK_QUARTZ_GEORE);
			generateGeoreModels(GeOreRegistry.MONAZITE_GEORE);
			generateGeoreModels(GeOreRegistry.ALUMINUM_GEORE);
			generateGeoreModels(GeOreRegistry.LEAD_GEORE);
			generateGeoreModels(GeOreRegistry.NICKEL_GEORE);
			generateGeoreModels(GeOreRegistry.OSMIUM_GEORE);
			generateGeoreModels(GeOreRegistry.PLATINUM_GEORE);
			generateGeoreModels(GeOreRegistry.SILVER_GEORE);
			generateGeoreModels(GeOreRegistry.TIN_GEORE);
			generateGeoreModels(GeOreRegistry.TUNGSTEN_GEORE);
			generateGeoreModels(GeOreRegistry.URANIUM_GEORE);
		}

		protected void generateGeoreModels(GeOreBlockReg blockReg) {
			singleTexture(blockReg.getShard().getId().getPath(), ResourceLocation.withDefaultNamespace("item/generated"), "layer0", Reference.modLoc("item/" + blockReg.getShard().getId().getPath()));

			withExistingParent(blockReg.getBlock().getId().getPath(), Reference.modLoc(BLOCK_FOLDER + "/" + blockReg.getBlock().getId().getPath()));
			withExistingParent(blockReg.getBudding().getId().getPath(), Reference.modLoc(BLOCK_FOLDER + "/" + blockReg.getBudding().getId().getPath()));

			makeCluster(blockReg.getCluster());
			makeSmallBud(blockReg.getSmallBud());
			makeMediumBud(blockReg.getMediumBud());
			makeLargeBud(blockReg.getLargeBud());
			makeSpyglass(blockReg.getSpyglass());
		}

		private void makeSpyglass(DeferredHolder<Item, GeoreSpyglassItem> spyglass) {
			String path = spyglass.getId().getPath();

			ModelFile spyglass_gui = withExistingParent(path + "_gui", mcLoc("spyglass")).texture("layer0", modLoc(ITEM_FOLDER + "/" + path));
			ModelFile spyglass_hand = withExistingParent(path + "_in_hand", mcLoc("spyglass_in_hand")).texture("spyglass", modLoc(ITEM_FOLDER + "/" + path + "_model"));

			withExistingParent(path, "neoforge:item/default").customLoader(SeparateTransformsModelBuilder::begin).base(nested().parent(spyglass_hand)).perspective(ItemDisplayContext.GUI, nested().parent(spyglass_gui)).perspective(ItemDisplayContext.GROUND, nested().parent(spyglass_gui)).perspective(ItemDisplayContext.FIXED, nested().parent(spyglass_gui)).end();
		}

		private void makeCluster(DeferredHolder<Block, AmethystClusterBlock> deferredHolder) {
			String path = deferredHolder.getId().getPath();
			getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated"))).texture("layer0", modLoc(BLOCK_FOLDER + "/" + path)).transforms().transform(ItemDisplayContext.HEAD).translation(0, 14, -5).end();
		}

		private void makeSmallBud(DeferredHolder<Block, AmethystClusterBlock> deferredHolder) {
			String path = deferredHolder.getId().getPath();
			getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud"))).texture("layer0", modLoc(BLOCK_FOLDER + "/" + path)).transforms().transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(0, 6, 0).scale(0.68F, 0.68F, 0.68F).end().transform(ItemDisplayContext.FIXED).translation(0, 7, 0).end();
		}

		private void makeMediumBud(DeferredHolder<Block, AmethystClusterBlock> deferredHolder) {
			String path = deferredHolder.getId().getPath();
			getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud"))).texture("layer0", modLoc(BLOCK_FOLDER + "/" + path)).transforms().transform(ItemDisplayContext.FIXED).translation(0, 6, 0).end();
		}

		private void makeLargeBud(DeferredHolder<Block, AmethystClusterBlock> deferredHolder) {
			String path = deferredHolder.getId().getPath();
			getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud"))).texture("layer0", modLoc(BLOCK_FOLDER + "/" + path)).transforms().transform(ItemDisplayContext.FIXED).translation(0, 4, 0).end();
		}
	}

	public static class GeoreBlockTags extends BlockTagsProvider {
		public GeoreBlockTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
			super(packOutput, lookupProvider, Reference.MOD_ID, existingFileHelper);
		}

		private static TagKey<Block> modTag(String name) {
			return BlockTags.create(Reference.modLoc(name));
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			this.tag(Tags.Blocks.RELOCATION_NOT_SUPPORTED).add(GeOreRegistry.COAL_GEORE.getBudding().get()).add(GeOreRegistry.COPPER_GEORE.getBudding().get()).add(GeOreRegistry.DIAMOND_GEORE.getBudding().get()).add(GeOreRegistry.EMERALD_GEORE.getBudding().get()).add(GeOreRegistry.GOLD_GEORE.getBudding().get()).add(GeOreRegistry.IRON_GEORE.getBudding().get()).add(GeOreRegistry.LAPIS_GEORE.getBudding().get()).add(GeOreRegistry.QUARTZ_GEORE.getBudding().get()).add(GeOreRegistry.REDSTONE_GEORE.getBudding().get()).add(GeOreRegistry.RUBY_GEORE.getBudding().get()).add(GeOreRegistry.SAPPHIRE_GEORE.getBudding().get()).add(GeOreRegistry.TOPAZ_GEORE.getBudding().get());

			for (GeOreBlockReg reg : GeOreRegistry.getGeOres()) {
				this.addMineable(reg);
				this.addCrystalSounds(reg);
				this.addGeore(reg);
			}
		}

		private void addMineable(GeOreBlockReg blockReg) {
			this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(blockReg.getCluster().get()).add(blockReg.getSmallBud().get()).add(blockReg.getMediumBud().get()).add(blockReg.getLargeBud().get()).add(blockReg.getBlock().get()).add(blockReg.getBudding().get());
		}

		private void addCrystalSounds(GeOreBlockReg blockReg) {
			this.tag(BlockTags.CRYSTAL_SOUND_BLOCKS).add(blockReg.getBlock().get()).add(blockReg.getBudding().get());
		}

		private void addGeore(GeOreBlockReg blockReg) {
			TagKey<Block> budsTag = modTag("buds/" + "geore_" + blockReg.getName());
			this.tag(budsTag).add(blockReg.getSmallBud().get(), blockReg.getMediumBud().get(), blockReg.getLargeBud().get());
			this.tag(Tags.Blocks.BUDS).addTag(budsTag);

			TagKey<Block> clustersTag = modTag("clusters/" + "geore_" + blockReg.getName());
			this.tag(clustersTag).add(blockReg.getCluster().get());
			this.tag(Tags.Blocks.CLUSTERS).addTag(clustersTag);
			this.tag(Tags.Blocks.BUDDING_BLOCKS).add(blockReg.getBudding().get());

			TagKey<Block> blockTag = modTag("storage_blocks/" + "geore_" + blockReg.getName());
			this.tag(blockTag).add(blockReg.getBlock().get());
		}
	}

	public static class GeoreItemTags extends ItemTagsProvider {
		public GeoreItemTags(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
			super(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), Reference.MOD_ID, existingFileHelper);
		}

		public static final TagKey<Item> GEORE_BUDDING = modTag("geore_budding");
		public static final TagKey<Item> GEORE_CLUSTERS = modTag("geore_clusters");
		public static final TagKey<Item> GEORE_SMALL_BUDS = modTag("geore_small_buds");
		public static final TagKey<Item> GEORE_MEDIUM_BUDS = modTag("geore_medium_buds");
		public static final TagKey<Item> GEORE_LARGE_BUDS = modTag("geore_large_buds");
		public static final TagKey<Item> GEORE_SHARDS = modTag("geore_shards");
		public static final TagKey<Item> GEORE_BLOCKS = modTag("geore_blocks");

		@SuppressWarnings("unchecked")
		@Override
		protected void addTags(HolderLookup.Provider provider) {
			this.tag(ItemTags.COALS).add(GeOreRegistry.COAL_GEORE.getShard().get());

			for (GeOreBlockReg reg : GeOreRegistry.getGeOres()) {
				this.addGeore(reg);
				this.addStorage(reg);
			}

			this.tag(Tags.Items.BUDDING_BLOCKS).addTag(GEORE_BUDDING);
			this.tag(Tags.Items.BUDS).addTags(GEORE_SMALL_BUDS, GEORE_MEDIUM_BUDS, GEORE_LARGE_BUDS);
			this.tag(Tags.Items.CLUSTERS).addTag(GEORE_CLUSTERS);
		}

		private void addStorage(GeOreBlockReg blockReg) {
			TagKey<Item> itemTag = modTag("storage_blocks/" + "geore_" + blockReg.getName());
			this.tag(itemTag).add(blockReg.getBlock().get().asItem());
		}

		private void addGeore(GeOreBlockReg blockReg) {
			this.tag(GEORE_BUDDING).add(blockReg.getBudding().get().asItem());

			TagKey<Item> smallBudsTag = modTag("geore_small_buds/" + blockReg.getName());
			this.tag(GEORE_SMALL_BUDS).addTag(smallBudsTag);
			this.tag(smallBudsTag).add(blockReg.getCluster().get().asItem());

			TagKey<Item> mediumBudsTag = modTag("geore_medium_buds/" + blockReg.getName());
			this.tag(GEORE_MEDIUM_BUDS).addTag(mediumBudsTag);
			this.tag(mediumBudsTag).add(blockReg.getSmallBud().get().asItem());

			TagKey<Item> largeBudsTag = modTag("geore_large_buds/" + blockReg.getName());
			this.tag(GEORE_LARGE_BUDS).addTag(largeBudsTag);
			this.tag(largeBudsTag).add(blockReg.getCluster().get().asItem());

			TagKey<Item> clusterTag = modTag("geore_clusters/" + blockReg.getName());
			this.tag(GEORE_CLUSTERS).addTag(clusterTag);
			this.tag(clusterTag).add(blockReg.getCluster().get().asItem());

			TagKey<Item> shardTag = modTag("geore_shards/" + blockReg.getName());
			this.tag(GEORE_SHARDS).addTag(shardTag);
			this.tag(shardTag).add(blockReg.getShard().get());

			TagKey<Item> blockTag = modTag("geore_blocks/" + blockReg.getName());
			this.tag(GEORE_BLOCKS).addTag(blockTag);
			this.tag(blockTag).add(blockReg.getBlock().get().asItem());
		}

		private static TagKey<Item> modTag(String name) {
			return ItemTags.create(Reference.modLoc(name));
		}
	}
}
