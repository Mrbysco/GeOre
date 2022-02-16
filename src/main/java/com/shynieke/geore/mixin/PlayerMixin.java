package com.shynieke.geore.mixin;

import com.shynieke.geore.item.GeoreSpyglassItem;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/world/entity/player/Player;isScoping()Z", cancellable = true)
	public void georeIsScoping(CallbackInfoReturnable<Boolean> cir) {
		Player player = (Player) (Object) this;
		if(player.isUsingItem() && player.getUseItem().getItem() instanceof GeoreSpyglassItem) {
			cir.setReturnValue(true);
		}
	}
}
