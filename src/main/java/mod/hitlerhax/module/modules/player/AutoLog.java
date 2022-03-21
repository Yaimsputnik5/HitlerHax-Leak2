package mod.hitlerhax.module.modules.player;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.IntSetting;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.realms.RealmsBridge;

public class AutoLog extends Module {
	public AutoLog() {
		super("AutoLog", "Logs on Low Health", Category.PLAYER);
		addSetting(health);
	}

	final IntSetting health = new IntSetting("Health", this, 10);

	@Override
	public void onUpdate() {
		if (!mc.isSingleplayer() && mc.player.getHealth() < health.value) {
			boolean flag = mc.isIntegratedServerRunning();
			boolean flag1 = mc.isConnectedToRealms();
			mc.world.sendQuittingDisconnectingPacket();
			mc.loadWorld(null);

			if (flag) {
				mc.displayGuiScreen(new GuiMainMenu());
			} else if (flag1) {
				RealmsBridge realmsbridge = new RealmsBridge();
				realmsbridge.switchToRealms(new GuiMainMenu());
			} else {
				mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
			}
		}
	}
}
