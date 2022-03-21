package mod.hitlerhax.mixin;

import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventPush;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public class HtlrMixinWorld {
	@Redirect(method = {
			"handleMaterialAcceleration" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isPushedByWater()Z"))
	public boolean isPushedbyWaterHook(Entity entity) {
		HtlrEventPush event = new HtlrEventPush(2, entity);
		HtlrEventBus.EVENT_BUS.post(event);
		return entity.isPushedByWater() && !event.isCancelled();
	}
	
}
	    
