package mod.hitlerhax.util.font;

import mod.hitlerhax.Main;
import mod.hitlerhax.util.Globals;
import mod.hitlerhax.util.render.ColorUtil;

public class FontUtils implements Globals {

	public static void drawStringWithShadow(String text, int x, int y, ColorUtil color) {
		if (Main.moduleManager.getModule("ClientFont").toggled && Main.customFontRenderer != null) {
			Main.customFontRenderer.drawStringWithShadow(text, x, y, color);
		} else {
			mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
		}
	}

	public static int getStringWidth(String string) {
		if (Main.moduleManager.getModule("ClientFont").toggled && Main.customFontRenderer != null) {
			return Main.customFontRenderer.getStringWidth(string);
		} else {
			return mc.fontRenderer.getStringWidth(string);
		}
	}

	public static int getCharWidth(char ch) {
		if (Main.moduleManager.getModule("ClientFont").toggled && Main.customFontRenderer != null) {
			return Main.customFontRenderer.getStringWidth(String.valueOf(ch));
		} else {
			return mc.fontRenderer.getCharWidth(ch);
		}
	}

	public static int getFontHeight() {
		if (Main.moduleManager.getModule("ClientFont").toggled && Main.customFontRenderer != null) {
			return Main.customFontRenderer.getHeight();
		} else {
			return mc.fontRenderer.FONT_HEIGHT;
		}
	}

	public static int drawStringWithShadow(String text, double x, double y, int color) {
		if (Main.moduleManager.getModule("ClientFont").toggled && Main.customFontRenderer != null) {
			return (int) Main.customFontRenderer.drawStringWithShadow(text, x, y, new ColorUtil(color));
		} else {
			return mc.fontRenderer.drawStringWithShadow(text, (float) x, (float) y, color);
		}
	}

	public static float drawString(String text, int x, int y, ColorUtil color) {
		if (Main.moduleManager.getModule("ClientFont").toggled && Main.customFontRenderer != null) {
			return Main.customFontRenderer.drawString(text, x, y, color);
		} else {
			return mc.fontRenderer.drawString(text, x, y, color.getRGB());
		}
	}

	public static String trimStringToWidth(String text, int width) {
		return FontUtils.trimStringToWidth(text, width, false);
	}

	public static String trimStringToWidth(String text, int width, boolean reverse) {
		StringBuilder stringbuilder = new StringBuilder();
		int i = 0;
		int j = reverse ? text.length() - 1 : 0;
		int k = reverse ? -1 : 1;
		boolean flag = false;
		boolean flag1 = false;

		for (int l = j; l >= 0 && l < text.length() && i < width; l += k) {
			char c0 = text.charAt(l);
			int i1 = FontUtils.getCharWidth(c0);

			if (flag) {
				flag = false;

				if (c0 != 'l' && c0 != 'L') {
					if (c0 == 'r' || c0 == 'R') {
						flag1 = false;
					}
				} else {
					flag1 = true;
				}
			} else if (i1 < 0) {
				flag = true;
			} else {
				i += i1;

				if (flag1) {
					++i;
				}
			}

			if (i > width) {
				break;
			}

			if (reverse) {
				stringbuilder.insert(0, c0);
			} else {
				stringbuilder.append(c0);
			}
		}

		return stringbuilder.toString();
	}

}