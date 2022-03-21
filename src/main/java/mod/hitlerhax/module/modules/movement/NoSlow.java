package mod.hitlerhax.module.modules.movement;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.event.HtlrEventCancellable.Era;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*
* made by hausemasterissue, 21/10/2021
*/

public class NoSlow extends Module {
	public NoSlow() {
		super("NoSlow", "Prevents slowdown effects", Category.MOVEMENT);

		this.addSetting(sneak);
	}

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> packetReceiveListener = new Listener<>(event -> {
		if (event.get_packet() instanceof CPacketPlayerTryUseItem && event.get_era() == Era.EVENT_POST) {
			Item item = mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem();
			if (item instanceof ItemFood || item instanceof ItemBow || item instanceof ItemPotion) {
				if (mc.networkManager != null) {
					mc.networkManager.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
				}
			}
		}
	});

	final BooleanSetting sneak = new BooleanSetting("AirStrict", this, true);

	private boolean sneaking = false;

	public void onDisable() {
		if (sneaking) {
			mc.player.connection
					.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
			sneaking = false;
		}
	}

	public void onUpdate() {
		if (!mc.player.isHandActive() && sneak.isEnabled() && sneaking) {
			mc.player.connection
					.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
			sneaking = false;
		}
	}

	@SubscribeEvent
	public void onUpdateInput(final InputUpdateEvent event) {
		if (mc.player.isHandActive() && !mc.player.isRiding() && !sneak.isEnabled()) {
			event.getMovementInput().moveForward *= 5.0f;
			event.getMovementInput().moveStrafe *= 5.0f;
		}
	}

	@SubscribeEvent
	public void onUseItem(LivingEntityUseItemEvent event) {
		if (!sneaking && sneak.isEnabled()) {
			mc.player.connection
					.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
			sneaking = true;
		}
	}

}
