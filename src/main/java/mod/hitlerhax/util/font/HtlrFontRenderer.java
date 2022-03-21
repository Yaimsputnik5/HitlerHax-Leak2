package mod.hitlerhax.util.font;

import mod.hitlerhax.util.render.ColorUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class HtlrFontRenderer extends HtlrFont {
	protected final CharData[] boldChars = new CharData[256];
	protected final CharData[] italicChars = new CharData[256];
	protected final CharData[] boldItalicChars = new CharData[256];

	private final int[] colorCode = new int[32];

	public HtlrFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
		super(font, antiAlias, fractionalMetrics);
		setupMinecraftColorcodes();
		setupBoldItalicIDs();
	}

	public float drawStringWithShadow(String text, double x, double y, ColorUtil color) {
		float shadowWidth = drawString(text, x + 1D, y + 1D, color, true);
		return Math.max(shadowWidth, drawString(text, x, y, color, false));
	}

	public float drawString(String text, float x, float y, ColorUtil color) {
		return drawString(text, x, y, color, false);
	}

	public float drawString(String text, double x, double y, ColorUtil gsColor, boolean shadow) {
		x -= 1;
		y -= 2;
		ColorUtil color = new ColorUtil(gsColor);
		if (text == null)
			return 0.0F;
		if (color.getRed() == 255 && color.getGreen() == 255 && color.getBlue() == 255 && color.getAlpha() == 32)
			color = new ColorUtil(255, 255, 255);
		if (color.getAlpha() < 4)
			color = new ColorUtil(color, 255);
		if (shadow)
			color = new ColorUtil(color.getRed() / 4, color.getGreen() / 4, color.getBlue() / 4, color.getAlpha());

		CharData[] currentData = this.charData;
		boolean bold = false;
		boolean italic = false;
		boolean strikethrough = false;
		boolean underline = false;
		boolean render = true;
		x *= 2.0D;
		y *= 2.0D;
		if (render) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.5D, 0.5D, 0.5D);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
					color.getAlpha() / 255.0f);
			int size = text.length();
			GlStateManager.enableTexture2D();
			GlStateManager.bindTexture(tex.getGlTextureId());
			// GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getGlTextureId());
			for (int i = 0; i < size; i++) {
				char character = text.charAt(i);
				if ((character == '\u00A7') && (i < size)) {
					int colorIndex = 21;
					try {
						colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
					} catch (Exception ignored) {

					}
					if (colorIndex < 16) {
						bold = false;
						italic = false;
						underline = false;
						strikethrough = false;
						GlStateManager.bindTexture(tex.getGlTextureId());
						currentData = this.charData;
						if (colorIndex < 0)
							colorIndex = 15;
						if (shadow)
							colorIndex += 16;
						int colorcode = this.colorCode[colorIndex];
						GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F,
								(colorcode & 0xFF) / 255.0F, color.getAlpha());
					} else if (colorIndex == 16) {
					} else if (colorIndex == 17) {
						bold = true;
						if (italic) {
							GlStateManager.bindTexture(texItalicBold.getGlTextureId());
							currentData = this.boldItalicChars;
						} else {
							GlStateManager.bindTexture(texBold.getGlTextureId());
							currentData = this.boldChars;
						}
					} else if (colorIndex == 18) {
						strikethrough = true;
					} else if (colorIndex == 19) {
						underline = true;
					} else if (colorIndex == 20) {
						italic = true;
						if (bold) {
							GlStateManager.bindTexture(texItalicBold.getGlTextureId());
							currentData = this.boldItalicChars;
						} else {
							GlStateManager.bindTexture(texItalic.getGlTextureId());
							currentData = this.italicChars;
						}
					} else if (colorIndex == 21) {
						bold = false;
						italic = false;
						underline = false;
						strikethrough = false;
						GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f,
								color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
						GlStateManager.bindTexture(tex.getGlTextureId());
						currentData = this.charData;
					}
					i++;
				} else if ((character < currentData.length) && (character >= 0)) {
					GlStateManager.glBegin(GL11.GL_TRIANGLES);
					drawChar(currentData, character, (float) x, (float) y);
					GlStateManager.glEnd();
					if (strikethrough)
						drawLine(x, y + currentData[character].height / 2, x + currentData[character].width - 8.0D,
								y + currentData[character].height / 2, 1.0F);
					if (underline)
						drawLine(x, y + currentData[character].height - 2.0D, x + currentData[character].width - 8.0D,
								y + currentData[character].height - 2.0D, 1.0F);
					x += currentData[character].width - 8 + this.charOffset;
				}
			}
			GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
			GlStateManager.popMatrix();
		}
		return (float) x / 2.0F;
	}

	@Override
	public int getStringWidth(String text) {
		if (text == null) {
			return 0;
		}
		int width = 0;
		CharData[] currentData = this.charData;
		boolean bold = false;
		boolean italic = false;
		int size = text.length();

		for (int i = 0; i < size; i++) {
			char character = text.charAt(i);
			if ((character == '\u00A7') && (i < size)) {
				int colorIndex = "0123456789abcdefklmnor".indexOf(character);
				if (colorIndex < 16) {
					bold = false;
					italic = false;
				} else if (colorIndex == 17) {
					bold = true;
					if (italic)
						currentData = this.boldItalicChars;
					else
						currentData = this.boldChars;
				} else if (colorIndex == 20) {
					italic = true;
					if (bold)
						currentData = this.boldItalicChars;
					else
						currentData = this.italicChars;
				} else if (colorIndex == 21) {
					bold = false;
					italic = false;
					currentData = this.charData;
				}
				i++;
			} else if ((character < currentData.length) && (character >= 0)) {
				width += currentData[character].width - 8 + this.charOffset;
			}
		}

		return width / 2;
	}

	public void setFont(Font font) {
		super.setFont(font);
		setupBoldItalicIDs();
	}

	public void setAntiAlias(boolean antiAlias) {
		super.setAntiAlias(antiAlias);
		setupBoldItalicIDs();
	}

	public void setFractionalMetrics(boolean fractionalMetrics) {
		super.setFractionalMetrics(fractionalMetrics);
		setupBoldItalicIDs();
	}

	protected DynamicTexture texBold;
	protected DynamicTexture texItalic;
	protected DynamicTexture texItalicBold;

	private void setupBoldItalicIDs() {
		texBold = setupTexture(this.font.deriveFont(Font.BOLD), this.antiAlias, this.fractionalMetrics, this.boldChars);
		texItalic = setupTexture(this.font.deriveFont(Font.ITALIC), this.antiAlias, this.fractionalMetrics, this.italicChars);
		texItalicBold = setupTexture(this.font.deriveFont(Font.BOLD | Font.ITALIC), this.antiAlias, this.fractionalMetrics,
									 this.boldItalicChars);
	}

	private void drawLine(double x, double y, double x1, double y1, float width) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(width);
		GL11.glBegin(1);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x1, y1);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	private void setupMinecraftColorcodes() {
		for (int index = 0; index < 32; index++) {
			int noClue = (index >> 3 & 0x1) * 85;
			int red = (index >> 2 & 0x1) * 170 + noClue;
			int green = (index >> 1 & 0x1) * 170 + noClue;
			int blue = (index & 0x1) * 170 + noClue;

			if (index == 6) {
				red += 85;
			}

			if (index >= 16) {
				red /= 4;
				green /= 4;
				blue /= 4;
			}

			this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
		}
	}
}