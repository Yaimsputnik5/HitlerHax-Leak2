package mod.hitlerhax.mixin;

import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventSteerEntity;
import net.minecraft.entity.passive.EntityLlama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLlama.class)
public class HtlrMixinEntityLlama {
	@Inject(method = "canBeSteered", at = @At("HEAD"), cancellable = true)
	public void canBeSteered(CallbackInfoReturnable<Boolean> cir) {
		HtlrEventSteerEntity event = new HtlrEventSteerEntity();
		HtlrEventBus.EVENT_BUS.post(event);

		if (event.isCancelled()) {
			cir.cancel();
			cir.setReturnValue(true);
		}
	}
}