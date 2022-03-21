package mod.hitlerhax.module.modules.hud;

import mod.hitlerhax.Main;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.util.Globals;
import mod.hitlerhax.util.font.FontUtils;
import mod.hitlerhax.util.render.ColorUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Comparator;

public class HudArrayList extends Gui implements Globals {

	public static class ModuleComparator implements Comparator<Module> {

		@Override
		public int compare(Module arg0, Module arg1) {
			if (FontUtils.getStringWidth(arg0.getName()) > FontUtils.getStringWidth(arg1.getName())) {
				return -1;
			}
			if (FontUtils.getStringWidth(arg0.getName()) > FontUtils.getStringWidth(arg1.getName())) {
				return 1;
			}
			return 0;
		}
	}

	final Comparator<Module> comparator = (a, b) -> {
		final String firstName = a.getName();
		final String secondName = b.getName();
		final float dif = FontUtils.getStringWidth(secondName) - FontUtils.getStringWidth(firstName);
		return dif != 0 ? (int) dif : secondName.compareTo(firstName);
	};

	@SubscribeEvent
	public void renderOverlay(Text event) {
		if (Main.moduleManager.getModule("Hud").toggled) {
			Main.moduleManager.modules.sort(new ModuleComparator());
			ScaledResolution sr = new ScaledResolution(mc);
			FontRenderer fr = mc.fontRenderer;

			if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {

				if (((BooleanSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
						"ArrayList")).enabled) {
					int y = 2;
					ArrayList<Module> modules = new ArrayList<>();
					for (Module mod : Main.moduleManager.getModuleList()) {
						if (!mod.getName().equalsIgnoreCase("Esp2dHelper") && mod.isToggled()) {
							if (!mod.getName().equalsIgnoreCase("Hud") && mod.isToggled()) {
								if (!mod.getName().equalsIgnoreCase("ClientFont") && mod.isToggled()) {

									modules.add(mod);
								}
							}
						}
					}

					modules.sort(comparator);

					for (Module m : modules) {
						FontUtils.drawStringWithShadow(m.getName(),
								sr.getScaledWidth() - FontUtils.getStringWidth(m.getName()) - 2, y,
								ColorUtil.getRainbow(300, 255));
						y += fr.FONT_HEIGHT;
					}
				}
			}

		}
	}

	public static String round(double num) {
		return (Integer.valueOf((int) num)).toString() + "."
				+ (Integer.valueOf(Math.abs((int) ((num % 1) * 10)))).toString();
	}

}
