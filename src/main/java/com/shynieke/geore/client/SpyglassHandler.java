package com.shynieke.geore.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.shynieke.geore.item.GeoreSpyglassItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpyglassHandler{
	@SubscribeEvent
	public void onGameOverlayRender(RenderGameOverlayEvent.Pre event) {
		if(event.getType() != ElementType.ALL) return;
		Minecraft minecraft = Minecraft.getInstance();
		Player player = Minecraft.getInstance().player;
		PoseStack poseStack = event.getMatrixStack();

		if(minecraft.options.getCameraType().isFirstPerson()) {
			if (player.isUsingItem() && player.getUseItem().getItem() instanceof GeoreSpyglassItem georeSpyglassItem) {
				int color = georeSpyglassItem.getOverlayHex();
				RenderSystem.enableBlend();
				fillGradient(poseStack, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight(), color, color);
				RenderSystem.defaultBlendFunc();
			}
		}
	}

	protected void fillGradient(PoseStack poseStack, int width, int height, int color1, int color2) {
		fillGradient(poseStack, 0, 0, width, height, color1, color2, 0);
	}

	protected static void fillGradient(PoseStack poseStack, int p_168742_, int p_168743_, int width, int height, int color1, int color2, int z) {
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		fillGradient(poseStack.last().pose(), bufferbuilder, p_168742_, p_168743_, width, height, z, color1, color2);
		tesselator.end();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected static void fillGradient(Matrix4f matrix4f, BufferBuilder builder, int p_93126_, int p_93127_, int width, int height, int z, int color1, int color2) {
		float f1 = (float)(color1 >> 16 & 255) / 255.0F;
		float f2 = (float)(color1 >> 8 & 255) / 255.0F;
		float f3 = (float)(color1 & 255) / 255.0F;
		float f5 = (float)(color2 >> 16 & 255) / 255.0F;
		float f6 = (float)(color2 >> 8 & 255) / 255.0F;
		float f7 = (float)(color2 & 255) / 255.0F;
		builder.vertex(matrix4f, (float)width, (float)p_93127_, (float)z).color(f1, f2, f3, 0.15F).endVertex();
		builder.vertex(matrix4f, (float)p_93126_, (float)p_93127_, (float)z).color(f1, f2, f3, 0.15F).endVertex();
		builder.vertex(matrix4f, (float)p_93126_, (float)height, (float)z).color(f5, f6, f7, 0.15F).endVertex();
		builder.vertex(matrix4f, (float)width, (float)height, (float)z).color(f5, f6, f7, 0.15F).endVertex();
	}
}
