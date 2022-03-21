package mod.hitlerhax.module.modules.combat;

import mod.hitlerhax.Main;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.setting.settings.ModeSetting;

//credit: srgantmoomoo and postman client

public class CrystalConfigHelper extends Module {

	public final BooleanSetting auto = new BooleanSetting("autoConfig", this, true);
	public final ModeSetting server = new ModeSetting("server", this, "2b2tpvp", "2b2tpvp", ".cc", "other");
	public final IntSetting ping = new IntSetting("averagePing", this, 40);
	public final BooleanSetting multiplace = new BooleanSetting("multiplace", this, false);

	public CrystalConfigHelper() {
		super("CrustalConfig", "Configs crystal aura based on your server", Category.COMBAT);

		addSetting(auto, server, ping, multiplace);
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onUpdate() {

		if (multiplace.isEnabled()) {
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).multiplace.setEnabled(true);

			if (ping.getValue() <= 1)
				((CrystalAura) Main.moduleManager.getModule("CrystalAura")).multiplacePlus.setEnabled(false);
			else if (ping.getValue() > 1)
				((CrystalAura) Main.moduleManager.getModule("CrystalAura")).multiplacePlus.setEnabled(true);

			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).highPing.setEnabled(false);
			return;
		}

		if (server.is("2b2tpvp")) {
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).rotate.setEnabled(true);
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).spoofRotations.setEnabled(true);
		}
		if (server.is(".cc")) {
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).rotate.setEnabled(false);
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).spoofRotations.setEnabled(false);
		}
		if (server.is("other")) {
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).rotate.setEnabled(false);
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).spoofRotations.setEnabled(false);
		}

		if (ping.getValue() <= 20) {
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).breakType.setMode("swing");
		} else if (ping.getValue() > 20) {
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).breakType.setMode("packet");
		}
		if (ping.getValue() <= 5) {
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).highPing.setEnabled(false);
		} else if (ping.getValue() > 5) {
			((CrystalAura) Main.moduleManager.getModule("CrystalAura")).highPing.setEnabled(true);
		}
	}

}
