package com.shynieke.geore.datagen.builder;

import com.google.gson.JsonObject;
import com.shynieke.geore.registry.GeOreRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TagSmeltingRecipeBuilder implements RecipeBuilder {
	private final RecipeCategory category;
	private final CookingBookCategory bookCategory;
	private final Ingredient result;
	private final Ingredient ingredient;
	private final float experience;
	private final int cookingTime;
	private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
	@Nullable
	private String group;
	private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

	private TagSmeltingRecipeBuilder(RecipeCategory category, CookingBookCategory bookCategory, Ingredient result, Ingredient ingredient, float xp, int cookingTime, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
		this.category = category;
		this.bookCategory = bookCategory;
		this.result = result;
		this.ingredient = ingredient;
		this.experience = xp;
		this.cookingTime = cookingTime;
		this.serializer = serializer;
	}

	public static TagSmeltingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory category, Ingredient result, float xp, int cookingTime) {
		return new TagSmeltingRecipeBuilder(category, CookingBookCategory.MISC, result, ingredient, xp, cookingTime, GeOreRecipes.TAG_BLASTING_SERIALIZER.get());
	}

	public static TagSmeltingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory category, Ingredient result, float xp, int cookingTime) {
		return new TagSmeltingRecipeBuilder(category, CookingBookCategory.MISC, result, ingredient, xp, cookingTime, GeOreRecipes.TAG_FURNACE_SERIALIZER.get());
	}

	public TagSmeltingRecipeBuilder unlockedBy(String criteriaName, CriterionTriggerInstance criteria) {
		this.advancement.addCriterion(criteriaName, criteria);
		return this;
	}

	public TagSmeltingRecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	public Item getResult() {
		return this.result.getItems()[0].getItem();
	}

	public void save(Consumer<FinishedRecipe> recipeConsumer, ResourceLocation id) {
		this.ensureValid(id);
		this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
		recipeConsumer.accept(new Result(id, this.group == null ? "" : this.group, this.bookCategory, this.ingredient, this.result, this.experience, this.cookingTime, this.advancement, id.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.serializer));
	}

	private void ensureValid(ResourceLocation id) {
		if (this.advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + id);
		}
	}

	static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final String group;
		private final CookingBookCategory category;
		private final Ingredient ingredient;
		private final Ingredient result;
		private final float experience;
		private final int cookingTime;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

		public Result(ResourceLocation id, String group, CookingBookCategory category, Ingredient ingredient, Ingredient result, float xp, int cookingTime, Advancement.Builder advancement, ResourceLocation advancementID, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
			this.id = id;
			this.group = group;
			this.category = category;
			this.ingredient = ingredient;
			this.result = result;
			this.experience = xp;
			this.cookingTime = cookingTime;
			this.advancement = advancement;
			this.advancementId = advancementID;
			this.serializer = serializer;
		}

		public void serializeRecipeData(JsonObject jsonObject) {
			if (!this.group.isEmpty()) {
				jsonObject.addProperty("group", this.group);
			}

			jsonObject.addProperty("category", this.category.getSerializedName());
			jsonObject.add("ingredient", this.ingredient.toJson());
			jsonObject.add("result", this.result.toJson());
			jsonObject.addProperty("experience", this.experience);
			jsonObject.addProperty("cookingtime", this.cookingTime);
		}

		public RecipeSerializer<?> getType() {
			return this.serializer;
		}

		public ResourceLocation getId() {
			return this.id;
		}

		@Nullable
		public JsonObject serializeAdvancement() {
			return this.advancement.serializeToJson();
		}

		@Nullable
		public ResourceLocation getAdvancementId() {
			return this.advancementId;
		}
	}
}