package mod.hitlerhax.event;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.event.events.HtlrEventPacket;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class HtlrEventHandler implements Listenable {
	public static HtlrEventHandler INSTANCE;

	static final float[] ticks = new float[20];

	private long last_update_tick;
	private int next_index = 0;

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> receive_event_packet = new Listener<>(event -> {
		if (event.get_packet() instanceof SPacketTimeUpdate) {
			INSTANCE.update_time();
		}
	});

	public HtlrEventHandler() {
		HtlrEventBus.EVENT_BUS.subscribe(this);

		reset_tick();
	}

	public void reset_tick() {
		this.next_index = 0;
		this.last_update_tick = -1L;

		Arrays.fill(ticks, 0.0f);
	}

	public void update_time() {
		if (this.last_update_tick != -1L) {
			float time = (float) (System.currentTimeMillis() - this.last_update_tick) / 1000.0f;
			ticks[(this.next_index % ticks.length)] = MathHelper.clamp(20.0f / time, 0.0f, 20.0f);

			this.next_index += 1;
		}

		this.last_update_tick = System.currentTimeMillis();
	}
}