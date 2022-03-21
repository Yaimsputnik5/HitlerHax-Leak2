package mod.hitlerhax.module.modules.player;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Disabler extends Module {
	public Disabler() {
		super("Disabler", "Prevent AntiCheat flags", Category.PLAYER);
	}

	@SubscribeEvent
	public void onUpdateInput(final InputUpdateEvent event) {
		if (!mc.player.isElytraFlying() && !mc.player.isRidingHorse() && !mc.player.rowingBoat) {
			mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, GetLocalPlayerPosFloored(), EnumFacing.DOWN));
		}
	}

	private static BlockPos GetLocalPlayerPosFloored() {
		return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
	}
}