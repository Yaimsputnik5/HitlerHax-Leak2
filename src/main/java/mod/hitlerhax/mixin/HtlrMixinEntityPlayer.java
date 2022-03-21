package mod.hitlerhax.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventPlayerTravel;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@Mixin(value = EntityPlayer.class, priority = Integer.MAX_VALUE)
public abstract class HtlrMixinEntityPlayer extends EntityLivingBase {

	public HtlrMixinEntityPlayer(World worldIn) {
		super(worldIn);
	}

	@Inject(method = "travel", at = @At("HEAD"), cancellable = true)
	public void travel(float strafe, float vertical, float forward, CallbackInfo info) {
		if (EntityPlayerSP.class.isAssignableFrom(this.getClass())) {
			HtlrEventPlayerTravel event = new HtlrEventPlayerTravel();
			HtlrEventBus.EVENT_BUS.post(event);
			if (event.isCancelled()) {
				move(MoverType.SELF, motionX, motionY, motionZ);
				info.cancel();
			}
		}
	}
}
