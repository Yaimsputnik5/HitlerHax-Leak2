
package mod.hitlerhax.module.modules.render;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import net.minecraft.network.play.server.SPacketChunkData;

public class NewChunks extends Module {

	public NewChunks() {
		super("NoNewChunks", "Hides new chunks", Category.RENDER);

	}

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> PacketEvent = new Listener<>(p_Event -> {
		if (p_Event.get_packet() instanceof SPacketChunkData) {
			final SPacketChunkData c_Packet = (SPacketChunkData) p_Event.get_packet();
			if (!c_Packet.isFullChunk()) {
				p_Event.cancel();
			}
		}

	});
}
