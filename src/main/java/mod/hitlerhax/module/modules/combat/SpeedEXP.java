package mod.hitlerhax.module.modules.combat;

import mod.hitlerhax.Main;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.util.EntityUtil;
import mod.hitlerhax.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;

/*
WARNING: Shit Code - HausemasterIssue
*/

public class SpeedEXP extends Module {

	public SpeedEXP() {
		super("SpeedEXP", "Automatically switches to and throws EXP", Category.COMBAT);

		addSetting(delay, stopEXP, silent, feet);
	}

	final IntSetting delay = new IntSetting("ThrowDelay", this, 0);
	final BooleanSetting stopEXP = new BooleanSetting("StopEXP", this, false);
	final BooleanSetting silent = new BooleanSetting("SilentSwap", this, false);
	final BooleanSetting feet = new BooleanSetting("Feet", this, false);

	private int oldSlot = -1;

	@Override
	public void onUpdate() {
		if (mc.player == null || mc.world == null || mc.currentScreen != null) {
			return;
		}

		oldSlot = mc.player.inventory.currentItem;
		float oldPitch = mc.player.rotationPitch;

		if (!silent.isEnabled()) {
			InventoryUtil.switchToSlot(Items.EXPERIENCE_BOTTLE);
		} else {
			InventoryUtil.switchToSlotGhost(Items.EXPERIENCE_BOTTLE);
		}

		if (feet.isEnabled()) {
			mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90, mc.player.onGround));
		}

		mc.rightClickDelayTimer = delay.getValue();
		mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
		mc.player.connection.sendPacket(new CPacketHeldItemChange(oldSlot));

		if (stopEXP.isEnabled() && EntityUtil.getArmor(mc.player) == 100) {
			mc.player.stopActiveHand();
			onDisable();
			return;
		}

		if (feet.isEnabled()) {
			mc.player.connection
					.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, oldPitch, mc.player.onGround));
		}

	}

	@Override
	public void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);

		HtlrEventBus.EVENT_BUS.unsubscribe(this);

		Main.config.Save();

		if (mc.world == null)
			return;
		mc.player.connection.sendPacket(new CPacketHeldItemChange(oldSlot));
		mc.player.inventory.currentItem = oldSlot;
		mc.rightClickDelayTimer = 4;
	}
}
