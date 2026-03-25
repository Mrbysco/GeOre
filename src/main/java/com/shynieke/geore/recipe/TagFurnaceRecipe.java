package com.shynieke.geore.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.geore.registry.GeOreRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;

/**
 * Taken from the Grinder repository from Noobanidus <3
 */
public class TagFurnaceRecipe extends AbstractCookingRecipe {
	private static final MapCodec<TagFurnaceRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
							Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
							AbstractCookingRecipe.CookingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
							Ingredient.CODEC.fieldOf("ingredient").forGetter(SingleItemRecipe::input),
							Ingredient.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
							Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(AbstractCookingRecipe::experience),
							Codec.INT.fieldOf("cookingtime").orElse(100).forGetter(AbstractCookingRecipe::cookingTime)
					)
					.apply(instance, TagFurnaceRecipe::new)
	);
	private static final StreamCodec<RegistryFriendlyByteBuf, TagFurnaceRecipe> STREAM_CODEC = StreamCodec.composite(
			Recipe.CommonInfo.STREAM_CODEC,
			o -> o.commonInfo,
			TagFurnaceRecipe.CookingBookInfo.STREAM_CODEC,
			o -> o.bookInfo,
			Ingredient.CONTENTS_STREAM_CODEC,
			TagFurnaceRecipe::input,
			Ingredient.CONTENTS_STREAM_CODEC,
			TagFurnaceRecipe::output,
			ByteBufCodecs.FLOAT,
			TagFurnaceRecipe::experience,
			ByteBufCodecs.INT,
			TagFurnaceRecipe::cookingTime,
			TagFurnaceRecipe::new
	);
	public static final RecipeSerializer<TagFurnaceRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	protected final Ingredient output;

	public TagFurnaceRecipe(Recipe.CommonInfo commonInfo, CookingBookInfo bookInfo, Ingredient ingredientIn, Ingredient resultIn, float experienceIn, int cookTimeIn) {
		super(commonInfo, bookInfo, ingredientIn, null, experienceIn, cookTimeIn);
		this.output = resultIn;
	}

	@Override
	protected Item furnaceIcon() {
		return Items.FURNACE;
	}

	@Override
	public RecipeType<SmeltingRecipe> getType() {
		return RecipeType.SMELTING;
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return switch (this.category()) {
			case BLOCKS -> RecipeBookCategories.BLAST_FURNACE_BLOCKS;
			case FOOD, MISC -> RecipeBookCategories.BLAST_FURNACE_MISC;
		};
	}

	public Ingredient output() {
		return output;
	}

	@Override
	public ItemStack assemble(SingleRecipeInput input) {
		return this.result().create();
	}

	@Override
	public ItemStackTemplate result() {
		return output.getValues().size() > 0 ? new ItemStackTemplate(output.getValues().get(0)) : null;
	}

	@Override
	public RecipeSerializer<TagFurnaceRecipe> getSerializer() {
		return GeOreRecipes.TAG_FURNACE_SERIALIZER.get();
	}
}

