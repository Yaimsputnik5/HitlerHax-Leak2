package mod.hitlerhax.mixin;

import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventHorseSaddled;
import mod.hitlerhax.event.events.HtlrEventSteerEntity;
import net.minecraft.entity.passive.EntityPig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPig.class)
public class HtlrMixinEntityPig {
	@Inject(method = "canBeSteered", at = @At("HEAD"), cancellable = true)
	public void canBeSteered(CallbackInfoReturnable<Boolean> cir) {
		HtlrEventSteerEntity event = new HtlrEventSteerEntity();
		HtlrEventBus.EVENT_BUS.post(event);

		if (event.isCancelled()) {
			cir.cancel();
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "getSaddled", at = @At("HEAD"), cancellable = true)
	public void getSaddled(CallbackInfoReturnable<Boolean> cir) {
		HtlrEventHorseSaddled event = new HtlrEventHorseSaddled();
		HtlrEventBus.EVENT_BUS.post(event);

		if (event.isCancelled()) {
			cir.cancel();
			cir.setReturnValue(true);
		}
	}
}