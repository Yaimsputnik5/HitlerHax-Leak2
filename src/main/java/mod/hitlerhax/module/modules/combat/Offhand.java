package mod.hitlerhax.module.modules.combat;

import mod.hitlerhax.Main;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import mod.hitlerhax.setting.settings.ModeSetting;
import mod.hitlerhax.util.InventoryUtil;
import mod.hitlerhax.util.PlayerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import org.lwjgl.input.Mouse;

public class Offhand extends Module {
	public Offhand() {
		super("Offhand", "Switches items in the offhand to a totem when low on health", Category.COMBAT);
		addSetting(mode);
		addSetting(fallbackMode);
		addSetting(health);
		addSetting(checks);
		addSetting(swordGap);
		addSetting(forceGap);
		addSetting(hotbar);
	}

	final ModeSetting mode = new ModeSetting("Mode", this, "Crystal", "Crystal", "Gapple", "Bed", "Chorus", "Totem");
	final ModeSetting fallbackMode = new ModeSetting("Fallback", this, "Crystal", "Crystal", "Gapple", "Bed", "Chorus",
													 "Totem");
	final FloatSetting health = new FloatSetting("Health", this, 0.1f);

	final BooleanSetting checks = new BooleanSetting("Checks", this, true);
	final BooleanSetting caFunction = new BooleanSetting("ChecksAutoCrystal", this, false);
	final BooleanSetting elytraCheck = new BooleanSetting("ChecksElytra", this, false);
	final BooleanSetting fallCheck = new BooleanSetting("ChecksFalling", this, false);

	final BooleanSetting swordGap = new BooleanSetting("Sword Gapple", this, true);
	final BooleanSetting forceGap = new BooleanSetting("Force Gapple", this, false);
	final BooleanSetting hotbar = new BooleanSetting("Search Hotbar", this, false);

	@Override
	public void onUpdate() {
		if (mc.player == null || mc.world == null)
			return;

		Item searching = Items.TOTEM_OF_UNDYING;

		if (mc.player.isElytraFlying() && checks.isEnabled() && elytraCheck.isEnabled())
			return;

		if (mc.player.fallDistance > 5 && checks.isEnabled() && fallCheck.isEnabled())
			return;

		if (!Main.moduleManager.getModule("CrystalAura").isEnabled() && checks.isEnabled() && caFunction.isEnabled())
			return;

		switch (mode.getMode()) {
		case "Crystal":
			searching = Items.END_CRYSTAL;
			break;
		case "Gapple":
			searching = Items.GOLDEN_APPLE;
			break;
		case "Bed":
			searching = Items.BED;
			break;
		case "Chorus":
			searching = Items.CHORUS_FRUIT;
			break;
		}

		if (health.getValue() > PlayerUtil.getHealth())
			searching = Items.TOTEM_OF_UNDYING;

		else if (InventoryUtil.getHeldItem(Items.DIAMOND_SWORD) && swordGap.isEnabled())
			searching = Items.GOLDEN_APPLE;

		else if (forceGap.isEnabled() && Mouse.isButtonDown(1))
			searching = Items.GOLDEN_APPLE;

		if (mc.player.getHeldItemOffhand().getItem() == searching)
			return;

		if (mc.currentScreen != null)
			return;

		if (InventoryUtil.getInventoryItemSlot(searching, !hotbar.isEnabled()) != -1) {
			InventoryUtil.moveItemToOffhand(InventoryUtil.getInventoryItemSlot(searching, hotbar.isEnabled()));
			return;
		}

		switch (fallbackMode.getMode()) {
		case "Crystal":
			searching = Items.END_CRYSTAL;
			break;
		case "Gapple":
			searching = Items.GOLDEN_APPLE;
			break;
		case "Bed":
			searching = Items.BED;
			break;
		case "Chorus":
			searching = Items.CHORUS_FRUIT;
			break;
		case "Totem":
			searching = Items.TOTEM_OF_UNDYING;
			break;
		}

		if (mc.player.getHeldItemOffhand().getItem() == searching)
			return;

		if (InventoryUtil.getInventoryItemSlot(searching, !hotbar.isEnabled()) != -1)
			InventoryUtil.moveItemToOffhand(InventoryUtil.getInventoryItemSlot(searching, hotbar.isEnabled()));
	}
}