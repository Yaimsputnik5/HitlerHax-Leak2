package mod.hitlerhax.command.commands;

import mod.hitlerhax.Client;
import mod.hitlerhax.command.Command;
import net.minecraft.network.play.client.CPacketPlayer;

public class Clip extends Command {
	@Override
	public String getAlias() {
		return "clip";
	}

	@Override
	public String getDescription() {
		return "Sets the player position";
	}

	@Override
	public String getSyntax() {
		return ".clip [x] [y] [z]";
	}

	@Override
	public void onCommand(String command, String[] args) {
		if (args[0].isEmpty() || args[1].isEmpty() || args[2].isEmpty()) {
			Client.addChatMessage("No arguments found");
			Client.addChatMessage(this.getSyntax());
		} else {
			if (mc.player.ridingEntity == null) {
				mc.player.setPosition(
						mc.player.posX + Double.parseDouble(args[0]),
						mc.player.posY + Double.parseDouble(args[1]),
						mc.player.posZ + Double.parseDouble(args[2]));
			} else {
				mc.player.ridingEntity.setPosition(
						mc.player.ridingEntity.posX + Double.parseDouble(args[0]),
						mc.player.ridingEntity.posY + Double.parseDouble(args[1]),
						mc.player.ridingEntity.posZ + Double.parseDouble(args[2]));
			}
			mc.player.connection
					.sendPacket(new CPacketPlayer.Position(mc.player.posX,
							mc.player.posY, mc.player.posZ, false));
		}
	}
}
