package mod.hitlerhax.module.modules.hud;

import mod.hitlerhax.Main;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.util.Globals;
import mod.hitlerhax.util.Timer;
import mod.hitlerhax.util.font.FontUtils;
import mod.hitlerhax.util.render.ColorUtil;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.DecimalFormat;

public class HudBPS implements Globals {

	private final Timer timer = new Timer();
	private double prevPosX;
	private double prevPosZ;
	final DecimalFormat formatter = new DecimalFormat("#.#");

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		if (Main.moduleManager.getModule("Hud").toggled) {
			ScaledResolution sr = new ScaledResolution(mc);

			if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
				if (((BooleanSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
						"BPS")).enabled) {

					if (timer.getPassedMillis(1000)) {
						prevPosX = mc.player.prevPosX;
						prevPosZ = mc.player.prevPosZ;
					}

					String bps = formatter.format(
							Math.floor(((MathHelper.sqrt((mc.player.posX - prevPosX) * (mc.player.posX - prevPosX)
									+ (mc.player.posZ - prevPosZ) * (mc.player.posZ - prevPosZ))) / 1000.0f)
									/ (0.05f / 3600.0f)));
					bps += " km/h";

					boolean isChatOpen = mc.currentScreen instanceof GuiChat;

					int heightBPS = isChatOpen ? sr.getScaledHeight() - 45 : sr.getScaledHeight() - 30;

					FontUtils.drawStringWithShadow(bps, 2, heightBPS, ColorUtil.getRainbow(300, 255));

				}
			}
		}
	}
}
