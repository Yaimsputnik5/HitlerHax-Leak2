package mod.hitlerhax.module.modules.utilities;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.ModeSetting;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

import static net.minecraft.network.play.client.CPacketEntityAction.Action.START_SPRINTING;
import static net.minecraft.network.play.client.CPacketEntityAction.Action.STOP_SPRINTING;

public class NoHunger extends Module {

	final ModeSetting mode = new ModeSetting("Mode", this, "Packet", "Packet", "Vanilla");

	public NoHunger() {
		super("NoHunger", "Prevents You From Losing Hunger", Category.UTILITIES);

		addSetting(mode);
	}

	@Override
	public void onUpdate() {
		if (mode.getMode().equals("Vanilla"))
			mc.player.getFoodStats().setFoodLevel(20);
	}

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> PacketEvent = new Listener<>(p_Event -> {
		if (mode.getMode().equals("Packet")) {
			if (p_Event.get_packet() instanceof CPacketPlayer && !mc.player.isElytraFlying()) {
				final CPacketPlayer l_Packet = (CPacketPlayer) p_Event.get_packet();
				l_Packet.onGround = mc.player.fallDistance > 0 || mc.playerController.isHittingBlock;
			}

			if (p_Event.get_packet() instanceof CPacketEntityAction) {
				final CPacketEntityAction l_Packet = (CPacketEntityAction) p_Event.get_packet();
				if (l_Packet.getAction() == START_SPRINTING || l_Packet.getAction() == STOP_SPRINTING) {
					p_Event.cancel();
				}
			}
		}
	});
}
