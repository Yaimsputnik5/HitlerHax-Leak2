package mod.hitlerhax.module.modules.player;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.util.PlayerUtil;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class AutoEat extends Module {

	final IntSetting hunger = new IntSetting("Hunger", this, 17);
	final IntSetting health = new IntSetting("Health", this, 16);

	public AutoEat() {
		super("AutoEat", "Eats Automatically", Category.PLAYER);

		addSetting(hunger);
		addSetting(health);
	}

	private boolean m_WasEating = false;

	@Override
	public void onDisable() {
		super.onDisable();

		if (m_WasEating) {
			m_WasEating = false;
			mc.gameSettings.keyBindUseItem.pressed = false;
		}
	}

	@Override
	public void onUpdate() {
		if (hunger.value > 20) {
			hunger.value = 0;	
		}	

		if (!PlayerUtil.IsEating() && hunger.value >= mc.player.getFoodStats().getFoodLevel() || !PlayerUtil.IsEating() && health.value <= mc.player.getHealth()) {
			boolean l_CanEat = false;

			ItemStack l_Stack = null;

			for (int l_I = 0; l_I < 9; ++l_I) {
				l_Stack = mc.player.inventory.getStackInSlot(l_I);

				if (mc.player.inventory.getStackInSlot(l_I).isEmpty())
					continue;

				if (l_Stack.getItem() instanceof ItemFood) {
					l_CanEat = true;
					mc.player.inventory.currentItem = l_I;
					mc.playerController.updateController();
					break;
				}
			}

			if (l_CanEat && l_Stack.getItem() instanceof ItemFood) {
				mc.gameSettings.keyBindUseItem.pressed = true;
				mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
				mc.playerController.updateController();

				m_WasEating = true;
			}
		}

		if (m_WasEating) {
			m_WasEating = false;
		}
	}
}
