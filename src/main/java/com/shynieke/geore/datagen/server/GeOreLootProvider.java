package com.shynieke.geore.datagen.server;

import com.shynieke.geore.registry.GeOreBlockReg;
import com.shynieke.geore.registry.GeOreRegistry;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.shynieke.geore.registry.GeOreRegistry.BLOCKS;

public class GeOreLootProvider extends LootTableProvider {
	public GeOreLootProvider(PackOutput packOutput, CompletableFuture<Provider> lookupProvider) {
		super(packOutput, Set.of(), List.of(new SubProviderEntry(GeOreLootProvider.GeOreBlockTables::new, LootContextParamSets.BLOCK)), lookupProvider);
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
				if (reg.getName().equals("ancient_debris")) {
					addAncientDebrisGeOreTables();
				} else {
					addGeOreTables(reg);
				}
			}
		}

		protected void addGeOreTables(GeOreBlockReg blockReg) {
			this.dropSelf(blockReg.getBlock().get());
			this.add(blockReg.getCluster().get(), (block) ->
					createSilkTouchDispatchTable(block,
							LootItem.lootTableItem(blockReg.getShard().get())
									.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)))
									.apply(ApplyBonusCount.addOreBonusCount(enchantmentLookup.getOrThrow(Enchantments.FORTUNE)))
									.when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES)))
									.otherwise(applyExplosionDecay(block, LootItem.lootTableItem(blockReg.getShard().get())
											.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
			this.dropWhenSilkTouch(blockReg.getSmallBud().get());
			this.dropWhenSilkTouch(blockReg.getMediumBud().get());
			this.dropWhenSilkTouch(blockReg.getLargeBud().get());
			this.add(blockReg.getBudding().get(), noDrop());
			this.dropSelf(blockReg.getTintedGlass().get());
		}

		private void addAncientDebrisGeOreTables() {
			GeOreBlockReg blockReg = GeOreRegistry.ANCIENT_DEBRIS_GEORE;
			this.dropSelf(blockReg.getBlock().get());
			this.add(blockReg.getCluster().get(), (block) ->
					createSilkTouchDispatchTable(block,
							LootItem.lootTableItem(blockReg.getShard().get())
									.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
					));

			this.dropWhenSilkTouch(blockReg.getSmallBud().get());
			this.dropWhenSilkTouch(blockReg.getMediumBud().get());
			this.dropWhenSilkTouch(blockReg.getLargeBud().get());
			this.add(blockReg.getBudding().get(), noDrop());
			this.dropSelf(blockReg.getTintedGlass().get());
		}

		@NotNull
		@Override
		protected Iterable<Block> getKnownBlocks() {
			return BLOCKS.getEntries().stream().map(holder -> (Block) holder.get())::iterator;
		}
	}

	@Override
	protected void validate(@NotNull WritableRegistry<LootTable> writableregistry,
	                        @NotNull ValidationContext validationcontext,
	                        @NotNull ProblemReporter.Collector problemreporter$collector) {
		super.validate(writableregistry, validationcontext, problemreporter$collector);
	}
}
