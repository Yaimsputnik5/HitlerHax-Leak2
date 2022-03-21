package mod.hitlerhax.module.modules.combat;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.util.Timer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.util.EnumHand;

public class AutoTotem extends Module {

	private final IntSetting delay = new IntSetting("Delay", this, 1);
	private final BooleanSetting cancelMotion = new BooleanSetting("CancelMotion", this, false);

	public AutoTotem() {
		super("AutoTotem", "Puts totem in offhand", Category.COMBAT);
		addSetting(delay);
		addSetting(cancelMotion);
	}

	private final Timer timer = new Timer();

	private boolean hasTotem = false;

	@Override
	public void onUpdate() {
		if (!hasTotem) {
			timer.reset();
		}

		if (mc.player == null || mc.world == null)
			return;

		if (!(mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof GuiInventory)
				|| mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() == Items.TOTEM_OF_UNDYING
				|| mc.player.isCreative())) {
			int index = 44;
			while (index >= 9) {
				if (mc.player.inventoryContainer.getSlot(index).getStack().getItem() == Items.TOTEM_OF_UNDYING) {
					hasTotem = true;

					if (timer.getPassedTimeMs() >= (delay.getValue() * 100F)
							&& mc.player.inventory.getItemStack().getItem() != Items.TOTEM_OF_UNDYING) {
						if (cancelMotion.enabled
								&& mc.player.motionX * mc.player.motionX + mc.player.motionY * mc.player.motionY
										+ mc.player.motionZ * mc.player.motionZ >= 9.0E-4D) {
							mc.player.motionX = 0D;
							mc.player.motionY = 0D;
							mc.player.motionZ = 0D;
							return;
						}
						mc.playerController.windowClick(0, index, 0, ClickType.PICKUP, mc.player);
					}

					if (timer.getPassedTimeMs() >= (delay.getValue() * 200F)
							&& mc.player.inventory.getItemStack().getItem() == Items.TOTEM_OF_UNDYING) {
						if (cancelMotion.enabled
								&& mc.player.motionX * mc.player.motionX + mc.player.motionY * mc.player.motionY
										+ mc.player.motionZ * mc.player.motionZ >= 9.0E-4D) {
							mc.player.motionX = 0D;
							mc.player.motionY = 0D;
							mc.player.motionZ = 0D;
							return;
						}
						mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
						if (mc.player.inventory.getItemStack().isEmpty()) {
							hasTotem = false;
							return;
						}
					}

					if (timer.getPassedTimeMs() >= (delay.getValue() * 300F)
							&& !mc.player.inventory.getItemStack().isEmpty()
							&& mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() == Items.TOTEM_OF_UNDYING) {
						if (cancelMotion.enabled
								&& mc.player.motionX * mc.player.motionX + mc.player.motionY * mc.player.motionY
										+ mc.player.motionZ * mc.player.motionZ >= 9.0E-4D) {
							mc.player.motionX = 0D;
							mc.player.motionY = 0D;
							mc.player.motionZ = 0D;
							return;
						}
						mc.playerController.windowClick(0, index, 0, ClickType.PICKUP, mc.player);
						hasTotem = false;
						return;
					}
				}
				index--;
			}
		}
	}

	public void onEnable() {
		hasTotem = false;
	}
}
