package mod.hitlerhax.command;

import mod.hitlerhax.util.Globals;

public abstract class Command implements Globals {
	public abstract String getAlias();

	public abstract String getDescription();

	public abstract String getSyntax();

	public abstract void onCommand(String command, String[] args);
}
