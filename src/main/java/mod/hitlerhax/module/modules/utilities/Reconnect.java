package mod.hitlerhax.module.modules.utilities;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.IntSetting;
import net.minecraft.client.multiplayer.ServerData;

public class Reconnect extends Module {

	public final IntSetting timer = new IntSetting("Timer", this, 5000);

	public ServerData serverData;

	public Reconnect() {
		super("Reconnect", "Reconnects You Automatically", Category.UTILITIES);

		addSetting(timer);
	}
}
