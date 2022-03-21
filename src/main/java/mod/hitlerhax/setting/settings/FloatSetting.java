package mod.hitlerhax.setting.settings;

import mod.hitlerhax.Main;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventSettings;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.Setting;

public class FloatSetting extends Setting {
	public float value;

	public FloatSetting(String name, Module parent, Float value) {
		this.name = name;
		this.parent = parent;
		if (!Main.configLoaded)
			this.value = value;
	}

	public float getValue() {
		return this.value;
	}

	public void setValue(float value) {
		this.value = value;

		if (Main.config != null) {
			Main.config.Save();
		}
		HtlrEventBus.EVENT_BUS.post(new HtlrEventSettings(this, this.parent));
	}
}