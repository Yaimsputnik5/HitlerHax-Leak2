package mod.hitlerhax.module.modules.movement;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.event.events.HtlrEventPush;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module {

	final FloatSetting horizontal = new FloatSetting("Horizontal", this, 0.0f);
	final FloatSetting vertical = new FloatSetting("Vertical", this, 0.0f);
	final BooleanSetting noPush = new BooleanSetting("No Push", this, true);

	public Velocity() {
		super("Velocity", "Stops Knockback", Category.MOVEMENT);
		addSetting(horizontal, vertical, noPush);
	}

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> PacketEvent = new Listener<>(p_Event -> {
		if (p_Event.get_packet() instanceof SPacketEntityVelocity) {
			final SPacketEntityVelocity packet = (SPacketEntityVelocity) p_Event.get_packet();
			if (packet.getEntityID() == mc.player.getEntityId()) {
				p_Event.cancel();
				return;
			}
		}
		if (p_Event.get_packet() instanceof SPacketExplosion) {
			p_Event.cancel();
		}
	});

	@EventHandler
	private final Listener<HtlrEventPush> PushEvent = new Listener<>(event -> {
		event.cancel();
	});

	public void onPush(HtlrEventPush event) {
		if (event.stage == 0 && this.noPush.isEnabled() && event.entity.equals(mc.player)) {
			event.cancel();
		}
	}
}
