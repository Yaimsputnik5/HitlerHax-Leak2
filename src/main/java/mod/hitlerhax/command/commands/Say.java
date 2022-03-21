package mod.hitlerhax.command.commands;

import mod.hitlerhax.Client;
import mod.hitlerhax.command.Command;

public class Say extends Command {
	@Override
	public String getAlias() {
		return "say";
	}

	@Override
	public String getDescription() {
		return "Say something in chat without triggering commands";
	}

	@Override
	public String getSyntax() {
		return ".say [message]";
	}

	@Override
	public void onCommand(String command, String[] args) {
		if (args[0].isEmpty()) {
			Client.addChatMessage("No arguments found");
			Client.addChatMessage(this.getSyntax());
		} else {
			StringBuilder msg = new StringBuilder();
			for (String arg : args) msg.append(arg).append(" ");
			mc.player.sendChatMessage(msg.toString());
		}
	}
}
