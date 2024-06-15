package com.shynieke.geore.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import org.joml.Matrix4f;

public class SpyglassHandler {

	@SubscribeEvent
	public void onOverlayRender(RenderGuiLayerEvent.Pre event) {
		if (!event.getName().equals(ResourceLocation.parse("camera_overlays"))) return;
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
		BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		fillGradient(poseStack.last().pose(), bufferbuilder, width, height, color1, color2, intensity);
		BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	//TODO: Maybe just use the gradiant related method in GuiGraphics
	protected static void fillGradient(Matrix4f matrix4f, BufferBuilder builder, int width, int height, int color1, int color2, float intensity) {
		float f1 = (float) (color1 >> 16 & 255) / 255.0F;
		float f2 = (float) (color1 >> 8 & 255) / 255.0F;
		float f3 = (float) (color1 & 255) / 255.0F;
		float f5 = (float) (color2 >> 16 & 255) / 255.0F;
		float f6 = (float) (color2 >> 8 & 255) / 255.0F;
		float f7 = (float) (color2 & 255) / 255.0F;
		builder.addVertex(matrix4f, (float) width, (float) 0, (float) 0).setColor(f1, f2, f3, intensity);
		builder.addVertex(matrix4f, (float) 0, (float) 0, (float) 0).setColor(f1, f2, f3, intensity);
		builder.addVertex(matrix4f, (float) 0, (float) height, (float) 0).setColor(f5, f6, f7, intensity);
		builder.addVertex(matrix4f, (float) width, (float) height, (float) 0).setColor(f5, f6, f7, intensity);
	}
}
