package mod.hitlerhax.mixin;

import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventSetOpaqueCube;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VisGraph.class)
public class HtlrMixinVisGraph {
	@Inject(method = "setOpaqueCube", at = @At("HEAD"), cancellable = true)
	public void setOpaqueCube(BlockPos pos, CallbackInfo info) {
		HtlrEventSetOpaqueCube l_Event = new HtlrEventSetOpaqueCube(); /// < pos is unused
		HtlrEventBus.EVENT_BUS.post(l_Event);
		if (l_Event.isCancelled())
			info.cancel();
	}
}