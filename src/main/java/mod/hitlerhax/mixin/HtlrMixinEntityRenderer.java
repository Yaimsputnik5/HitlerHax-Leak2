package mod.hitlerhax.mixin;

import mod.hitlerhax.Main;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventRenderCamera;
import mod.hitlerhax.module.modules.render.NoRender;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EntityRenderer.class)
public class HtlrMixinEntityRenderer {

	@Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
	public void hurtCameraEffect(float ticks, CallbackInfo info) {
			NoRender noRender = (NoRender)Main.moduleManager.getModule("noRender");
        if (noRender.isToggled() && noRender.hurtCam.is("normal")) {
			info.cancel();
	}
}
	
	@Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"), expect = 0)
	private RayTraceResult rayTraceBlocks(WorldClient worldClient, Vec3d start, Vec3d end) {
		HtlrEventRenderCamera event = new HtlrEventRenderCamera();
	    HtlrEventBus.EVENT_BUS.post(event);
	    if (event.isCancelled())
	        return null;
	    else return worldClient.rayTraceBlocks(start, end);
	    }
}