package com.shynieke.geore.registry;

import com.shynieke.geore.Reference;
import com.shynieke.geore.recipe.TagBlastingRecipe;
import com.shynieke.geore.recipe.TagFurnaceRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class GeOreRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Reference.MOD_ID);

	public static final Supplier<TagFurnaceRecipe.Serializer> TAG_FURNACE_SERIALIZER = RECIPE_SERIALIZERS.register("furnace", TagFurnaceRecipe.Serializer::new);
	public static final Supplier<TagBlastingRecipe.Serializer> TAG_BLASTING_SERIALIZER = RECIPE_SERIALIZERS.register("blasting", TagBlastingRecipe.Serializer::new);
}
