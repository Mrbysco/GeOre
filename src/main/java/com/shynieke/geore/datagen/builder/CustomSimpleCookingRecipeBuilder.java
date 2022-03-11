package com.shynieke.geore.datagen.builder;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class CustomSimpleCookingRecipeBuilder implements RecipeBuilder {
	private final Item result;
	private final Ingredient ingredient;
	private final float experience;
	private final int cookingTime;
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	@Nullable
	private String group;
	private final SimpleCookingSerializer<?> serializer;

	private CustomSimpleCookingRecipeBuilder(ItemLike result, Ingredient ingredient, float xp, int time, SimpleCookingSerializer<?> serializer) {
		this.result = result.asItem();
		this.ingredient = ingredient;
		this.experience = xp;
		this.cookingTime = time;
		this.serializer = serializer;
	}

	public static CustomSimpleCookingRecipeBuilder cooking(Ingredient ingredient, ItemLike result, float xp, int time, SimpleCookingSerializer<?> serializer) {
		return new CustomSimpleCookingRecipeBuilder(result, ingredient, xp, time, serializer);
	}

	public static CustomSimpleCookingRecipeBuilder campfireCooking(Ingredient ingredient, ItemLike result, float xp, int time) {
		return cooking(ingredient, result, xp, time, RecipeSerializer.CAMPFIRE_COOKING_RECIPE);
	}

	public static CustomSimpleCookingRecipeBuilder blasting(Ingredient ingredient, ItemLike result, float xp, int time) {
		return cooking(ingredient, result, xp, time, RecipeSerializer.BLASTING_RECIPE);
	}

	public static CustomSimpleCookingRecipeBuilder smelting(Ingredient ingredient, ItemLike result, float xp, int time) {
		return cooking(ingredient, result, xp, time, RecipeSerializer.SMELTING_RECIPE);
	}

	public static CustomSimpleCookingRecipeBuilder smoking(Ingredient ingredient, ItemLike result, float xp, int time) {
		return cooking(ingredient, result, xp, time, RecipeSerializer.SMOKING_RECIPE);
	}

	public CustomSimpleCookingRecipeBuilder unlockedBy(String triggerName, CriterionTriggerInstance triggerInstance) {
		this.advancement.addCriterion(triggerName, triggerInstance);
		return this;
	}

	public CustomSimpleCookingRecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	public Item getResult() {
		return this.result;
	}

	public void save(Consumer<FinishedRecipe> recipeConsumer, ResourceLocation p_126264_) {
		this.ensureValid(p_126264_);
		this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_126264_)).rewards(AdvancementRewards.Builder.recipe(p_126264_)).requirements(RequirementsStrategy.OR);
		recipeConsumer.accept(new CustomSimpleCookingRecipeBuilder.Result(p_126264_, this.group == null ? "" : this.group, this.ingredient, this.result, this.experience, this.cookingTime, this.advancement, new ResourceLocation(p_126264_.getNamespace(), "recipes/" + p_126264_.getPath()), this.serializer));
	}

	private void ensureValid(ResourceLocation p_126266_) {
		if (this.advancement.getCriteria().isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + p_126266_);
		}
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final String group;
		private final Ingredient ingredient;
		private final Item result;
		private final float experience;
		private final int cookingTime;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

		public Result(ResourceLocation id, String group, Ingredient ingredient, Item result, float xp, int time, Advancement.Builder advancement, ResourceLocation advancementID, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
			this.id = id;
			this.group = group;
			this.ingredient = ingredient;
			this.result = result;
			this.experience = xp;
			this.cookingTime = time;
			this.advancement = advancement;
			this.advancementId = advancementID;
			this.serializer = serializer;
		}

		public void serializeRecipeData(JsonObject jsonObject) {
			if (!this.group.isEmpty()) {
				jsonObject.addProperty("group", this.group);
			}

			jsonObject.add("ingredient", this.ingredient.toJson());
			jsonObject.addProperty("result", Registry.ITEM.getKey(this.result).toString());
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