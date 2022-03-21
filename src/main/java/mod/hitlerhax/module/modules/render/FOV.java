package mod.hitlerhax.module.modules.render;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.IntSetting;

public class FOV extends Module {

	public FOV() {
		super("FOV", "Changes your players view", Category.RENDER);
		addSetting(fov);
	}

	final IntSetting fov = new IntSetting("Fov", this, 120);

	float fovOld;

	@Override
	public void onEnable() {
		fovOld = mc.gameSettings.fovSetting;
	}

	@Override
	public void onUpdate() {
		mc.gameSettings.fovSetting = (float) fov.getValue();
	}

	@Override
	public void onDisable() {
		mc.gameSettings.fovSetting = fovOld;
	}
}
