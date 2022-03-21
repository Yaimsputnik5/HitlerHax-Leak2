package mod.hitlerhax.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.hitlerhax.Main;
import mod.hitlerhax.module.modules.utilities.Reconnect;
import mod.hitlerhax.ui.AutoReconnectButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.resources.I18n;

@Mixin(value = GuiDisconnected.class, priority = Integer.MAX_VALUE)
public abstract class HtlrMixinGuiDisconnected extends HtlrMixinGuiScreen {
	@Shadow
	private int textHeight;

	private AutoReconnectButton ReconnectingButton;

	@Inject(method = "initGui", at = @At("RETURN"))
	public void initGui(CallbackInfo info) {
		if (Main.moduleManager.getModule("Reconnect").toggled) {
			buttonList.clear();

			this.buttonList.add(new GuiButton(0, this.width / 2 - 100,
					Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT, this.height - 30),
					I18n.format("gui.toMenu")));
			this.buttonList.add(new GuiButton(421, this.width / 2 - 100, Math
					.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 20, this.height - 10),
					"Reconnect"));
			this.buttonList.add(ReconnectingButton = new AutoReconnectButton(420, this.width / 2 - 100, Math
					.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 40, this.height + 10),
					"AutoReconnect"));
		}
	}

	@Inject(method = "actionPerformed", at = @At("RETURN"))
	protected void actionPerformed(GuiButton button, CallbackInfo info) {
		if (Main.moduleManager.getModule("Reconnect").toggled) {
			if (button.id == 420) {
				ReconnectingButton.Clicked();
			} else if (button.id == 421) {
				if (((Reconnect) Main.moduleManager.getModule("Reconnect")).serverData != null)
					Minecraft.getMinecraft().displayGuiScreen(new GuiConnecting(null, Minecraft.getMinecraft(),
							((Reconnect) Main.moduleManager.getModule("Reconnect")).serverData));
			}
		}
	}

	@Inject(method = "drawScreen", at = @At("RETURN"))
	public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo info) {
		if (Main.moduleManager.getModule("Reconnect").toggled) {
			if (buttonList.size() > 3) {
				this.buttonList.clear();
				this.buttonList.add(new GuiButton(0, this.width / 2 - 100, Math
						.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT, this.height - 30),
						I18n.format("gui.toMenu")));
				this.buttonList.add(new GuiButton(421, this.width / 2 - 100,
						Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 30,
								this.height - 10),
						"Reconnect"));
				this.buttonList.add(ReconnectingButton = new AutoReconnectButton(420, this.width / 2 - 100,
						Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT + 60,
								this.height + 10),
						"AutoReconnect"));
			}
		}
	}
}
