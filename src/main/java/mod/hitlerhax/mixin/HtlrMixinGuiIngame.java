package mod.hitlerhax.mixin;

import mod.hitlerhax.Main;
import mod.hitlerhax.module.modules.render.NoRender;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class HtlrMixinGuiIngame {

	@Inject(method = "renderPotionEffects", at = @At("HEAD"), cancellable = true)
	protected void renderPotionEffectsHook(ScaledResolution scaledRes, CallbackInfo callbackInfo) {
	        NoRender noRender = (NoRender)Main.moduleManager.getModule("noRender");
	        if (noRender.isToggled() && noRender.potionIndicators.isEnabled()) {

			callbackInfo.cancel();
		}
	}
}