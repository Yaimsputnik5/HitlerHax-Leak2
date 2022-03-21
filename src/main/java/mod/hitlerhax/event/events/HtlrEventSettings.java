package mod.hitlerhax.event.events;

import mod.hitlerhax.event.HtlrEventCancellable;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.Setting;

public class HtlrEventSettings extends HtlrEventCancellable {

	public Setting setting;
	public Module module;

	public HtlrEventSettings(Setting setting, Module module) {
		super();
		this.setting = setting;
		this.module = module;
	}
}
