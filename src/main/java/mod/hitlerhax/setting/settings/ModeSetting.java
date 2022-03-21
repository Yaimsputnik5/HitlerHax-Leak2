package mod.hitlerhax.setting.settings;

import java.util.Arrays;
import java.util.List;

import mod.hitlerhax.Main;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventSettings;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.Setting;

public class ModeSetting extends Setting {
	public int index;

	public final List<String> modes;

	public ModeSetting(String name, Module parent, String defaultMode, String... modes) {
		this.name = name;
		this.parent = parent;
		this.modes = Arrays.asList(modes);
		this.index = this.modes.indexOf(defaultMode);
	}

	public String getMode() {
		return this.modes.get(this.index);
	}

	public void setMode(String mode) {
		this.index = this.modes.indexOf(mode);

		if (Main.config != null) {
			Main.config.Save();
		}
		HtlrEventBus.EVENT_BUS.post(new HtlrEventSettings(this, this.parent));
	}

	public boolean is(String mode) {
		return (this.index == this.modes.indexOf(mode));
	}

	public void cycle() {
		if (this.index < this.modes.size() - 1) {
			this.index++;
		} else {
			this.index = 0;
		}

		if (Main.config != null) {
			Main.config.Save();
		}
		HtlrEventBus.EVENT_BUS.post(new HtlrEventSettings(this, this.parent));
	}

}