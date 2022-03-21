package mod.hitlerhax.mixin;

import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventRenderEntityName;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public class HtlrMixinRenderPlayer {
	@Inject(method = "renderEntityName(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;D)V", at = @At("HEAD"), cancellable = true)
	public void renderLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String name,
			double distanceSq, CallbackInfo info) {
		HtlrEventRenderEntityName l_Event = new HtlrEventRenderEntityName(entityIn, x, y, z, name, distanceSq);
		HtlrEventBus.EVENT_BUS.post(l_Event);
		if (l_Event.isCancelled())
			info.cancel();
	}
}