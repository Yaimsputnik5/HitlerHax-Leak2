package mod.hitlerhax.mixin;

import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventTransformSideFirstPerson;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class HtlrMixinItemRenderer {

	@Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
	public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo callbackInfo) {
		HtlrEventTransformSideFirstPerson event = new HtlrEventTransformSideFirstPerson(hand);
		HtlrEventBus.EVENT_BUS.post(event);
	}

	@Inject(method = "transformFirstPerson", at = @At("HEAD"))
	public void transformFirstPerson(EnumHandSide hand, float p_187453_2_, CallbackInfo callbackInfo) {
		HtlrEventTransformSideFirstPerson event = new HtlrEventTransformSideFirstPerson(hand);
		HtlrEventBus.EVENT_BUS.post(event);
	}

}