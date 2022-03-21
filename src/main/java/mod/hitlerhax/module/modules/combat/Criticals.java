package mod.hitlerhax.module.modules.combat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.ModeSetting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class Criticals extends Module {

	public Criticals() {
		super("Criticals", "Makes normal hits critical hits", Category.COMBAT);

		this.addSetting(mode);
	}

	final ModeSetting mode = new ModeSetting("Mode", this, "NCPStrict", "NCPStrict", "Packet", "Jump");

	CPacketUseEntity packet;

	@EventHandler
	private final Listener<HtlrEventPacket.SendPacket> listener = new Listener<>(event -> {
		if (event.get_packet() instanceof CPacketUseEntity) {

			CPacketUseEntity packet = (CPacketUseEntity)event.get_packet();

            if (packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                if (packet.getEntityFromWorld(mc.world) instanceof EntityLivingBase && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {

                	if (mode.is("NCPStrict")) {
    					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,
    							mc.player.posY + 0.062602401692772D, mc.player.posZ, false));
    					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,
    							mc.player.posY + 0.0726023996066094D, mc.player.posZ, false));
    					mc.player.connection.sendPacket(
    							new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
    					
                	}
                	if(mode.is("Jump")) {
                		mc.player.jump();
    					mc.player.connection.sendPacket(
    							new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 0.05, mc.player.posZ, false));
    					mc.player.connection.sendPacket(
    							new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                	}

                	if(mode.is("Packet")) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                	}

                }
            }
		}

	});
}
