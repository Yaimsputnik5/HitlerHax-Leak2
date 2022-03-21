package mod.hitlerhax.module.modules.player;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class XCarry extends Module {

	public XCarry() {
		super("XCarry", "Keep items in your crafting slot", Category.PLAYER);
	}

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> PacketRecieveEvent = new Listener<>(event -> {

		if (event.get_packet() instanceof CPacketCloseWindow)
			event.cancel();

	});

	@EventHandler
	private final Listener<HtlrEventPacket.SendPacket> PacketSendEvent = new Listener<>(event -> {

		if (event.get_packet() instanceof CPacketCloseWindow)
			event.cancel();

	});
}
