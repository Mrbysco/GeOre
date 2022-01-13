package com.shynieke.geore.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.shynieke.geore.Reference;
import com.shynieke.geore.registry.GeOreBlockReg;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparatePerspectiveModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.shynieke.geore.registry.GeOreRegistry.BLOCKS;
import static com.shynieke.geore.registry.GeOreRegistry.COAL_GEORE;
import static com.shynieke.geore.registry.GeOreRegistry.COPPER_GEORE;
import static com.shynieke.geore.registry.GeOreRegistry.DIAMOND_GEORE;
import static com.shynieke.geore.registry.GeOreRegistry.EMERALD_GEORE;
import static com.shynieke.geore.registry.GeOreRegistry.GOLD_GEORE;
import static com.shynieke.geore.registry.GeOreRegistry.IRON_GEORE;
import static com.shynieke.geore.registry.GeOreRegistry.LAPIS_GEORE;
import static com.shynieke.geore.registry.GeOreRegistry.QUARTZ_GEORE;
import static com.shynieke.geore.registry.GeOreRegistry.REDSTONE_GEORE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeOreDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(new Loots(generator));
			generator.addProvider(new Recipes(generator));
			generator.addProvider(new GeoreBlockTags(generator, helper));
		}
		if (event.includeClient()) {
            generator.addProvider(new Language(generator));
            generator.addProvider(new BlockModels(generator, helper));
            generator.addProvider(new ItemModels(generator, helper));
			generator.addProvider(new BlockStates(generator, helper));
		}
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
				addGeOreTables(COAL_GEORE);
				addGeOreTables(COPPER_GEORE);
				addGeOreTables(DIAMOND_GEORE);
				addGeOreTables(EMERALD_GEORE);
				addGeOreTables(GOLD_GEORE);
				addGeOreTables(IRON_GEORE);
				addGeOreTables(LAPIS_GEORE);
				addGeOreTables(QUARTZ_GEORE);
				addGeOreTables(REDSTONE_GEORE);
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
				return (Iterable<Block>)BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
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
			generateRecipes(COAL_GEORE, recipeConsumer);

			generateRecipes(COPPER_GEORE, recipeConsumer);
			smeltToIngot(COPPER_GEORE, 0.7F, Items.COPPER_INGOT, recipeConsumer);

			generateRecipes(DIAMOND_GEORE, recipeConsumer);
			smeltToIngot(DIAMOND_GEORE, 1.0F, Items.DIAMOND, recipeConsumer);

			generateRecipes(EMERALD_GEORE, recipeConsumer);
			smeltToIngot(EMERALD_GEORE, 1.0F, Items.EMERALD, recipeConsumer);

			generateRecipes(GOLD_GEORE, recipeConsumer);
			smeltToIngot(GOLD_GEORE, 1.0F, Items.GOLD_INGOT, recipeConsumer);

			generateRecipes(IRON_GEORE, recipeConsumer);
			smeltToIngot(IRON_GEORE, 0.7F, Items.IRON_INGOT, recipeConsumer);

			generateRecipes(LAPIS_GEORE, recipeConsumer);
			smeltToIngot(LAPIS_GEORE, 0.2F, Items.LAPIS_LAZULI, recipeConsumer);

			generateRecipes(QUARTZ_GEORE, recipeConsumer);
			smeltToIngot(QUARTZ_GEORE, 0.2F, Items.QUARTZ, recipeConsumer);

			generateRecipes(REDSTONE_GEORE, recipeConsumer);
			smeltToIngot(REDSTONE_GEORE, 0.7F, Items.REDSTONE, recipeConsumer);
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

		private void smeltToIngot(GeOreBlockReg blockReg, float xp, Item item, Consumer<FinishedRecipe> recipeConsumer) {
			SimpleCookingRecipeBuilder.smelting(Ingredient.of(blockReg.getShard().get()), item, xp, 200)
					.group("geore").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
					.save(recipeConsumer,
							new ResourceLocation(Reference.MOD_ID, item.getRegistryName().getPath() + "_from_smelting_" + blockReg.getShard().get().getRegistryName().getPath()));
			SimpleCookingRecipeBuilder.blasting(Ingredient.of(blockReg.getShard().get()), item, xp, 100)
					.group("geore").unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
					.save(recipeConsumer,
							new ResourceLocation(Reference.MOD_ID, item.getRegistryName().getPath() + "_from_blasting_" + blockReg.getShard().get().getRegistryName().getPath()));
		}
	}

	private static class Language extends LanguageProvider {
		public Language(DataGenerator gen) {
			super(gen, Reference.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			add("itemGroup.geore", "GeOre");

			generateLang("Coal", COAL_GEORE);
			generateLang("Copper", COPPER_GEORE);
			generateLang("Diamond", DIAMOND_GEORE);
			generateLang("Emerald", EMERALD_GEORE);
			generateLang("Gold", GOLD_GEORE);
			generateLang("Iron", IRON_GEORE);
			generateLang("Lapis", LAPIS_GEORE);
			generateLang("Quartz", QUARTZ_GEORE);
			generateLang("Redstone", REDSTONE_GEORE);
		}

		public void generateLang(String name, GeOreBlockReg blockReg) {
			addBlock(blockReg.getBlock(), "Block Of " + name + " Geore");
			addBlock(blockReg.getBudding(), "Budding " + name + " Geore");
			addBlock(blockReg.getSmallBud(), "Small  " + name + " Geore Bud");
			addBlock(blockReg.getMediumBud(), "Medium  " + name + " Geore Bud");
			addBlock(blockReg.getLargeBud(), "Large  " + name + " Geore Bud");
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
			generateGeoreModels(COAL_GEORE);
			generateGeoreModels(COPPER_GEORE);
			generateGeoreModels(DIAMOND_GEORE);
			generateGeoreModels(EMERALD_GEORE);
			generateGeoreModels(GOLD_GEORE);
			generateGeoreModels(IRON_GEORE);
			generateGeoreModels(LAPIS_GEORE);
			generateGeoreModels(QUARTZ_GEORE);
			generateGeoreModels(REDSTONE_GEORE);
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
			ModelFile clusterBlock = models().getExistingFile(modLoc("block/" + block.getRegistryName().getPath()));
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
			generateGeoreModels(COAL_GEORE);
			generateGeoreModels(COPPER_GEORE);
			generateGeoreModels(DIAMOND_GEORE);
			generateGeoreModels(EMERALD_GEORE);
			generateGeoreModels(GOLD_GEORE);
			generateGeoreModels(IRON_GEORE);
			generateGeoreModels(LAPIS_GEORE);
			generateGeoreModels(QUARTZ_GEORE);
			generateGeoreModels(REDSTONE_GEORE);
		}

		protected void generateGeoreModels(GeOreBlockReg blockReg) {
			cubeAll(blockReg.getBlock().get().getRegistryName().getPath(), modLoc(BLOCK_FOLDER + "/" + blockReg.getBlock().get().getRegistryName().getPath()));
			cubeAll(blockReg.getBudding().get().getRegistryName().getPath(), modLoc(BLOCK_FOLDER + "/" + blockReg.getBudding().get().getRegistryName().getPath()));

			crossBlock(blockReg.getCluster().get());
			crossBlock(blockReg.getSmallBud().get());
			crossBlock(blockReg.getMediumBud().get());
			crossBlock(blockReg.getLargeBud().get());
		}

		private void crossBlock(Block block) {
			String path = block.getRegistryName().getPath();
			cross(path, modLoc(BLOCK_FOLDER + "/" + path));
		}
	}

	private static class ItemModels extends ItemModelProvider {
		public ItemModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Reference.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			generateGeoreModels(COAL_GEORE);
			generateGeoreModels(COPPER_GEORE);
			generateGeoreModels(DIAMOND_GEORE);
			generateGeoreModels(EMERALD_GEORE);
			generateGeoreModels(GOLD_GEORE);
			generateGeoreModels(IRON_GEORE);
			generateGeoreModels(LAPIS_GEORE);
			generateGeoreModels(QUARTZ_GEORE);
			generateGeoreModels(REDSTONE_GEORE);
		}

		protected void generateGeoreModels(GeOreBlockReg blockReg) {
			singleTexture(blockReg.getShard().get().getRegistryName().getPath(), new ResourceLocation("item/generated"),
					"layer0", new ResourceLocation(Reference.MOD_ID, "item/" + blockReg.getShard().get().getRegistryName().getPath()));

			withExistingParent(blockReg.getBlock().get().getRegistryName().getPath(),
					new ResourceLocation(Reference.MOD_ID, BLOCK_FOLDER + "/" + blockReg.getBlock().get().getRegistryName().getPath()));
			withExistingParent(blockReg.getBudding().get().getRegistryName().getPath(),
					new ResourceLocation(Reference.MOD_ID, BLOCK_FOLDER + "/" + blockReg.getBudding().get().getRegistryName().getPath()));

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
					.customLoader(SeparatePerspectiveModelBuilder::begin)
					.base(nested().parent(spyglass_hand))
					.perspective(TransformType.GUI, nested().parent(spyglass_gui))
					.perspective(TransformType.GROUND, nested().parent(spyglass_gui))
					.perspective(TransformType.FIXED, nested().parent(spyglass_gui))
					.end();
		}

		private void makeCluster(Block block) {
			String path = block.getRegistryName().getPath();
			getBuilder(path)
					.parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
					.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
					.transforms().transform(Perspective.HEAD)
					.translation(0, 14, -5).end();
		}

		private void makeSmallBud(Block block) {
			String path = block.getRegistryName().getPath();
			getBuilder(path)
					.parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
					.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
					.transforms().transform(Perspective.FIRSTPERSON_RIGHT)
					.rotation(0, -90, 25)
					.translation(0, 6, 0)
					.scale(0.68F, 0.68F, 0.68F).end()
					.transform(Perspective.FIXED)
					.translation(0, 7, 0).end();
		}

		private void makeMediumBud(Block block) {
			String path = block.getRegistryName().getPath();
			getBuilder(path)
					.parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
					.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
					.transforms().transform(Perspective.FIXED)
					.translation(0, 6, 0).end();
		}

		private void makeLargeBud(Block block) {
			String path = block.getRegistryName().getPath();
			getBuilder(path)
					.parent(new ModelFile.UncheckedModelFile(mcLoc("item/amethyst_bud")))
					.texture("layer0", modLoc(BLOCK_FOLDER + "/" + path))
					.transforms().transform(Perspective.FIXED)
					.translation(0, 4, 0).end();
		}
	}

	public static class GeoreBlockTags extends BlockTagsProvider {
		public GeoreBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, Reference.MOD_ID, existingFileHelper);
		}

		public static final Tag.Named<Block> RELOCATION_NOT_SUPPORTED = forgeTag("relocation_not_supported");

		private static Tag.Named<Block> forgeTag(String name) {
			return BlockTags.bind(new ResourceLocation("forge", name).toString());
		}

		@Override
		protected void addTags() {
			this.tag(RELOCATION_NOT_SUPPORTED)
					.add(COAL_GEORE.getBudding().get())
					.add(COPPER_GEORE.getBudding().get())
					.add(DIAMOND_GEORE.getBudding().get())
					.add(EMERALD_GEORE.getBudding().get())
					.add(GOLD_GEORE.getBudding().get())
					.add(IRON_GEORE.getBudding().get())
					.add(LAPIS_GEORE.getBudding().get())
					.add(QUARTZ_GEORE.getBudding().get())
					.add(REDSTONE_GEORE.getBudding().get());

			this.addMineable(COAL_GEORE);
			this.addMineable(COPPER_GEORE);
			this.addMineable(DIAMOND_GEORE);
			this.addMineable(EMERALD_GEORE);
			this.addMineable(GOLD_GEORE);
			this.addMineable(IRON_GEORE);
			this.addMineable(LAPIS_GEORE);
			this.addMineable(QUARTZ_GEORE);
			this.addMineable(REDSTONE_GEORE);
			this.addCrystalSounds(REDSTONE_GEORE);

			this.addCrystalSounds(COAL_GEORE);
			this.addCrystalSounds(COPPER_GEORE);
			this.addCrystalSounds(DIAMOND_GEORE);
			this.addCrystalSounds(EMERALD_GEORE);
			this.addCrystalSounds(GOLD_GEORE);
			this.addCrystalSounds(IRON_GEORE);
			this.addCrystalSounds(LAPIS_GEORE);
			this.addCrystalSounds(QUARTZ_GEORE);
			this.addCrystalSounds(REDSTONE_GEORE);
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
	}
}
