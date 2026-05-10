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
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;


/**
 * Taken from the Grinder repository from Noobanidus <3
 */
public class TagBlastingRecipe extends BlastingRecipe {
	private static final MapCodec<TagBlastingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
							Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
							AbstractCookingRecipe.CookingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
							Ingredient.CODEC.fieldOf("ingredient").forGetter(SingleItemRecipe::input),
							Ingredient.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
							Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(AbstractCookingRecipe::experience),
							Codec.INT.fieldOf("cookingtime").orElse(100).forGetter(AbstractCookingRecipe::cookingTime)
					)
					.apply(instance, TagBlastingRecipe::new)
	);
	private static final StreamCodec<RegistryFriendlyByteBuf, TagBlastingRecipe> STREAM_CODEC = StreamCodec.composite(
			Recipe.CommonInfo.STREAM_CODEC,
			o -> o.commonInfo,
			TagBlastingRecipe.CookingBookInfo.STREAM_CODEC,
			o -> o.bookInfo,
			Ingredient.CONTENTS_STREAM_CODEC,
			TagBlastingRecipe::input,
			Ingredient.CONTENTS_STREAM_CODEC,
			TagBlastingRecipe::output,
			ByteBufCodecs.FLOAT,
			TagBlastingRecipe::experience,
			ByteBufCodecs.INT,
			TagBlastingRecipe::cookingTime,
			TagBlastingRecipe::new
	);
	public static final RecipeSerializer<TagBlastingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	protected final Recipe.CommonInfo commonInfo;
	protected final Ingredient output;

	public TagBlastingRecipe(Recipe.CommonInfo commonInfo, CookingBookInfo bookInfo, Ingredient ingredientIn,
	                         Ingredient resultin, float experienceIn, int cookTimeIn) {
		super(commonInfo, bookInfo, ingredientIn, new ItemStackTemplate(Items.EGG), experienceIn, cookTimeIn);
		this.commonInfo = commonInfo;
		this.output = resultin;
	}

	@Override
	protected Item furnaceIcon() {
		return Items.BLAST_FURNACE;
	}

	@Override
	public RecipeType<BlastingRecipe> getType() {
		return RecipeType.BLASTING;
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
		return output.getValues().size() > 0 ? new ItemStackTemplate(output.getValues().get(0)) : new ItemStackTemplate(Items.EGG);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RecipeSerializer<BlastingRecipe> getSerializer() {
		return (RecipeSerializer<BlastingRecipe>) (RecipeSerializer) GeOreRecipes.TAG_BLASTING_SERIALIZER.get();
	}
}

