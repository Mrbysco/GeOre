package com.shynieke.geore.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.shynieke.geore.Reference;
import com.shynieke.geore.features.GeOreFeatures;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.shynieke.geore.registry.GeOreRegistry.BLOCKS;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeOreDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new Loots(generator));
			generator.addProvider(event.includeServer(), new Recipes(generator));
			BlockTagsProvider provider;
			generator.addProvider(event.includeServer(), provider = new GeoreBlockTags(generator, helper));
			generator.addProvider(event.includeServer(), new GeoreItemTags(generator, provider, helper));


			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, helper, Reference.MOD_ID, ops, Registry.PLACED_FEATURE_REGISTRY, getConfiguredFeatures(ops)));

			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, helper, Reference.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS, getBiomeModifiers(ops)));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new Language(generator));
			generator.addProvider(event.includeClient(), new BlockModels(generator, helper));
			generator.addProvider(event.includeClient(), new ItemModels(generator, helper));
			generator.addProvider(event.includeClient(), new BlockStates(generator, helper));
		}
	}

	public static Map<ResourceLocation, PlacedFeature> getConfiguredFeatures(RegistryOps<JsonElement> ops) {
		Map<ResourceLocation, PlacedFeature> map = Maps.newHashMap();

		GeOreFeatures.COAL_GEORE.fillPlacedFeatureMap(ops, map, 60, 6, 30);
		GeOreFeatures.COPPER_GEORE.fillPlacedFeatureMap(ops, map, 90, 6, 30);
		GeOreFeatures.DIAMOND_GEORE.fillPlacedFeatureMap(ops, map, 330, 6, 30);
		GeOreFeatures.EMERALD_GEORE.fillPlacedFeatureMap(ops, map, 420, 6, 30);
		GeOreFeatures.GOLD_GEORE.fillPlacedFeatureMap(ops, map, 180, 6, 30);
		GeOreFeatures.IRON_GEORE.fillPlacedFeatureMap(ops, map, 120, 6, 30);
		GeOreFeatures.LAPIS_GEORE.fillPlacedFeatureMap(ops, map, 210, 6, 30);
		GeOreFeatures.QUARTZ_GEORE.fillPlacedFeatureMap(ops, map, 150, 6, 30);
		GeOreFeatures.REDSTONE_GEORE.fillPlacedFeatureMap(ops, map, 240, 6, 30);
		GeOreFeatures.RUBY_GEORE.fillPlacedFeatureMap(ops, map, 240, 6, 30);
		GeOreFeatures.SAPPHIRE_GEORE.fillPlacedFeatureMap(ops, map, 240, 6, 30);
		GeOreFeatures.TOPAZ_GEORE.fillPlacedFeatureMap(ops, map, 240, 6, 30);
		GeOreFeatures.ZINC_GEORE.fillPlacedFeatureMap(ops, map, 140, 6, 30);

		return map;
	}

	public static Map<ResourceLocation, BiomeModifier> getBiomeModifiers(RegistryOps<JsonElement> ops) {
		Map<ResourceLocation, BiomeModifier> map = Maps.newHashMap();

		GeOreFeatures.COAL_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "coal");
		GeOreFeatures.COPPER_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "copper");
		GeOreFeatures.DIAMOND_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "diamond");
		GeOreFeatures.EMERALD_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "emerald");
		GeOreFeatures.GOLD_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "gold");
		GeOreFeatures.IRON_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "iron");
		GeOreFeatures.LAPIS_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "lapis");
		GeOreFeatures.QUARTZ_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "quartz");
		GeOreFeatures.QUARTZ_GEORE.fillModifierMap(ops, map, BiomeTags.IS_NETHER, "quartz_nether");
		GeOreFeatures.REDSTONE_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "redstone");
		GeOreFeatures.RUBY_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "ruby");
		GeOreFeatures.SAPPHIRE_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "sapphire");
		GeOreFeatures.TOPAZ_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "topaz");
		GeOreFeatures.ZINC_GEORE.fillModifierMap(ops, map, BiomeTags.IS_OVERWORLD, "zinc");

		return map;
	}

	private static class Loots extends LootTableProvider {
		public Loots(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
			return ImmutableList.of(
					Pair.of(GeOreBlockTables::new, LootContextParamSets.BLOCK)
			);
		}

		public static class GeOreBlockTables extends BlockLoot {

			@Override
			protected void addTables() {
				addGeOreTables(GeOreRegistry.COAL_GEORE);
				addGeOreTables(GeOreRegistry.COPPER_GEORE);
				addGeOreTables(GeOreRegistry.DIAMOND_GEORE);
				addGeOreTables(GeOreRegistry.EMERALD_GEORE);
				addGeOreTables(GeOreRegistry.GOLD_GEORE);
				addGeOreTables(GeOreRegistry.IRON_GEORE);
				addGeOreTables(GeOreRegistry.LAPIS_GEORE);
				addGeOreTables(GeOreRegistry.QUARTZ_GEORE);
				addGeOreTables(GeOreRegistry.REDSTONE_GEORE);
				addGeOreTables(GeOreRegistry.RUBY_GEORE);
				addGeOreTables(GeOreRegistry.SAPPHIRE_GEORE);
				addGeOreTables(GeOreRegistry.TOPAZ_GEORE);
				addGeOreTables(GeOreRegistry.ZINC_GEORE);
			}

			protected void addGeOreTables(GeOreBlockReg blockReg) {
				this.dropSelf(blockReg.getBlock().get());
				this.add(blockReg.getCluster().get(), (block) ->
						createSilkTouchDispatchTable(block, LootItem.lootTableItem(blockReg.getShard().get())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)))
								.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
								.when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES)))
								.otherwise(applyExplosionDecay(block, LootItem.lootTableItem(blockReg.getShard().get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
				this.dropWhenSilkTouch(blockReg.getSmallBud().get());
				this.dropWhenSilkTouch(blockReg.getMediumBud().get());
				this.dropWhenSilkTouch(blockReg.getLargeBud().get());
				this.add(blockReg.getBudding().get(), noDrop());
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return (Iterable<Block>) BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {
			map.forEach((name, table) -> LootTables.validate(validationContext, name, table));
		}
	}

	public static class Recipes extends RecipeProvider {

		public Recipes(DataGenerator generator) {
			super(generator);
		}

		@Override
		protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeConsumer) {
			generateRecipes(GeOreRegistry.COAL_GEORE, recipeConsumer);

			ShapedRecipeBuilder.shaped(Blocks.TORCH, 2)
					.pattern("X").pattern("#")
					.define('#', Tags.Items.RODS_WOODEN).define('X', GeOreRegistry.COAL_GEORE.getShard().get())
					.unlockedBy("has_coal_geore_shard",
							has(GeOreRegistry.COAL_GEORE.getShard().get())).save(recipeConsumer, "geore:torch_from_coal_shard");

			generateRecipes(GeOreRegistry.COPPER_GEORE, recipeConsumer);
			smeltToOre(GeOreRegistry.COPPER_GEORE, 0.7F, Items.COPPER_INGOT, recipeConsumer);

			generateRecipes(GeOreRegistry.DIAMOND_GEORE, recipeConsumer);
			smeltToOre(GeOreRegistry.DIAMOND_GEORE, 1.0F, Items.DIAMOND, recipeConsumer);

			generateRecipes(GeOreRegistry.EMERALD_GEORE, recipeConsumer);
			smeltToOre(GeOreRegistry.EMERALD_GEORE, 1.0F, Items.EMERALD, recipeConsumer);

			generateRecipes(GeOreRegistry.GOLD_GEORE, recipeConsumer);
			smeltToOre(GeOreRegistry.GOLD_GEORE, 1.0F, Items.GOLD_INGOT, recipeConsumer);

			generateRecipes(GeOreRegistry.IRON_GEORE, recipeConsumer);
			smeltToOre(GeOreRegistry.IRON_GEORE, 0.7F, Items.IRON_INGOT, recipeConsumer);

			generateRecipes(GeOreRegistry.LAPIS_GEORE, recipeConsumer);
			smeltToOre(GeOreRegistry.LAPIS_GEORE, 0.2F, Items.LAPIS_LAZULI, recipeConsumer);

			generateRecipes(GeOreRegistry.QUARTZ_GEORE, recipeConsumer);
			smeltToOre(GeOreRegistry.QUARTZ_GEORE, 0.2F, Items.QUARTZ, recipeConsumer);

			generateRecipes(GeOreRegistry.REDSTONE_GEORE, recipeConsumer);
			smeltToOre(GeOreRegistry.REDSTONE_GEORE, 0.7F, Items.REDSTONE, recipeConsumer);

			//Mod compat
			String gemsID = "gemsandcrystals";
			Item rubyItem = getModItem(new ResourceLocation(gemsID, "ruby"));
			generateRecipes(GeOreRegistry.RUBY_GEORE, recipeConsumer);
			if (rubyItem != null) {
				optionalSmeltToOre(GeOreRegistry.RUBY_GEORE, 0.7F, rubyItem, gemsID, recipeConsumer);
			}

			Item sapphireItem = getModItem(new ResourceLocation(gemsID, "sapphire"));
			generateRecipes(GeOreRegistry.SAPPHIRE_GEORE, recipeConsumer);
			if (sapphireItem != null) {
				optionalSmeltToOre(GeOreRegistry.SAPPHIRE_GEORE, 0.7F, sapphireItem, gemsID, recipeConsumer);
			}

			Item topazItem = getModItem(new ResourceLocation(gemsID, "topaz"));
			generateRecipes(GeOreRegistry.TOPAZ_GEORE, recipeConsumer);
			if (topazItem != null) {
				optionalSmeltToOre(GeOreRegistry.TOPAZ_GEORE, 0.7F, topazItem, gemsID, recipeConsumer);
			}

			generateRecipes(GeOreRegistry.ZINC_GEORE, recipeConsumer);
		}

		public Item getModItem(ResourceLocation itemLocation) {
			for (Item item : ForgeRegistries.ITEMS) {
				if (ForgeRegistries.ITEMS.getKey(item).equals(itemLocation)) {
					return item;
				}
			}
			return null;
		}

		private void generateRecipes(GeOreBlockReg blockReg, Consumer<FinishedRecipe> recipeConsumer) {
			ShapedRecipeBuilder.shaped(blockReg.getBlock().get())
					.define('S', blockReg.getShard().get()).pattern("SS").pattern("SS").unlockedBy("has_" + blockReg.getName() + "geore_shard",
							has(blockReg.getShard().get())).save(recipeConsumer);

			ShapedRecipeBuilder.shaped(blockReg.getSpyglass().get())
					.define('#', blockReg.getShard().get())
					.define('X', Items.COPPER_INGOT)
					.pattern(" # ").pattern(" X ").pattern(" X ").unlockedBy("has_" + blockReg.getName() + "geore_shard",
							has(blockReg.getShard().get())).save(recipeConsumer);
		}

		private void smeltToOre(GeOreBlockReg blockReg, float xp, Item item, Consumer<FinishedRecipe> recipeConsumer) {
			SimpleCookingRecipeBuilder.smelting(Ingredient.of(blockReg.getShard().get()), item, xp, 200)
					.group("geore").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
					.save(recipeConsumer,
							new ResourceLocation(Reference.MOD_ID, ForgeRegistries.ITEMS.getKey(item).getPath() + "_from_smelting_" + blockReg.getShard().getId().getPath()));
			SimpleCookingRecipeBuilder.blasting(Ingredient.of(blockReg.getShard().get()), item, xp, 100)
					.group("geore").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
					.save(recipeConsumer,
							new ResourceLocation(Reference.MOD_ID, ForgeRegistries.ITEMS.getKey(item).getPath() + "_from_blasting_" + blockReg.getShard().getId().getPath()));
		}

		private void optionalSmeltToOre(GeOreBlockReg blockReg, float xp, Item item, String modid, Consumer<FinishedRecipe> recipeConsumer) {
			ConditionalRecipe.builder()
					.addCondition(new ModLoadedCondition(modid))
					.addRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(blockReg.getShard().get()), item, xp, 200)
							.group("geore").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
							::save
					)
					.build(recipeConsumer,
							new ResourceLocation(Reference.MOD_ID, ForgeRegistries.ITEMS.getKey(item).getPath() + "_from_smelting_" + blockReg.getShard().getId().getPath()));

			ConditionalRecipe.builder()
					.addCondition(new ModLoadedCondition(modid))
					.addRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(blockReg.getShard().get()), item, xp, 100)
							.group("geore").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
							::save
					)
					.build(recipeConsumer,
							new ResourceLocation(Reference.MOD_ID, ForgeRegistries.ITEMS.getKey(item).getPath() + "_from_blasting_" + blockReg.getShard().getId().getPath()));
		}
	}

	private static class Language extends LanguageProvider {
		public Language(DataGenerator gen) {
			super(gen, Reference.MOD_ID, "en_us");
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
	}

	private static class BlockStates extends BlockStateProvider {
		public BlockStates(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Reference.MOD_ID, helper);
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
			ModelFile clusterBlock = models().getExistingFile(modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
			getVariantBuilder(block)
					.partialState().with(BlockStateProperties.FACING, Direction.DOWN)
					.modelForState().modelFile(clusterBlock).rotationX(180).addModel()
					.partialState().with(BlockStateProperties.FACING, Direction.EAST)
					.modelForState().modelFile(clusterBlock).rotationX(90).rotationY(90).addModel()
					.partialState().with(BlockStateProperties.FACING, Direction.NORTH)
					.modelForState().modelFile(clusterBlock).rotationX(90).addModel()
					.partialState().with(BlockStateProperties.FACING, Direction.SOUTH)
					.modelForState().modelFile(clusterBlock).rotationX(90).rotationY(180).addModel()
					.partialState().with(BlockStateProperties.FACING, Direction.UP)
					.modelForState().modelFile(clusterBlock).addModel()
					.partialState().with(BlockStateProperties.FACING, Direction.WEST)
					.modelForState().modelFile(clusterBlock).rotationX(90).rotationY(270).addModel();
		}
	}

	private static class BlockModels extends BlockModelProvider {
		public BlockModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Reference.MOD_ID, helper);
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
			String path = ForgeRegistries.BLOCKS.getKey(block).getPath();
			cross(path, modLoc(BLOCK_FOLDER + "/" + path)).renderType("cutout");
		}
	}

	private static class ItemModels extends ItemModelProvider {
		public ItemModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Reference.MOD_ID, helper);
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
		}

		protected void generateGeoreModels(GeOreBlockReg blockReg) {
			singleTexture(blockReg.getShard().getId().getPath(), new ResourceLocation("item/generated"),
					"layer0", new ResourceLocation(Reference.MOD_ID, "item/" + blockReg.getShard().getId().getPath()));

			withExistingParent(blockReg.getBlock().getId().getPath(),
					new ResourceLocation(Reference.MOD_ID, BLOCK_FOLDER + "/" + blockReg.getBlock().getId().getPath()));
			withExistingParent(blockReg.getBudding().getId().getPath(),
					new ResourceLocation(Reference.MOD_ID, BLOCK_FOLDER + "/" + blockReg.getBudding().getId().getPath()));

			makeCluster(blockReg.getCluster().get());
			makeSmallBud(blockReg.getSmallBud().get());
			makeMediumBud(blockReg.getMediumBud().get());
			makeLargeBud(blockReg.getLargeBud().get());
			makeSpyglass(blockReg.getSpyglass());
		}

		private void makeSpyglass(RegistryObject<Item> spyglass) {
			String path = spyglass.getId().getPath();

			ModelFile spyglass_gui = withExistingParent(path + "_gui", mcLoc("spyglass"))
					.texture("layer0", modLoc(ITEM_FOLDER + "/" + path));
			ModelFile spyglass_hand = withExistingParent(path + "_in_hand", mcLoc("spyglass_in_hand"))
					.texture("spyglass", modLoc(ITEM_FOLDER + "/" + path + "_model"));

			withExistingParent(path, "forge:item/default")
					.customLoader(SeparateTransformsModelBuilder::begin)
					.base(nested().parent(spyglass_hand))
					.perspective(TransformType.GUI, nested().parent(spyglass_gui))
					.perspective(TransformType.GROUND, nested().parent(spyglass_gui))
					.perspective(TransformType.FIXED, nested().parent(spyglass_gui))
					.end();
		}

		private void makeCluster(Block block) {
			String path = ForgeRegistries.BLOCKS.getKey(block).getPath();
			getBuilder(path)
					.parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
					.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
					.transforms().transform(TransformType.HEAD)
					.translation(0, 14, -5).end();
		}

		private void makeSmallBud(Block block) {
			String path = ForgeRegistries.BLOCKS.getKey(block).getPath();
			getBuilder(path)
					.parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
					.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
					.transforms().transform(TransformType.THIRD_PERSON_RIGHT_HAND)
					.rotation(0, -90, 25)
					.translation(0, 6, 0)
					.scale(0.68F, 0.68F, 0.68F).end()
					.transform(TransformType.FIXED)
					.translation(0, 7, 0).end();
		}

		private void makeMediumBud(Block block) {
			String path = ForgeRegistries.BLOCKS.getKey(block).getPath();
			getBuilder(path)
					.parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
					.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
					.transforms().transform(TransformType.FIXED)
					.translation(0, 6, 0).end();
		}

		private void makeLargeBud(Block block) {
			String path = ForgeRegistries.BLOCKS.getKey(block).getPath();
			getBuilder(path)
					.parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
					.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
					.transforms().transform(TransformType.FIXED)
					.translation(0, 4, 0).end();
		}
	}

	public static class GeoreBlockTags extends BlockTagsProvider {
		public GeoreBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, Reference.MOD_ID, existingFileHelper);
		}

		public static final TagKey<Block> RELOCATION_NOT_SUPPORTED = forgeTag("relocation_not_supported");
		public static final TagKey<Block> NON_MOVABLE = modTag("create", "non_movable");

		public static final TagKey<Block> BUDDING = forgeTag("budding");
		public static final TagKey<Block> BUDS = forgeTag("buds");
		public static final TagKey<Block> CLUSTERS = forgeTag("clusters");

		private static TagKey<Block> forgeTag(String name) {
			return BlockTags.create(new ResourceLocation("forge", name));
		}

		private static TagKey<Block> modTag(String modid, String name) {
			return BlockTags.create(new ResourceLocation(modid, name));
		}

		@Override
		protected void addTags() {
			this.tag(RELOCATION_NOT_SUPPORTED)
					.add(GeOreRegistry.COAL_GEORE.getBudding().get())
					.add(GeOreRegistry.COPPER_GEORE.getBudding().get())
					.add(GeOreRegistry.DIAMOND_GEORE.getBudding().get())
					.add(GeOreRegistry.EMERALD_GEORE.getBudding().get())
					.add(GeOreRegistry.GOLD_GEORE.getBudding().get())
					.add(GeOreRegistry.IRON_GEORE.getBudding().get())
					.add(GeOreRegistry.LAPIS_GEORE.getBudding().get())
					.add(GeOreRegistry.QUARTZ_GEORE.getBudding().get())
					.add(GeOreRegistry.REDSTONE_GEORE.getBudding().get())
					.add(GeOreRegistry.RUBY_GEORE.getBudding().get())
					.add(GeOreRegistry.SAPPHIRE_GEORE.getBudding().get())
					.add(GeOreRegistry.TOPAZ_GEORE.getBudding().get());
			this.tag(NON_MOVABLE)
					.add(GeOreRegistry.COAL_GEORE.getBudding().get())
					.add(GeOreRegistry.COPPER_GEORE.getBudding().get())
					.add(GeOreRegistry.DIAMOND_GEORE.getBudding().get())
					.add(GeOreRegistry.EMERALD_GEORE.getBudding().get())
					.add(GeOreRegistry.GOLD_GEORE.getBudding().get())
					.add(GeOreRegistry.IRON_GEORE.getBudding().get())
					.add(GeOreRegistry.LAPIS_GEORE.getBudding().get())
					.add(GeOreRegistry.QUARTZ_GEORE.getBudding().get())
					.add(GeOreRegistry.REDSTONE_GEORE.getBudding().get())
					.add(GeOreRegistry.RUBY_GEORE.getBudding().get())
					.add(GeOreRegistry.SAPPHIRE_GEORE.getBudding().get())
					.add(GeOreRegistry.TOPAZ_GEORE.getBudding().get());

			this.addMineable(GeOreRegistry.COAL_GEORE);
			this.addMineable(GeOreRegistry.COPPER_GEORE);
			this.addMineable(GeOreRegistry.DIAMOND_GEORE);
			this.addMineable(GeOreRegistry.EMERALD_GEORE);
			this.addMineable(GeOreRegistry.GOLD_GEORE);
			this.addMineable(GeOreRegistry.IRON_GEORE);
			this.addMineable(GeOreRegistry.LAPIS_GEORE);
			this.addMineable(GeOreRegistry.QUARTZ_GEORE);
			this.addMineable(GeOreRegistry.REDSTONE_GEORE);
			this.addMineable(GeOreRegistry.RUBY_GEORE);
			this.addMineable(GeOreRegistry.SAPPHIRE_GEORE);
			this.addMineable(GeOreRegistry.TOPAZ_GEORE);
			this.addMineable(GeOreRegistry.ZINC_GEORE);

			this.addCrystalSounds(GeOreRegistry.COAL_GEORE);
			this.addCrystalSounds(GeOreRegistry.COPPER_GEORE);
			this.addCrystalSounds(GeOreRegistry.DIAMOND_GEORE);
			this.addCrystalSounds(GeOreRegistry.EMERALD_GEORE);
			this.addCrystalSounds(GeOreRegistry.GOLD_GEORE);
			this.addCrystalSounds(GeOreRegistry.IRON_GEORE);
			this.addCrystalSounds(GeOreRegistry.LAPIS_GEORE);
			this.addCrystalSounds(GeOreRegistry.QUARTZ_GEORE);
			this.addCrystalSounds(GeOreRegistry.REDSTONE_GEORE);
			this.addCrystalSounds(GeOreRegistry.RUBY_GEORE);
			this.addCrystalSounds(GeOreRegistry.SAPPHIRE_GEORE);
			this.addCrystalSounds(GeOreRegistry.TOPAZ_GEORE);
			this.addCrystalSounds(GeOreRegistry.ZINC_GEORE);

			this.addGeore(GeOreRegistry.COAL_GEORE);
			this.addGeore(GeOreRegistry.COPPER_GEORE);
			this.addGeore(GeOreRegistry.DIAMOND_GEORE);
			this.addGeore(GeOreRegistry.EMERALD_GEORE);
			this.addGeore(GeOreRegistry.GOLD_GEORE);
			this.addGeore(GeOreRegistry.IRON_GEORE);
			this.addGeore(GeOreRegistry.LAPIS_GEORE);
			this.addGeore(GeOreRegistry.QUARTZ_GEORE);
			this.addGeore(GeOreRegistry.REDSTONE_GEORE);
			this.addGeore(GeOreRegistry.RUBY_GEORE);
			this.addGeore(GeOreRegistry.SAPPHIRE_GEORE);
			this.addGeore(GeOreRegistry.TOPAZ_GEORE);
			this.addGeore(GeOreRegistry.ZINC_GEORE);
		}

		private void addMineable(GeOreBlockReg blockReg) {
			this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
					.add(blockReg.getCluster().get())
					.add(blockReg.getSmallBud().get())
					.add(blockReg.getMediumBud().get())
					.add(blockReg.getLargeBud().get())
					.add(blockReg.getBlock().get())
					.add(blockReg.getBudding().get());
		}

		private void addCrystalSounds(GeOreBlockReg blockReg) {
			this.tag(BlockTags.CRYSTAL_SOUND_BLOCKS)
					.add(blockReg.getBlock().get())
					.add(blockReg.getBudding().get());
		}

		private void addGeore(GeOreBlockReg blockReg) {
			TagKey<Block> budsTag = forgeTag("buds/" + "geore_" + blockReg.getName());
			this.tag(budsTag).add(blockReg.getSmallBud().get(), blockReg.getMediumBud().get(), blockReg.getLargeBud().get());
			this.tag(BUDS).addTag(budsTag);

			TagKey<Block> clustersTag = forgeTag("clusters/" + "geore_" + blockReg.getName());
			this.tag(clustersTag).add(blockReg.getCluster().get());
			this.tag(CLUSTERS).addTag(clustersTag);
			this.tag(BUDDING).add(blockReg.getBudding().get());

			TagKey<Block> blockTag = modTag(Reference.MOD_ID, "storage_blocks/" + "geore_" + blockReg.getName());
			this.tag(blockTag).add(blockReg.getBlock().get());
		}
	}

	public static class GeoreItemTags extends ItemTagsProvider {
		public GeoreItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
			super(dataGenerator, blockTagsProvider, Reference.MOD_ID, existingFileHelper);
		}

		public static final TagKey<Item> GEORE_CLUSTERS = forgeTag("geore_clusters");
		public static final TagKey<Item> GEORE_SMALL_BUDS = forgeTag("geore_small_buds");
		public static final TagKey<Item> GEORE_MEDIUM_BUDS = forgeTag("geore_medium_buds");
		public static final TagKey<Item> GEORE_LARGE_BUDS = forgeTag("geore_large_buds");
		public static final TagKey<Item> GEORE_SHARDS = forgeTag("geore_shards");
		public static final TagKey<Item> GEORE_BLOCKS = forgeTag("geore_blocks");

		@Override
		protected void addTags() {
			this.addGeore(GeOreRegistry.COAL_GEORE);
			this.tag(ItemTags.COALS).add(GeOreRegistry.COAL_GEORE.getShard().get());
			this.addGeore(GeOreRegistry.COPPER_GEORE);
			this.addGeore(GeOreRegistry.DIAMOND_GEORE);
			this.addGeore(GeOreRegistry.EMERALD_GEORE);
			this.addGeore(GeOreRegistry.GOLD_GEORE);
			this.addGeore(GeOreRegistry.IRON_GEORE);
			this.addGeore(GeOreRegistry.LAPIS_GEORE);
			this.addGeore(GeOreRegistry.QUARTZ_GEORE);
			this.addGeore(GeOreRegistry.REDSTONE_GEORE);
			this.addGeore(GeOreRegistry.RUBY_GEORE);
			this.addGeore(GeOreRegistry.SAPPHIRE_GEORE);
			this.addGeore(GeOreRegistry.TOPAZ_GEORE);
			this.addGeore(GeOreRegistry.ZINC_GEORE);

			this.addStorage(GeOreRegistry.COAL_GEORE);
			this.addStorage(GeOreRegistry.COPPER_GEORE);
			this.addStorage(GeOreRegistry.DIAMOND_GEORE);
			this.addStorage(GeOreRegistry.EMERALD_GEORE);
			this.addStorage(GeOreRegistry.GOLD_GEORE);
			this.addStorage(GeOreRegistry.IRON_GEORE);
			this.addStorage(GeOreRegistry.LAPIS_GEORE);
			this.addStorage(GeOreRegistry.QUARTZ_GEORE);
			this.addStorage(GeOreRegistry.REDSTONE_GEORE);
			this.addStorage(GeOreRegistry.RUBY_GEORE);
			this.addStorage(GeOreRegistry.SAPPHIRE_GEORE);
			this.addStorage(GeOreRegistry.TOPAZ_GEORE);
			this.addStorage(GeOreRegistry.ZINC_GEORE);
		}

		private void addStorage(GeOreBlockReg blockReg) {
			TagKey<Item> itemTag = modTag(Reference.MOD_ID, "storage_blocks/" + "geore_" + blockReg.getName());
			this.tag(itemTag).add(blockReg.getBlock().get().asItem());
		}

		private void addGeore(GeOreBlockReg blockReg) {
			TagKey<Item> smallBudsTag = forgeTag("geore_small_buds/" + blockReg.getName());
			this.tag(GEORE_SMALL_BUDS).addTag(smallBudsTag);
			this.tag(smallBudsTag).add(blockReg.getCluster().get().asItem());

			TagKey<Item> mediumBudsTag = forgeTag("geore_medium_buds/" + blockReg.getName());
			this.tag(GEORE_MEDIUM_BUDS).addTag(mediumBudsTag);
			this.tag(mediumBudsTag).add(blockReg.getSmallBud().get().asItem());

			TagKey<Item> largeBudsTag = forgeTag("geore_large_buds/" + blockReg.getName());
			this.tag(GEORE_LARGE_BUDS).addTag(largeBudsTag);
			this.tag(largeBudsTag).add(blockReg.getCluster().get().asItem());

			TagKey<Item> clusterTag = forgeTag("geore_clusters/" + blockReg.getName());
			this.tag(GEORE_CLUSTERS).addTag(clusterTag);
			this.tag(clusterTag).add(blockReg.getCluster().get().asItem());

			TagKey<Item> shardTag = forgeTag("geore_shards/" + blockReg.getName());
			this.tag(GEORE_SHARDS).addTag(shardTag);
			this.tag(shardTag).add(blockReg.getShard().get());

			TagKey<Item> blockTag = forgeTag("geore_blocks/" + blockReg.getName());
			this.tag(GEORE_BLOCKS).addTag(blockTag);
			this.tag(blockTag).add(blockReg.getBlock().get().asItem());
		}

		private static TagKey<Item> forgeTag(String name) {
			return ItemTags.create(new ResourceLocation("forge", name));
		}

		private static TagKey<Item> modTag(String modid, String name) {
			return ItemTags.create(new ResourceLocation(modid, name));
		}
	}
}
