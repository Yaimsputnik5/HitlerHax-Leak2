package mod.hitlerhax.command;

import mod.hitlerhax.Client;
import mod.hitlerhax.command.commands.*;

import java.util.ArrayList;

public class CommandManager {
	private final ArrayList<Command> commands;

	public CommandManager() {
		commands = new ArrayList<>();

		// add commands
		addCommand(new Bind());
		addCommand(new Toggle());
		addCommand(new Help());
		addCommand(new Rotate());
		addCommand(new Login());
		addCommand(new Clip());
		addCommand(new Say());
		addCommand(new Entity());
	}

	public void addCommand(Command c) {
		commands.add(c);
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}

	public void callCommand(String input) {

		String[] split = input.split(" ");
		String command = split[0];
		String args = input.substring(command.length()).trim();

		for (Command c : getCommands()) {
			if (c.getAlias().equalsIgnoreCase(command)) {
				try {
					c.onCommand(args, args.split(" "));
				} catch (Exception e) {
					Client.addChatMessage("Invalid command usage");
					Client.addChatMessage(c.getSyntax());
					e.printStackTrace(); //TODO fix .say
				}
				return;
			}
		}
		Client.addChatMessage("no such command exists");
	}
}
