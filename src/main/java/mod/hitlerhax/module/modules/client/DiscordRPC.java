package mod.hitlerhax.module.modules.client;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.ModeSetting;
import mod.hitlerhax.util.HtlrDiscordRichPresence;

public class DiscordRPC extends Module {

	public DiscordRPC() {
		super("DiscordRPC", "Rich Presence For Discord", Category.CLIENT);

		addSetting(mode);
	}

	public final ModeSetting mode = new ModeSetting("Mode", this, "HitlerHax", "Vanilla", "HitlerHax");

	@Override
	public void onEnable() {

		HtlrDiscordRichPresence.start(mode.getMode());

	}

	@Override
	public void onDisable() {
		HtlrDiscordRichPresence.stop();
	}
}
