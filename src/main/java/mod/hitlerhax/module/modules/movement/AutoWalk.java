package mod.hitlerhax.module.modules.movement;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoWalk extends Module {
	public AutoWalk() {
		super("AutoWalk", "Walk Automatically", Category.MOVEMENT);
	}

	@SubscribeEvent
	public void onUpdateInput(final InputUpdateEvent event) {
		event.getMovementInput().moveForward = 1.0f;
	}
}
