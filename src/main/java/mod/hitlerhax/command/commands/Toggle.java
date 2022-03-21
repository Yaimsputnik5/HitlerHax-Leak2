package mod.hitlerhax.command.commands;

import mod.hitlerhax.Client;
import mod.hitlerhax.Main;
import mod.hitlerhax.command.Command;
import mod.hitlerhax.module.Module;

public class Toggle extends Command {

	@Override
	public String getAlias() {
		return "toggle";
	}

	@Override
	public String getDescription() {
		return "toggles modules";
	}

	@Override
	public String getSyntax() {
		return ".toggle [Module]";
	}

	@Override
	public void onCommand(String command, String[] args) {
		for (Module m : Main.moduleManager.getModuleList()) {
			if (args[0].equalsIgnoreCase(m.getName())) {
				m.toggle();
				Client.addChatMessage(args[0] + " has been toggled");
				return;
			}
		}
		Client.addChatMessage("Module " + args[0] + " was not found");
		Client.addChatMessage(this.getSyntax());
	}

}
