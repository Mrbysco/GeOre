package com.shynieke.geore.registry;

import com.shynieke.geore.Reference;
import com.shynieke.geore.recipes.TagBlastingRecipe;
import com.shynieke.geore.recipes.TagFurnaceRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GeOreRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);

	public static final RegistryObject<TagFurnaceRecipe.Serializer> TAG_FURNACE_SERIALIZER = RECIPE_SERIALIZERS.register("furnace", TagFurnaceRecipe.Serializer::new);
	public static final RegistryObject<TagBlastingRecipe.Serializer> TAG_BLASTING_SERIALIZER = RECIPE_SERIALIZERS.register("blasting", TagBlastingRecipe.Serializer::new);
}
