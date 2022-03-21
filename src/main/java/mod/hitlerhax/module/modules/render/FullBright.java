package mod.hitlerhax.module.modules.render;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;

public class FullBright extends Module {

	private float lastGamma;

	public FullBright() {
		super("FullBright", "Makes Everything Brighter", Category.RENDER);
	}

	@Override
	public void onEnable() {
		super.onEnable();

		this.lastGamma = mc.gameSettings.gammaSetting;
	}

	@Override
	public void onDisable() {
		super.onDisable();

		mc.gameSettings.gammaSetting = Math.min(lastGamma, 1.0f);
	}

	@Override
	public void onUpdate() {
		mc.gameSettings.gammaSetting = 1000;
	}
}
