package com.shynieke.geore.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.shynieke.geore.config.GeOreConfig;
import com.shynieke.geore.item.GeoreSpyglassItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Matrix4f;

public class SpyglassHandler {

	@SubscribeEvent
	public void onOverlayRender(RenderGuiOverlayEvent.Pre event) {
		if (!event.getOverlay().id().equals(new ResourceLocation("spyglass"))) return;
		Minecraft minecraft = Minecraft.getInstance();
		Player player = Minecraft.getInstance().player;
		PoseStack poseStack = event.getGuiGraphics().pose();

		if (player != null && minecraft.options.getCameraType().isFirstPerson()) {
			if (player.isUsingItem() && player.getUseItem().getItem() instanceof GeoreSpyglassItem georeSpyglassItem) {
				int color = georeSpyglassItem.getOverlayHex();
				RenderSystem.enableBlend();
				fillGradient(poseStack, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight(), color, color);
				RenderSystem.defaultBlendFunc();
			}
		}
	}

	protected void fillGradient(PoseStack poseStack, int width, int height, int color1, int color2) {
		fillGradient(poseStack, width, height, color1, color2, GeOreConfig.CLIENT.spyglassIntensity.get().floatValue());
	}

	protected static void fillGradient(PoseStack poseStack, int width, int height, int color1, int color2, float intensity) {
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		fillGradient(poseStack.last().pose(), bufferbuilder, width, height, color1, color2, intensity);
		tesselator.end();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected static void fillGradient(Matrix4f matrix4f, BufferBuilder builder, int width, int height, int color1, int color2, float intensity) {
		float f1 = (float) (color1 >> 16 & 255) / 255.0F;
		float f2 = (float) (color1 >> 8 & 255) / 255.0F;
		float f3 = (float) (color1 & 255) / 255.0F;
		float f5 = (float) (color2 >> 16 & 255) / 255.0F;
		float f6 = (float) (color2 >> 8 & 255) / 255.0F;
		float f7 = (float) (color2 & 255) / 255.0F;
		builder.vertex(matrix4f, (float) width, (float) 0, (float) 0).color(f1, f2, f3, intensity).endVertex();
		builder.vertex(matrix4f, (float) 0, (float) 0, (float) 0).color(f1, f2, f3, intensity).endVertex();
		builder.vertex(matrix4f, (float) 0, (float) height, (float) 0).color(f5, f6, f7, intensity).endVertex();
		builder.vertex(matrix4f, (float) width, (float) height, (float) 0).color(f5, f6, f7, intensity).endVertex();
	}
}
