package com.shynieke.geore.datagen.server;

import com.shynieke.geore.Reference;
import com.shynieke.geore.datagen.builder.TagSmeltingRecipeBuilder;
import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class GeOreRecipeProvider extends RecipeProvider {

	public GeOreRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> output) {
		generateRecipes(GeOreRegistry.COAL_GEORE, output);

		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.TORCH, 2)
				.pattern("X").pattern("#")
				.define('#', Tags.Items.RODS_WOODEN).define('X', GeOreRegistry.COAL_GEORE.getShard().get())
				.unlockedBy("has_coal_geore_shard",
						has(GeOreRegistry.COAL_GEORE.getShard().get())).save(output, "geore:torch_from_coal_shard");

		generateRecipe(GeOreRegistry.COPPER_GEORE, 0.7F, Items.COPPER_INGOT, output);
		generateRecipe(GeOreRegistry.DIAMOND_GEORE, 1.0F, Items.DIAMOND, output);
		generateRecipe(GeOreRegistry.EMERALD_GEORE, 1.0F, Items.EMERALD, output);
		generateRecipe(GeOreRegistry.GOLD_GEORE, 1.0F, Items.GOLD_INGOT, output);
		generateRecipe(GeOreRegistry.IRON_GEORE, 0.7F, Items.IRON_INGOT, output);
		generateRecipe(GeOreRegistry.LAPIS_GEORE, 0.2F, Items.LAPIS_LAZULI, output);
		generateRecipe(GeOreRegistry.QUARTZ_GEORE, 0.2F, Items.QUARTZ, output);
		generateRecipe(GeOreRegistry.REDSTONE_GEORE, 0.7F, Items.REDSTONE, output);

		generateRecipes(GeOreRegistry.ANCIENT_DEBRIS_GEORE, output);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.ANCIENT_DEBRIS)
				.pattern("###")
				.pattern("###")
				.pattern("###")
				.define('#', GeOreRegistry.ANCIENT_DEBRIS_GEORE.getShard().get())
				.unlockedBy("has_ancient_debris_geore_shard", has(GeOreRegistry.ANCIENT_DEBRIS_GEORE.getShard().get()))
				.save(output, "geore:ancient_debris_from_ancient_debris_shard");

		//Mod compat
		generateTagRecipe(GeOreRegistry.RUBY_GEORE, 0.7F, getForgeTag("gems/ruby"), output);
		generateTagRecipe(GeOreRegistry.SAPPHIRE_GEORE, 0.7F, getForgeTag("gems/sapphire"), output);
		generateTagRecipe(GeOreRegistry.TOPAZ_GEORE, 0.7F, getForgeTag("gems/topaz"), output);
		generateTagRecipe(GeOreRegistry.ZINC_GEORE, 0.7F, getForgeTag("ingots/zinc"), output);
		generateTagRecipe(GeOreRegistry.URANINITE_GEORE, 0.7F, getForgeTag("raw_materials/uraninite"), output);
		generateTagRecipe(GeOreRegistry.BLACK_QUARTZ_GEORE, 0.7F, getForgeTag("gems/black_quartz"), output);
		generateTagRecipe(GeOreRegistry.MONAZITE_GEORE, 0.7F, getForgeTag("dusts/monazite"), output);
		generateTagRecipe(GeOreRegistry.ALUMINUM_GEORE, 0.7F, getForgeTag("ingots/aluminum"), output);
		generateTagRecipe(GeOreRegistry.LEAD_GEORE, 0.7F, getForgeTag("ingots/lead"), output);
		generateTagRecipe(GeOreRegistry.NICKEL_GEORE, 0.7F, getForgeTag("ingots/nickel"), output);
		generateTagRecipe(GeOreRegistry.OSMIUM_GEORE, 0.7F, getForgeTag("ingots/osmium"), output);
		generateTagRecipe(GeOreRegistry.PLATINUM_GEORE, 0.7F, getForgeTag("ingots/platinum"), output);
		generateTagRecipe(GeOreRegistry.SILVER_GEORE, 0.7F, getForgeTag("ingots/silver"), output);
		generateTagRecipe(GeOreRegistry.TIN_GEORE, 0.7F, getForgeTag("ingots/tin"), output);
		generateTagRecipe(GeOreRegistry.TUNGSTEN_GEORE, 0.7F, getForgeTag("ingots/tungsten"), output);
		generateTagRecipe(GeOreRegistry.URANIUM_GEORE, 0.7F, getForgeTag("ingots/uranium"), output);

		generateHarderTagRecipe(GeOreRegistry.ALLTHEMODIUM_GEORE, 0.7F, getForgeTag("nuggets/allthemodium"), output);
		generateHarderTagRecipe(GeOreRegistry.VIBRANIUM_GEORE, 0.7F, getForgeTag("nuggets/vibranium"), output);
		generateHarderTagRecipe(GeOreRegistry.UNOBTAINIUM_GEORE, 0.7F, getForgeTag("nuggets/unobtainium"), output);
	}

	private void generateRecipe(GeOreBlockReg reg, float xp, ItemLike result, Consumer<FinishedRecipe> output) {
		generateRecipes(reg, output);
		smeltToOre(reg, xp, result, output);
	}

	private void generateTagRecipe(GeOreBlockReg reg, float xp, TagKey<Item> result, Consumer<FinishedRecipe> output) {
		generateRecipes(reg, output);
		smeltToOre(reg, xp, result, output);
	}

	private TagKey<Item> getForgeTag(String path) {
		return ItemTags.create(new ResourceLocation("forge", path));
	}

	private void generateRecipes(GeOreBlockReg blockReg, Consumer<FinishedRecipe> output) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockReg.getBlock().get())
				.pattern("SS").pattern("SS")
				.define('S', blockReg.getShard().get())
				.unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, blockReg.getSpyglass().get())
				.pattern(" # ").pattern(" X ").pattern(" X ")
				.define('#', blockReg.getShard().get())
				.define('X', Items.COPPER_INGOT)
				.unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockReg.getTintedGlass().get(), 2)
				.define('G', Blocks.GLASS)
				.define('S', blockReg.getShard().get())
				.pattern(" S ")
				.pattern("SGS")
				.pattern(" S ")
				.unlockedBy("has_shard", has(blockReg.getShard().get()))
				.save(output);
	}

	private void generateHarderTagRecipe(GeOreBlockReg blockReg, float xp, TagKey<Item> oreTag, Consumer<FinishedRecipe> output) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockReg.getBlock().get())
				.pattern("SS").pattern("SS")
				.define('S', blockReg.getShard().get())
				.unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, blockReg.getSpyglass().get())
				.pattern(" # ").pattern(" X ").pattern(" X ")
				.define('#', blockReg.getShard().get())
				.define('X', Items.COPPER_INGOT)
				.unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blockReg.getTintedGlass().get(), 2)
				.define('G', Blocks.GLASS)
				.define('S', blockReg.getShard().get())
				.pattern(" S ")
				.pattern("SGS")
				.pattern(" S ")
				.unlockedBy("has_shard", has(blockReg.getShard().get()))
				.save(output);

		Ingredient outputIngredient = Ingredient.of(oreTag);
		new ConditionalRecipe.Builder()
				.addCondition(
						new NotCondition(new TagEmptyCondition(oreTag.location()))
				)
				.addRecipe(
						TagSmeltingRecipeBuilder.smelting(Ingredient.of(blockReg.getBlock().get()), RecipeCategory.MISC, outputIngredient, xp, 200)
								.group("geore")
								.unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))::save
				)
				.build(output, Reference.modLoc(blockReg.getName() + "_from_smelting_" + blockReg.getShard().getId().getPath()));


		new ConditionalRecipe.Builder()
				.addCondition(
						new NotCondition(new TagEmptyCondition(oreTag.location()))
				)
				.addRecipe(
						TagSmeltingRecipeBuilder.blasting(Ingredient.of(blockReg.getBlock().get()), RecipeCategory.MISC, outputIngredient, xp, 100)
								.group("geore")
								.unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))::save
				)
				.build(output, Reference.modLoc(blockReg.getName() + "_from_blasting_" + blockReg.getShard().getId().getPath()));
	}

	private void smeltToOre(GeOreBlockReg blockReg, float xp, ItemLike item, Consumer<FinishedRecipe> output) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(blockReg.getShard().get()), RecipeCategory.MISC, item, xp, 200)
				.group("geore")
				.unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
				.save(output, Reference.modLoc(
						ForgeRegistries.ITEMS.getKey(item.asItem()).getPath() + "_from_smelting_" + blockReg.getShard().getId().getPath()
				));
		SimpleCookingRecipeBuilder.blasting(Ingredient.of(blockReg.getShard().get()), RecipeCategory.MISC, item, xp, 100)
				.group("geore"
				).unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))
				.save(output, Reference.modLoc(
						ForgeRegistries.ITEMS.getKey(item.asItem()).getPath() + "_from_blasting_" + blockReg.getShard().getId().getPath()
				));
	}

	private void smeltToOre(GeOreBlockReg blockReg, float xp, TagKey<Item> oreTag, Consumer<FinishedRecipe> output) {
		Ingredient outputIngredient = Ingredient.of(oreTag);
		new ConditionalRecipe.Builder()
				.addCondition(
						new NotCondition(new TagEmptyCondition(oreTag.location()))
				)
				.addRecipe(
						TagSmeltingRecipeBuilder.smelting(Ingredient.of(blockReg.getShard().get()), RecipeCategory.MISC, outputIngredient, xp, 200)
								.group("geore")
								.unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))::save
				)
				.build(output, Reference.modLoc(blockReg.getName() + "_from_smelting_" + blockReg.getShard().getId().getPath()));


		new ConditionalRecipe.Builder()
				.addCondition(
						new NotCondition(new TagEmptyCondition(oreTag.location()))
				)
				.addRecipe(
						TagSmeltingRecipeBuilder.blasting(Ingredient.of(blockReg.getShard().get()), RecipeCategory.MISC, outputIngredient, xp, 100)
								.group("geore")
								.unlockedBy("has_" + blockReg.getName() + "geore_shard", has(blockReg.getShard().get()))::save
				)
				.build(output, Reference.modLoc(blockReg.getName() + "_from_blasting_" + blockReg.getShard().getId().getPath()));
	}
}
