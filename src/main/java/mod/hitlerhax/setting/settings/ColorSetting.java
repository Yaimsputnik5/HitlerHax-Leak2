package mod.hitlerhax.setting.settings;

import mod.hitlerhax.Main;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.Setting;
import mod.hitlerhax.util.render.ColorUtil;

public class ColorSetting extends Setting {
	private int red;
	private int green;
	private int blue;
	private boolean rainbow;

	public void setColor(ColorUtil c) {
		this.red = c.getRed();
		this.green = c.getGreen();
		this.blue = c.getBlue();
	}

	public ColorUtil getColor() {
		if (rainbow) {
			return getRainbow(0, this.getColor().getAlpha());
		}
		return new ColorUtil(this.red, this.green, this.blue);
	}

	public ColorSetting(String name, Module parent, final ColorUtil value) {
		this.name = name;
		this.parent = parent;
		if (!Main.configLoaded) {
			this.red = value.getRed();
			this.green = value.getGreen();
			this.blue = value.getBlue();
		}
	}

	public static ColorUtil getRainbow(int incr, int alpha) {
		ColorUtil color = ColorUtil.fromHSB(((System.currentTimeMillis() + incr * 200) % (360 * 20)) / (360f * 20),
				0.5f, 1f);
		return new ColorUtil(color.getRed(), color.getBlue(), color.getGreen(), alpha);
	}
}
