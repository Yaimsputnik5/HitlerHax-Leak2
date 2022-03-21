package mod.hitlerhax.mixin;

import mod.hitlerhax.Main;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.ui.HtlrSplashScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(value = { Minecraft.class })
public abstract class HtlrMixinMinecraft {

	@Shadow
	public EntityPlayerSP player;
	@Shadow
	public PlayerControllerMP playerController;

	@Shadow
	public abstract void displayGuiScreen(@Nullable GuiScreen var1);

	@Inject(method = { "displayGuiScreen" }, at = { @At(value = "HEAD") })
	private void displayGuiScreen(GuiScreen screen, CallbackInfo ci) {
		if (screen instanceof GuiMainMenu) {
			this.displayGuiScreen(new HtlrSplashScreen());
		}
	}

	@Inject(method = { "runTick()V" }, at = { @At(value = "RETURN") })
	private void runTick(CallbackInfo callbackInfo) {
		if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu && ((BooleanSetting) Main.settingManager
				.getSettingByName(Main.moduleManager.getModule("Hud"), "Menu")).enabled) {
			Minecraft.getMinecraft().displayGuiScreen(new HtlrSplashScreen());
		}
	}

}
