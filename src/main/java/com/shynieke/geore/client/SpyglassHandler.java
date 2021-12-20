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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpyglassHandler{
	protected static final ResourceLocation SPYGLASS_SCOPE_LOCATION = new ResourceLocation("textures/misc/spyglass_scope.png");
	protected float scopeScale;

	@SubscribeEvent
	public void onGameOverlayRender(RenderGameOverlayEvent event) {
		if(event.getType() != ElementType.ALL) return;
		Minecraft minecraft = Minecraft.getInstance();
		Player player = Minecraft.getInstance().player;
		PoseStack poseStack = event.getMatrixStack();

		float f = minecraft.getDeltaFrameTime();
		this.scopeScale = Mth.lerp(0.5F * f, this.scopeScale, 1.125F);

		RenderSystem.enableBlend();
		if(minecraft.options.getCameraType().isFirstPerson()) {
			if (player.isUsingItem() && player.getUseItem().getItem() instanceof GeoreSpyglassItem georeSpyglassItem) {
				int color = georeSpyglassItem.getOverlayHex();
				fillGradient(poseStack, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight(), color, color);
				renderSpyglassOverlay(this.scopeScale, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight(), georeSpyglassItem);
			} else {
				this.scopeScale = 0.5F;
			}
		}
		RenderSystem.defaultBlendFunc();
	}

	protected void renderSpyglassOverlay(float scale, int screenWidth, int screenHeight, GeoreSpyglassItem spyglassItem) {
		RenderSystem.enableBlend();
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, SPYGLASS_SCOPE_LOCATION);
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		float f0 = (float)Math.min(screenWidth, screenHeight);
		float f1 = Math.min((float)screenWidth / f0, (float)screenHeight / f0) * scale;
		float f2 = f0 * f1;
		float f3 = f0 * f1;
		float f4 = ((float)screenWidth - f2) / 2.0F;
		float f5 = ((float)screenHeight - f3) / 2.0F;
		float f6 = f4 + f2;
		float f7 = f5 + f3;
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex((double)f4, (double)f7, -90.0D).uv(0.0F, 1.0F).endVertex();
		bufferbuilder.vertex((double)f6, (double)f7, -90.0D).uv(1.0F, 1.0F).endVertex();
		bufferbuilder.vertex((double)f6, (double)f5, -90.0D).uv(1.0F, 0.0F).endVertex();
		bufferbuilder.vertex((double)f4, (double)f5, -90.0D).uv(0.0F, 0.0F).endVertex();
		tesselator.end();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.disableTexture();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		bufferbuilder.vertex(0.0D, (double)screenHeight, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex((double)screenWidth, (double)screenHeight, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex((double)screenWidth, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(0.0D, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(0.0D, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex((double)screenWidth, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex((double)screenWidth, 0.0D, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(0.0D, 0.0D, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(0.0D, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex((double)f4, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex((double)f4, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex(0.0D, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex((double)f6, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex((double)screenWidth, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex((double)screenWidth, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.vertex((double)f6, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
		tesselator.end();
		RenderSystem.enableTexture();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected void fillGradient(PoseStack poseStack, int width, int height, int color1, int color2) {
		fillGradient(poseStack, 0, 0, width, height, color1, color2, 0);
	}

	protected static void fillGradient(PoseStack poseStack, int p_168742_, int p_168743_, int width, int height, int color1, int color2, int z) {
		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		fillGradient(poseStack.last().pose(), bufferbuilder, p_168742_, p_168743_, width, height, z, color1, color2);
		tesselator.end();
		RenderSystem.disableBlend();
		RenderSystem.enableTexture();
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
