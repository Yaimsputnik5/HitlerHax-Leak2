package mod.hitlerhax.module.modules.utilities;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.IntSetting;

public class YawLock extends Module {
	public YawLock() {
		super("YawLock", "Locks yaw to a specific value.", Category.UTILITIES);
		addSetting(smooth);
		addSetting(rotation);
	}

	final BooleanSetting smooth = new BooleanSetting("Smooth Rotate", this, false);
	final IntSetting rotation = new IntSetting("Rotation", this, 0);

	@Override
	public void onUpdate() {
		if (smooth.enabled) {
			if ((int) (mc.player.rotationYaw % 360) - rotation.value > 0) {
				mc.player.rotationYaw--;
			} else if ((int) (mc.player.rotationYaw % 360) - rotation.value < 0) {
				mc.player.rotationYaw++;
			}
		} else {
			mc.player.rotationYaw = rotation.value;
		}
	}
}
