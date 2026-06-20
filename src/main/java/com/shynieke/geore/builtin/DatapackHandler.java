package com.shynieke.geore.builtin;

import com.shynieke.geore.Reference;
import com.shynieke.geore.config.GeOreConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber
public class DatapackHandler {
	@SubscribeEvent
	public static void addPack(AddPackFindersEvent event) {
		if (event.getPackType() == PackType.SERVER_DATA && GeOreConfig.COMMON.disableImmovability.get()) {
			event.addPackFinders(
					Reference.modLoc("movable_geore"), PackType.SERVER_DATA,
					Component.literal("Movable GeOre Datapack").withStyle(ChatFormatting.YELLOW),
					PackSource.BUILT_IN, true, Pack.Position.TOP
			);
		}
	}
}
