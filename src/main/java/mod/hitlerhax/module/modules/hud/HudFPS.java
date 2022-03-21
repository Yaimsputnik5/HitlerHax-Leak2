package mod.hitlerhax.module.modules.hud;

import mod.hitlerhax.Main;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.util.Globals;
import mod.hitlerhax.util.font.FontUtils;
import mod.hitlerhax.util.render.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudFPS extends Gui implements Globals {

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		if (Main.moduleManager.getModule("Hud").toggled) {
			ScaledResolution sr = new ScaledResolution(mc);

			if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
				if (((BooleanSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
						"FPS")).enabled) {

					String fps = "FPS: " + Minecraft.getDebugFPS();
					boolean isChatOpen = mc.currentScreen instanceof GuiChat;

					int heightFPS = isChatOpen ? sr.getScaledHeight() - 35 : sr.getScaledHeight() - 20;

					FontUtils.drawStringWithShadow(fps, 2, heightFPS, ColorUtil.getRainbow(300, 255));

				}
			}
		}
	}
}
