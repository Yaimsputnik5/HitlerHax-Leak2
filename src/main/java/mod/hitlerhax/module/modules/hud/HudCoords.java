package mod.hitlerhax.module.modules.hud;

import mod.hitlerhax.Main;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.util.Globals;
import mod.hitlerhax.util.font.FontUtils;
import mod.hitlerhax.util.render.ColorUtil;
import mod.hitlerhax.util.render.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudCoords extends Gui implements Globals {

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		if (Main.moduleManager.getModule("Hud").toggled) {
			ScaledResolution sr = new ScaledResolution(mc);

			if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
				if (((BooleanSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
						"Coordinates")).enabled) {

					double x = mc.player.posX + ((IntSetting) Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Hud"), "XCoord-offset")).value;
					double y = mc.player.posY;
					double z = mc.player.posZ + ((IntSetting) Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Hud"), "ZCoord-offset")).value;

					String coords;

					if (mc.player.dimension == 0) {
						coords = String.format("X: %s Y: %s Z: %s [%s, %s, %s]", RenderUtil.DF((double) x, 1),
								RenderUtil.DF((int) y, 1), RenderUtil.DF((double) z, 1), (int) (x / 8), (int) (y),
								(int) (z / 8));
					} else if (mc.player.dimension == -1) {
						coords = String.format("X: %s Y: %s Z: %s [%s, %s, %s]", RenderUtil.DF((double) x, 1),
								RenderUtil.DF((int) y, 1), RenderUtil.DF((double) z, 1), (int) (x * 8), (int) (y),
								(int) (z * 8));
					} else {
						coords = String.format("X: %s Y: %s Z: %s", RenderUtil.DF((double) x, 1),
								RenderUtil.DF((int) y, 1), RenderUtil.DF((double) z, 1));
					}
					boolean isChatOpen = mc.currentScreen instanceof GuiChat;

					int heightCoords = isChatOpen ? sr.getScaledHeight() - 25 : sr.getScaledHeight() - 10;

					FontUtils.drawStringWithShadow(coords, 2, heightCoords, ColorUtil.getRainbow(300, 255));

				}
			}
		}
	}
}
