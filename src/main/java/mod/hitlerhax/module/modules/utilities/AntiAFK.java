package mod.hitlerhax.module.modules.utilities;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiAFK extends Module {
	public AntiAFK() {
		super("AntiAFK", "Prevents AFK Kick", Category.UTILITIES);

		addSetting(walk);
		addSetting(rotate);
		addSetting(punch);
		addSetting(punchTimer);
	}

	final BooleanSetting walk = new BooleanSetting("Walk", this, true);
	final IntSetting rotate = new IntSetting("Rotate", this, 1);
	final BooleanSetting punch = new BooleanSetting("Punch", this, true);
	final IntSetting punchTimer = new IntSetting("Punch Timer (s)", this, 30);

	long lastPunched = 0;

	@SubscribeEvent
	public void onUpdateInput(final InputUpdateEvent event) {
		if (walk.enabled)
			event.getMovementInput().moveForward = 1.0f;
		mc.player.rotationYaw += rotate.value;
		if (punch.enabled) {
			if (System.currentTimeMillis() - punchTimer.value * 1000L > lastPunched) {
				mc.player.swingArm(EnumHand.MAIN_HAND);
				lastPunched = System.currentTimeMillis();
			}
		}
	}
}
