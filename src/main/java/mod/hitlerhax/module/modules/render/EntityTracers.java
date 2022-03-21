package mod.hitlerhax.module.modules.render;

import java.awt.Color;

import mod.hitlerhax.event.events.HtlrEventRender;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.ColorSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.util.EntityUtil;
import mod.hitlerhax.util.MathUtil;
import mod.hitlerhax.util.render.ColorUtil;
import mod.hitlerhax.util.render.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class EntityTracers extends Module {

	IntSetting max = new IntSetting("Maximum Tracers", this, 50);
	final BooleanSetting monster = new BooleanSetting("Monsters", this, true);
	final BooleanSetting passive = new BooleanSetting("Passive Mobs", this, true);
	final BooleanSetting players = new BooleanSetting("Players", this, true);
	final BooleanSetting items = new BooleanSetting("Items", this, true);
	final BooleanSetting other = new BooleanSetting("Other Entities", this, true);
	final ColorSetting monsterColor = new ColorSetting("Monster Color", this, new ColorUtil(255, 255, 255, 255));
	final ColorSetting passiveColor = new ColorSetting("Passive Color", this, new ColorUtil(255, 255, 255, 255));
	final ColorSetting playerColor = new ColorSetting("Player Color", this, new ColorUtil(255, 255, 255, 255));
	final ColorSetting itemColor = new ColorSetting("Item Color", this, new ColorUtil(255, 255, 255, 255));
	final ColorSetting otherColor = new ColorSetting("Other Color", this, new ColorUtil(255, 255, 255, 255));
	final FloatSetting width = new FloatSetting("Tracer Width", this, 1f);

	public EntityTracers() {
		super("Tracers", "Traces a line to entities", Category.RENDER);

		addSetting(monster);
		addSetting(passive);
		addSetting(players);
		addSetting(items);
		addSetting(other);
		addSetting(monsterColor);
		addSetting(passiveColor);
		addSetting(playerColor);
		addSetting(itemColor);
		addSetting(otherColor);
		addSetting(width);
	}

	@Override
	public void render(HtlrEventRender event) {
		if (mc.world == null)
			return;

		for (Entity entity : mc.world.loadedEntityList) {
			if (checkShouldRenderTracers(entity)) {
				final Vec3d pos = MathUtil.interpolateEntity(entity, event.get_partial_ticks()).subtract(
						mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY,
						mc.getRenderManager().renderPosZ);

				if (pos != null) {
					final boolean bobbing = mc.gameSettings.viewBobbing;
					mc.gameSettings.viewBobbing = false;
					mc.entityRenderer.setupCameraTransform(event.get_partial_ticks(), 0);
					final Vec3d forward = new Vec3d(0, 0, 1)
							.rotatePitch(-(float) Math.toRadians(mc.player.rotationPitch))
							.rotateYaw(-(float) Math.toRadians(mc.player.rotationYaw));
					RenderUtil.drawLine3D((float) forward.x, (float) forward.y + mc.player.getEyeHeight(),
							(float) forward.z, (float) pos.x, (float) pos.y, (float) pos.z, width.value,
							getColor(entity));
					mc.gameSettings.viewBobbing = bobbing;
					mc.entityRenderer.setupCameraTransform(event.get_partial_ticks(), 0);
				}
			}
		}
	}

	private boolean checkShouldRenderTracers(Entity e) {
		if (e == mc.player)
			return false;
		if (e instanceof EntityPlayer) {
			return players.enabled;
		}
		if ((EntityUtil.isHostileMob(e) || EntityUtil.isNeutralMob(e))) {
			return monster.enabled;
		}
		if (EntityUtil.isPassive(e)) {
			return passive.enabled;
		}
		if (e instanceof EntityItem) {
			return items.enabled;
		}
		return other.enabled;
	}

	private int getColor(Entity e) {
		if (e instanceof EntityPlayer) {
			return new Color((float) playerColor.getColor().getRed() / 255,
					(float) playerColor.getColor().getGreen() / 255, (float) playerColor.getColor().getBlue() / 255,
					0.5f).getRGB();
		}
		if ((EntityUtil.isHostileMob(e) || EntityUtil.isNeutralMob(e))) {
			return new Color((float) monsterColor.getColor().getRed() / 255,
					(float) monsterColor.getColor().getGreen() / 255, (float) monsterColor.getColor().getBlue() / 255,
					0.5F).getRGB();
		}
		if (EntityUtil.isPassive(e)) {
			return new Color((float) passiveColor.getColor().getRed() / 255,
					(float) passiveColor.getColor().getGreen() / 255, (float) passiveColor.getColor().getBlue() / 255,
					0.5F).getRGB();
		}
		if (e instanceof EntityItem) {
			return new Color((float) itemColor.getColor().getRed() / 255, (float) itemColor.getColor().getGreen() / 255,
					(float) itemColor.getColor().getBlue() / 255, 0.5F).getRGB();
		}
		return new Color((float) otherColor.getColor().getRed() / 255, (float) otherColor.getColor().getGreen() / 255,
				(float) otherColor.getColor().getBlue() / 255, 0.5F).getRGB();
	}
}
