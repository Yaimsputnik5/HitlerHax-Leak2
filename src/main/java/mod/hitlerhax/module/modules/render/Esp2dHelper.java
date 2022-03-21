package mod.hitlerhax.module.modules.render;

import org.lwjgl.opengl.GL11;

import mod.hitlerhax.Main;
import mod.hitlerhax.event.events.HtlrEventRender;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.util.EntityUtil;
import mod.hitlerhax.util.render.ColorUtil;
import mod.hitlerhax.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;

public class Esp2dHelper extends Module {

	public Esp2dHelper() {
		super("Esp2dHelper", "Esp2dHelper", Category.CLIENT);
		toggled = true;
	}

	ColorUtil ppColor;
	int opacityGradient;

	@Override
	public void render(HtlrEventRender event) {
		// add mobs and items to 2dEsp
		if (Main.moduleManager.getModule("Esp's") != null && Main.moduleManager.getModule("Esp's").isToggled()
				&& ((Esp) Main.moduleManager.getModule("Esp's")).entityMode.is("outlineEsp2D")) {
			if ((mc.getRenderManager()).options == null)
				return;
			float viewerYaw = (mc.getRenderManager()).playerViewY;
			mc.world.loadedEntityList.stream().filter(entity -> entity != mc.player).forEach(e -> {
				RenderUtil.prepare_new();
				GlStateManager.pushMatrix();
				Vec3d pos = EntityUtil.getInterpolatedPos(e, mc.getRenderPartialTicks());
				GlStateManager.translate(pos.x - (mc.getRenderManager()).renderPosX,
						pos.y - (mc.getRenderManager()).renderPosY, pos.z - (mc.getRenderManager()).renderPosZ);
				GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
				GL11.glEnable(2848);
				if (e instanceof net.minecraft.entity.player.EntityPlayer) {
					ppColor = new ColorUtil(((Esp) Main.moduleManager.getModule("Esp's")).playerColor.getColor());
					GlStateManager.glLineWidth(((Esp) Main.moduleManager.getModule("Esp's")).lineWidth.getValue());
					ppColor.glColor();
					GL11.glBegin(2);
					GL11.glVertex2d(-e.width, 0.0D);
					GL11.glVertex2d(-e.width, (e.height / 4.0F));
					GL11.glVertex2d(-e.width, 0.0D);
					GL11.glVertex2d((-e.width / 4.0F * 2.0F), 0.0D);
					GL11.glEnd();
					GL11.glBegin(2);
					GL11.glVertex2d(-e.width, e.height);
					GL11.glVertex2d((-e.width / 4.0F * 2.0F), e.height);
					GL11.glVertex2d(-e.width, e.height);
					GL11.glVertex2d(-e.width, (e.height / 2.5F * 2.0F));
					GL11.glEnd();
					GL11.glBegin(2);
					GL11.glVertex2d(e.width, e.height);
					GL11.glVertex2d((e.width / 4.0F * 2.0F), e.height);
					GL11.glVertex2d(e.width, e.height);
					GL11.glVertex2d(e.width, (e.height / 2.5F * 2.0F));
					GL11.glEnd();
					GL11.glBegin(2);
					GL11.glVertex2d(e.width, 0.0D);
					GL11.glVertex2d((e.width / 4.0F * 2.0F), 0.0D);
					GL11.glVertex2d(e.width, 0.0D);
					GL11.glVertex2d(e.width, (e.height / 4.0F));
					GL11.glEnd();
				}
				RenderUtil.release_new();
				GlStateManager.popMatrix();
			});
		}
	}

	public boolean rangeEntityCheck(Entity entity) {
		if (entity.getDistance(mc.player) > ((Esp) Main.moduleManager.getModule("Esp's")).range.getValue()) {
			return false;
		}

		if (entity.getDistance(mc.player) >= 180) {
			opacityGradient = 50;
		} else if (entity.getDistance(mc.player) >= 130 && entity.getDistance(mc.player) < 180) {
			opacityGradient = 100;
		} else if (entity.getDistance(mc.player) >= 80 && entity.getDistance(mc.player) < 130) {
			opacityGradient = 150;
		} else if (entity.getDistance(mc.player) >= 30 && entity.getDistance(mc.player) < 80) {
			opacityGradient = 200;
		} else {
			opacityGradient = 255;
		}

		return true;
	}

	public boolean rangeTileCheck(TileEntity tileEntity) {
		// the range value has to be squared for this
		if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY,
				mc.player.posZ) > ((Esp) Main.moduleManager.getModule("Esp's")).range.getValue()
						* ((Esp) Main.moduleManager.getModule("Esp's")).range.getValue()) {
			return false;
		}

		if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 32400) {
			opacityGradient = 50;
		} else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 16900
				&& tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 32400) {
			opacityGradient = 100;
		} else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 6400
				&& tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 16900) {
			opacityGradient = 150;
		} else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 900
				&& tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 6400) {
			opacityGradient = 200;
		} else {
			opacityGradient = 255;
		}

		return true;
	}
}