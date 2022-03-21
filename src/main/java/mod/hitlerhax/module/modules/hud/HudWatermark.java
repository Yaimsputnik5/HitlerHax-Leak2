package mod.hitlerhax.module.modules.hud;

import mod.hitlerhax.Main;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.util.font.FontUtils;
import mod.hitlerhax.util.render.ColorUtil;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudWatermark {

	@SubscribeEvent
	public void renderOverlay(Text event) {
		if (Main.moduleManager.getModule("Hud").toggled) {

			if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
				if (((BooleanSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
						"Watermark")).enabled)

					FontUtils.drawStringWithShadow("HitlerHax " + Main.DEV_VERSION, 2, 1,
							new ColorUtil(128, 0, 128, 255));

			}
		}
	}
}
