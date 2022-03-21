package mod.hitlerhax.mixin;

import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventMotionUpdate;
import mod.hitlerhax.event.events.HtlrEventMove;
import mod.hitlerhax.event.events.HtlrEventPush;
import mod.hitlerhax.event.events.HtlrEventSwing;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityPlayerSP.class)
public class HtlrMixinEntityPlayerSP extends HtlrMixinEntity {

	@Inject(method = "move", at = @At("HEAD"), cancellable = true)
	private void move(MoverType type, double x, double y, double z, CallbackInfo info) {

		HtlrEventMove event = new HtlrEventMove(type, x, y, z);
		HtlrEventBus.EVENT_BUS.post(event);

		if (event.isCancelled()) {
			super.move(type, event.get_x(), event.get_y(), event.get_z());
			info.cancel();
		}
	}

	@Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
	public void OnPreUpdateWalkingPlayer(CallbackInfo p_Info) {

		HtlrEventMotionUpdate l_Event = new HtlrEventMotionUpdate(0);
		HtlrEventBus.EVENT_BUS.post(l_Event);
		if (l_Event.isCancelled())
			p_Info.cancel();
	}

	@Redirect(method = {
			"onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "net/minecraft/util/math/AxisAlignedBB.minY:D"))
	private double minYHook(AxisAlignedBB bb) {
		return bb.minY;
	}

	@Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"), cancellable = true)
	public void OnPostUpdateWalkingPlayer(CallbackInfo p_Info) {

		HtlrEventMotionUpdate l_Event = new HtlrEventMotionUpdate(1);
		HtlrEventBus.EVENT_BUS.post(l_Event);
		if (l_Event.isCancelled())
			p_Info.cancel();

	}

	@Inject(method = "swingArm", at = @At("RETURN"), cancellable = true)
	public void swingArm(EnumHand p_Hand, CallbackInfo p_Info) {

		HtlrEventSwing l_Event = new HtlrEventSwing(p_Hand);
		HtlrEventBus.EVENT_BUS.post(l_Event);
		if (l_Event.isCancelled())
			p_Info.cancel();

	}

	@Inject(method = { "pushOutOfBlocks" }, at = { @At(value = "HEAD") }, cancellable = true)
	private void pushOutOfBlocksHook(double x, double y, double z, CallbackInfoReturnable<Boolean> info) {
		HtlrEventPush event = new HtlrEventPush(1);
		HtlrEventBus.EVENT_BUS.post(event);
		if (event.isCancelled()) {
			info.setReturnValue(false);
		}
	}
}
