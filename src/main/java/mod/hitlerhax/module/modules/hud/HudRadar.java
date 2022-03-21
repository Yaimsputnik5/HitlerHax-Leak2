package mod.hitlerhax.module.modules.hud;

import mod.hitlerhax.Main;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.util.Globals;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class HudRadar implements Globals {

	private ArrayList<Blip> blips = new ArrayList<>();

	private int radius;

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		radius = ((IntSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
				"Radar Radius")).value;
		if (Main.moduleManager.getModule("Hud").toggled) {
			// ScaledResolution sr = new ScaledResolution(mc);

			if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
				if (((BooleanSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
						"Radar")).enabled) {

					GL11.glDisable(GL11.GL_CULL_FACE);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					GL11.glLineWidth(3);

					GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.5f);

					GL11.glBegin(GL11.GL_POLYGON);
					for (int ii = 0; ii < 100; ii++) {
						float theta = 2.0f * 3.1415926f * (float) (ii) / 100f;// get the current angle
						float x = (float) (radius * Math.cos(theta));// calculate the x component
						float y = (float) (radius * Math.sin(theta));// calculate the y component
						GL11.glVertex2f(x + radius + 10, y + radius + 30);// output vertex
					}
					GL11.glEnd();

					GL11.glColor3f(0.6f, 1.0f, 0.4f);

					GL11.glBegin(GL11.GL_LINE_LOOP);
					for (int ii = 0; ii < 100; ii++) {
						float theta = 2.0f * 3.1415926f * (float) (ii) / 100f;// get the current angle
						float x = (float) (radius * Math.cos(theta));// calculate the x component
						float y = (float) (radius * Math.sin(theta));// calculate the y component
						GL11.glVertex2f(x + radius + 10, y + radius + 30);// output vertex
					}
					GL11.glEnd();

					GL11.glLineWidth(1);

					GL11.glBegin(GL11.GL_LINE_LOOP);
					for (int ii = 0; ii < 36; ii++) {
						float theta = 2.0f * 3.1415926f * (float) (ii) / 36f;// get the current angle
						float x = (float) (radius * 0.75 * Math.cos(theta));// calculate the x component
						float y = (float) (radius * 0.75 * Math.sin(theta));// calculate the y component
						GL11.glVertex2f(x + radius + 10, y + radius + 30);// output vertex
					}
					GL11.glEnd();

					GL11.glBegin(GL11.GL_LINE_LOOP);
					for (int ii = 0; ii < 36; ii++) {
						float theta = 2.0f * 3.1415926f * (float) (ii) / 36f;// get the current angle
						float x = (float) (radius * 0.5 * Math.cos(theta));// calculate the x component
						float y = (float) (radius * 0.5 * Math.sin(theta));// calculate the y component
						GL11.glVertex2f(x + radius + 10, y + radius + 30);// output vertex
					}
					GL11.glEnd();

					GL11.glBegin(GL11.GL_LINE_LOOP);
					for (int ii = 0; ii < 36; ii++) {
						float theta = 2.0f * 3.1415926f * (float) (ii) / 36f;// get the current angle
						float x = (float) (radius * 0.25 * Math.cos(theta));// calculate the x component
						float y = (float) (radius * 0.25 * Math.sin(theta));// calculate the y component
						GL11.glVertex2f(x + radius + 10, y + radius + 30);// output vertex
					}
					GL11.glEnd();

					GL11.glBegin(GL11.GL_LINES);
					GL11.glVertex2f(10, radius + 30);
					GL11.glVertex2f(radius * 2 + 10, radius + 30);
					GL11.glEnd();

					GL11.glBegin(GL11.GL_LINES);
					GL11.glVertex2f(radius + 10, 30);
					GL11.glVertex2f(radius + 10, radius * 2 + 30);
					GL11.glEnd();

					int rotation = (int)(System.currentTimeMillis() % (720 * 4)) / (2 * 4);

					GL11.glColor4f(0.6f, 1.0f, 0.4f, 1.0f);
					GL11.glBegin(GL11.GL_LINES);
					GL11.glVertex2f(radius + 10 + (float) (radius * Math.cos((rotation - 6) * (3.1415926f / 180))),
							radius + 30 + (float) (radius * Math.sin((rotation - 6) * (3.1415926f / 180))));
					GL11.glVertex2f(radius + 10, radius + 30);
					GL11.glEnd();

					GL11.glBegin(GL11.GL_TRIANGLE_FAN);
					GL11.glColor4f(0.6f, 1.0f, 0.4f, 0.7f);
					GL11.glVertex2f(radius + 10, radius + 30);
					for (float i = 0.7f; i > 0.0f; i -= 0.05f) {
						int newRot = rotation - (int) ((1 - i) * 20);
						GL11.glColor4f(0.6f, 1.0f, 0.4f, i);
						GL11.glVertex2f(radius + 10 + (float) (radius * Math.cos(newRot * (3.1415926f / 180))),
								radius + 30 + (float) (radius * Math.sin(newRot * (3.1415926f / 180))));
					}
					GL11.glEnd();

					ArrayList<Blip> newBlips = (ArrayList<Blip>) blips.clone();
					for (Blip b : blips) {
						if (System.currentTimeMillis() - b.creationTimeMS > 10000) {
							newBlips.remove(b);
						}
					}
					blips = newBlips;
					for (EntityPlayer e : mc.world.playerEntities) {
						if (e != mc.player) {
							int posX = (int) (e.posX - mc.player.posX);
							int posZ = (int) (e.posZ - mc.player.posZ);
							posX *= 2;
							posZ *= 2;
							// hahaha funny ternary operator have fun trying to figure out what this does
							if (rotation - 6 > (int) (Math.toDegrees(Math.atan2(posZ, posX)) > 360
									? Math.toDegrees(Math.atan2(posZ, posX))
											- (360 * (int) (Math.toDegrees(Math.atan2(posZ, posX)) / 360))
									: Math.toDegrees(Math.atan2(posZ, posX)) < 0
											? Math.toDegrees(Math.atan2(posZ, posX))
													+ (((int) (Math.toDegrees(Math.atan2(posZ, posX)) / 360) + 1) * 360)
											: Math.toDegrees(Math.atan2(posZ, posX)))
									&& rotation - 20 < (int) (Math.toDegrees(Math.atan2(posZ, posX)) > 360
											? Math.toDegrees(Math.atan2(posZ, posX))
													- (360 * (int) (Math.toDegrees(Math.atan2(posZ, posX)) / 360))
											: Math.toDegrees(Math.atan2(posZ, posX)) < 0 ? Math
													.toDegrees(Math.atan2(posZ, posX))
													+ (((int) (Math.toDegrees(Math.atan2(posZ, posX)) / 360) + 1) * 360)
													: Math.toDegrees(Math.atan2(posZ, posX)))) {
								newBlips = (ArrayList<Blip>) blips.clone();
								for (Blip b : blips) {
									if (e.getName().equals(b.name)) {
										newBlips.remove(b);
									}
								}
								blips = newBlips;
								if (!(posX * posX + posZ * posZ <= (radius - 25) * (radius - 25))) {
									posX = 100000;
								}
								blips.add(new Blip(e.getName(), posX, posZ));
							}
						}
					}
					for (Blip b : blips) {
						b.render();
					}

					GL11.glEnable(GL11.GL_CULL_FACE);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glColor3f(1, 1, 1);
				}
			}
		}
	}

	/*
	 * @SubscribeEvent public void renderPlayer(RenderPlayerEvent e) {
	 * if(e.getEntityPlayer()) }
	 */

	private class Blip {
		public long creationTimeMS;
		public String name;
		public int x;
		public int z;

		public Blip(String name, int x, int z) {
			this.name = name;
			this.x = x;
			this.z = z;
			this.creationTimeMS = System.currentTimeMillis();
		}

		public void render() {
			float transparency = (2500f - (System.currentTimeMillis() - creationTimeMS)) / 2500f;
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			GlStateManager.disableAlpha();
			GlStateManager.shadeModel(7425);
			GlStateManager.shadeModel(7424);
			GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
			GlStateManager.enableTexture2D();
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceLocation tex = new ResourceLocation("textures/jew.png");
			mc.getTextureManager().bindTexture(tex);
			GL11.glPushMatrix();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, transparency);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex2f(radius + 10 + x - 25, radius + 30 + z - 25);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex2f(radius + 10 + x + 25, radius + 30 + z - 25);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex2f(radius + 10 + x + 25, radius + 30 + z + 25);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex2f(radius + 10 + x - 25, radius + 30 + z + 25);
			GL11.glEnd();
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
	}
}
