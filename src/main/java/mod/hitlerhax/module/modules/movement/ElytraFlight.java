package mod.hitlerhax.module.modules.movement;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.FloatSetting;

public class ElytraFlight extends Module {

	final FloatSetting downSpeed = new FloatSetting("DownSpeed", this, 0.15f);
	final FloatSetting upSpeed = new FloatSetting("UpSpeed", this, 2.0f);
	final FloatSetting baseSpeed = new FloatSetting("BaseSpeed", this, 0.15f);
	final BooleanSetting noVelocity = new BooleanSetting("noVelocity", this, true);

	public ElytraFlight() {
		super("ElytraFlight", "Fly with Elytras", Category.MOVEMENT);

		this.addSetting(downSpeed);
		this.addSetting(upSpeed);
		this.addSetting(baseSpeed);
		this.addSetting(noVelocity);
	}

	@Override
	public void onUpdate() {

		if (!mc.player.isElytraFlying())
			return;

		float yaw = mc.player.rotationYaw;
		float pitch = mc.player.rotationPitch;

		if (mc.gameSettings.keyBindForward.isKeyDown()) {
			mc.player.motionX -= Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))
					* baseSpeed.getValue();
			mc.player.motionZ += Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))
					* baseSpeed.getValue();
		}
		if (mc.gameSettings.keyBindJump.isKeyDown()) {
			mc.player.motionY += upSpeed.getValue();
		}
		if (mc.gameSettings.keyBindSneak.isKeyDown()) {
			mc.player.motionY -= downSpeed.getValue();
		}

		if (noVelocity.isEnabled())
			if (!mc.gameSettings.keyBindForward.isKeyDown()
					&& !mc.gameSettings.keyBindJump.isKeyDown()
					&& !mc.gameSettings.keyBindSneak.isKeyDown()) {
				mc.player.setVelocity(0, 0, 0);
			}
	}
}
