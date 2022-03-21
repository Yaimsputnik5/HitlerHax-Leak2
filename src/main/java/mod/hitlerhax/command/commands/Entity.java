package mod.hitlerhax.command.commands;

import mod.hitlerhax.Client;
import mod.hitlerhax.command.Command;
import net.minecraft.entity.passive.AbstractHorse;

public class Entity extends Command {
	@Override
	public String getAlias() {
		return "entity";
	}

	@Override
	public String getDescription() {
		return "interact with a set entity";
	}

	@Override
	public String getSyntax() {
		return ".entity setpointedentity | .entity clear | .entity inventory | .entity ride | .entity dismount | .entity setridingentity";
	}

	private net.minecraft.entity.Entity e;

	@Override
	public void onCommand(String command, String[] args) {
		if (args[0].isEmpty()) {
			Client.addChatMessage("No arguments found");
			Client.addChatMessage(this.getSyntax());
		} else {
			switch (args[0]) {
			case "setpointedentity":
				e = mc.pointedEntity;
				break;
			case "setridingentity":
				e = mc.player.ridingEntity;
				break;
			case "clear":
				e = null;
				break;
			case "inventory":
				if (e != null) {
					boolean sneakState = mc.player.isSneaking();
					mc.player.setSneaking(true);
					mc.player.openGuiHorseInventory((AbstractHorse) e,
							((AbstractHorse) e).horseChest);
					mc.player.setSneaking(sneakState);
				}
				break;
			case "ride":
				mc.player.startRiding(e);
				break;
			case "dismount":
				mc.player.dismountRidingEntity();
				break;
			default:
				Client.addChatMessage("Invalid Argument(s)");
				Client.addChatMessage(this.getSyntax());
				break;
			}
		}
	}
}
