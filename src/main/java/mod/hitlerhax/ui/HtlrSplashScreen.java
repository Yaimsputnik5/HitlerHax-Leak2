package mod.hitlerhax.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import mod.hitlerhax.Main;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.util.Globals;
import mod.hitlerhax.util.font.FontUtils;
import mod.hitlerhax.util.render.ColorUtil;
import mod.hitlerhax.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class HtlrSplashScreen extends GuiScreen {

	private final ArrayList<ResourceLocation> backgrounds = new ArrayList<>();

	private ResourceLocation background;

	private int x;
	private int y;

	private float tempSound;

	@Override
	public void onGuiClosed() {
		mc.gameSettings.setSoundLevel(SoundCategory.MUSIC, tempSound);
	}

	@Override
	public void initGui() {
		tempSound = mc.gameSettings.getSoundLevel(SoundCategory.MUSIC);
		mc.gameSettings.setSoundLevel(SoundCategory.MUSIC, 0.0f);

		backgrounds.add(new ResourceLocation("textures/drippler_wave.png"));
		backgrounds.add(new ResourceLocation("textures/krieg.jpg"));

		Random random = new Random();
		this.background = backgrounds.get(random.nextInt(backgrounds.size()));

		mc.gameSettings.enableVsync = false;
		mc.gameSettings.limitFramerate = 200;
		this.playMusic();

		this.x = this.width / 32;
		this.y = this.height / 32 + 10;
		this.buttonList.add(new SplashScreenButton(0, this.x, this.y, "Singleplayer"));
		this.buttonList.add(new SplashScreenButton(1, this.x, this.y + 22, "Multiplayer"));
		this.buttonList.add(new SplashScreenButton(2, this.x, this.y + 44, "Settings"));
		this.buttonList.add(new SplashScreenButton(2, this.x, this.y + 66, "Exit"));
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.shadeModel(7425);
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static void drawCompleteImage(float posX, float posY, float width, float height) {
		GL11.glPushMatrix();
		GL11.glTranslatef(posX, posY, 0.0f);
		GL11.glBegin(7);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(0.0f, height, 0.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(width, height, 0.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(width, 0.0f, 0.0f);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height;
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (HtlrSplashScreen.isHovered(this.x, this.y, mc.fontRenderer.getStringWidth("Singleplayer"),
				mc.fontRenderer.FONT_HEIGHT, mouseX, mouseY)) {
			this.mc.displayGuiScreen(new GuiWorldSelection(this));
		} else if (HtlrSplashScreen.isHovered(this.x, this.y + 22, mc.fontRenderer.getStringWidth("Multiplayer"),
				mc.fontRenderer.FONT_HEIGHT, mouseX, mouseY)) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		} else if (HtlrSplashScreen.isHovered(this.x, this.y + 44, mc.fontRenderer.getStringWidth("Settings"),
				mc.fontRenderer.FONT_HEIGHT, mouseX, mouseY)) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		} else if (HtlrSplashScreen.isHovered(this.x, this.y + 66, mc.fontRenderer.getStringWidth("Exit"),
				mc.fontRenderer.FONT_HEIGHT, mouseX, mouseY)) {
			this.mc.shutdown();
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.x = this.width / 32;
		this.y = this.height / 32 + 10;
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		this.mc.getTextureManager().bindTexture(this.background);
		HtlrSplashScreen.drawCompleteImage(0.0f, 0.0f, this.width, this.height);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private static class SplashScreenButton extends GuiButton implements Globals {

		public SplashScreenButton(int buttonId, int x, int y, String buttonText) {
			super(buttonId, x, y, mc.fontRenderer.getStringWidth(buttonText), mc.fontRenderer.FONT_HEIGHT, buttonText);
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible) {
				this.enabled = true;
				this.hovered = (float) mouseX >= (float) this.x && mouseY >= this.y && mouseX < this.x + this.width
						&& mouseY < this.y + this.height;
				FontUtils.drawStringWithShadow(this.displayString, this.x + 1, this.y,
						new ColorUtil(255, 255, 255, 255));
				if (this.hovered) {
					RenderUtil.drawLine(this.x - 5f, this.y + 2 + mc.fontRenderer.FONT_HEIGHT,
							this.x + 5f + mc.fontRenderer.getStringWidth(this.displayString),
							this.y + 2 + mc.fontRenderer.FONT_HEIGHT, 2f, Color.BLUE.getRGB());
				}
			}
		}

		@Override
		public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
			return this.enabled && this.visible
					&& mouseX >= this.x - mc.fontRenderer.getStringWidth(this.displayString) / 2.0f && mouseY >= this.y
					&& mouseX < this.x + this.width && mouseY < this.y + this.height;
		}
	}

	public void playMusic() {
		mc.soundHandler.stopSounds();
		if (((BooleanSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
				"Menu")).enabled) {
			mc.soundHandler.playSound(Main.songManager.getMenuSong());
		}
	}
}
