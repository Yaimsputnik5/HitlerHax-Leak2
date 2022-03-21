package mod.hitlerhax.event.events;

import mod.hitlerhax.event.HtlrEventCancellable;
import net.minecraft.network.Packet;

public class HtlrEventPacket extends HtlrEventCancellable {
	private final Packet<?> packet;

	public HtlrEventPacket(Packet<?> packet) {
		super();

		this.packet = packet;
	}

	public Packet<?> get_packet() {
		return this.packet;
	}

	public static class ReceivePacket extends HtlrEventPacket {
		public ReceivePacket(Packet<?> packet) {
			super(packet);
		}
	}

	public static class SendPacket extends HtlrEventPacket {
		public SendPacket(Packet<?> packet) {
			super(packet);
		}

		
	}
}