package mod.hitlerhax.module.modules.hud;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.IntSetting;

public class Hud extends Module {

	final BooleanSetting menu = new BooleanSetting("Menu", this, true);
	final BooleanSetting watermark = new BooleanSetting("Watermark", this, true);
	final BooleanSetting arraylist = new BooleanSetting("ArrayList", this, false);
	final BooleanSetting coords = new BooleanSetting("Coordinates", this, false);
	final BooleanSetting fps = new BooleanSetting("FPS", this, false);
	final BooleanSetting armor = new BooleanSetting("Armor", this, false);
	final BooleanSetting welcome = new BooleanSetting("Welcome", this, false);
	final BooleanSetting bps = new BooleanSetting("BPS", this, false);
	final BooleanSetting radar = new BooleanSetting("Radar", this, false);
	final IntSetting radarRadius = new IntSetting("Radar Radius", this, 100);
	final IntSetting xCoordOffset = new IntSetting("XCoord-offset", this, 0);
	final IntSetting zCoordOffset = new IntSetting("ZCoord-offset", this, 0);

	public Hud() {
		super("Hud", "In-Game Overlay", Category.HUD);

		addSetting(menu);
		addSetting(watermark);
		addSetting(arraylist);
		addSetting(coords);
		addSetting(fps);
		addSetting(armor);
		addSetting(welcome);
		addSetting(bps);
		addSetting(radar);
		addSetting(radarRadius);
		addSetting(xCoordOffset);
		addSetting(zCoordOffset);
	}
}
