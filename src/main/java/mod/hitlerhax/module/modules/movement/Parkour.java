package mod.hitlerhax.module.modules.movement;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Parkour extends Module {
	public Parkour() {
		super("Parkour", "Jump off the edge of blocks", Category.MOVEMENT);
	}

	@SubscribeEvent
	public void onUpdateInput(final InputUpdateEvent event) {
		if (mc.player.onGround && !mc.player.isSneaking()
				&& !mc.gameSettings.keyBindJump.isPressed()
				&& mc.world
				.getCollisionBoxes(mc.player, mc.player
						.getEntityBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001))
				.isEmpty()) {
			mc.player.jump();
		}
	}
}
