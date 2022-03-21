
package mod.hitlerhax.module.modules.player;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.Main;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.HtlrEventCancellable.Era;
import mod.hitlerhax.event.events.HtlrEventMotionUpdate;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.util.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;

public class Scaffold extends Module {

	final BooleanSetting refill = new BooleanSetting("refill", this, true);
	final BooleanSetting rotation = new BooleanSetting("Rotation", this, true);
	final BooleanSetting prerotate = new BooleanSetting("Pre-Rotate", this, true);
	private final Timer timer = new Timer();

	private final int[] blackList = new int[] { 145, 130, 12, 252, 54, 146, 122, 13, 219, 220, 221, 222, 223, 224, 225,
			226, 227, 228, 229, 230, 231, 232, 233, 234, 50 };

	public Scaffold() {
		super("Scaffold", "Places Blocks Below You", Category.PLAYER);

		addSetting(rotation);
		addSetting(refill);
		addSetting(prerotate);

	}

	@Override
	public void onEnable() {

		MinecraftForge.EVENT_BUS.register(this);

		HtlrEventBus.EVENT_BUS.subscribe(this);

		Main.config.Save();

		timer.reset();
	}

	@EventHandler
	private final Listener<HtlrEventMotionUpdate> player_move = new Listener<>(event -> {

		BlockPos playerBlock;
		if (mc.world == null || event.stage == 0) {
			return;
		}
		if (!mc.gameSettings.keyBindJump.isKeyDown()) {
			this.timer.reset();
		}
		if (BlockUtil.isScaffoldPos((playerBlock = EntityUtil.getPlayerPosWithEntity()).add(0, -1, 0))) {
			if (BlockUtil.isValidBlock(playerBlock.add(0, -2, 0))) {
				this.place(playerBlock.add(0, -1, 0), EnumFacing.UP, event);
			} else if (BlockUtil.isValidBlock(playerBlock.add(-1, -1, 0))) {
				this.place(playerBlock.add(0, -1, 0), EnumFacing.EAST, event);
			} else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 0))) {
				this.place(playerBlock.add(0, -1, 0), EnumFacing.WEST, event);
			} else if (BlockUtil.isValidBlock(playerBlock.add(0, -1, -1))) {
				this.place(playerBlock.add(0, -1, 0), EnumFacing.SOUTH, event);
			} else if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1))) {
				this.place(playerBlock.add(0, -1, 0), EnumFacing.NORTH, event);
			} else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 1))) {
				if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1))) {
					this.place(playerBlock.add(0, -1, 1), EnumFacing.NORTH, event);
				}
				this.place(playerBlock.add(1, -1, 1), EnumFacing.EAST, event);
			} else if (BlockUtil.isValidBlock(playerBlock.add(-1, -1, 1))) {
				if (BlockUtil.isValidBlock(playerBlock.add(-1, -1, 0))) {
					this.place(playerBlock.add(0, -1, 1), EnumFacing.WEST, event);
				}
				this.place(playerBlock.add(-1, -1, 1), EnumFacing.SOUTH, event);
			} else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 1))) {
				if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1))) {
					this.place(playerBlock.add(0, -1, 1), EnumFacing.SOUTH, event);
				}
				this.place(playerBlock.add(1, -1, 1), EnumFacing.WEST, event);
			} else if (BlockUtil.isValidBlock(playerBlock.add(1, -1, 1))) {
				if (BlockUtil.isValidBlock(playerBlock.add(0, -1, 1))) {
					this.place(playerBlock.add(0, -1, 1), EnumFacing.EAST, event);
				}
				this.place(playerBlock.add(1, -1, 1), EnumFacing.NORTH, event);
			}
		}
	});

	public void place(BlockPos posI, EnumFacing face, HtlrEventMotionUpdate event) {
		BlockPos pos = posI;
		if (face == EnumFacing.UP) {
			pos = pos.add(0, -1, 0);
		} else if (face == EnumFacing.NORTH) {
			pos = pos.add(0, 0, 1);
		} else if (face == EnumFacing.SOUTH) {
			pos = pos.add(0, 0, -1);
		} else if (face == EnumFacing.EAST) {
			pos = pos.add(-1, 0, 0);
		} else if (face == EnumFacing.WEST) {
			pos = pos.add(1, 0, 0);
		}
		if (event.get_era() == Era.EVENT_PRE) {
			if (this.rotation.enabled && this.prerotate.enabled) {
				float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()),
						new Vec3d((float) pos.getX() + 0.5f, (float) pos.getY() - 0.5f, (float) pos.getZ() + 0.5f));
				mc.player.connection.sendPacket(new CPacketPlayer.Rotation(angle[0],
						(float) MathHelper.normalizeAngle((int) angle[1], 360), mc.player.onGround));
			}
		}
		int oldSlot = mc.player.inventory.currentItem;
		int newSlot = -1;
		for (int i = 0; i < 9; ++i) {
			ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (InventoryUtil.isItemStackNull(stack) || !(stack.getItem() instanceof ItemBlock)
					|| !Block.getBlockFromItem(stack.getItem()).getDefaultState().isFullBlock())
				continue;
			newSlot = i;
			break;
		}
		if (newSlot == -1) {
			return;
		}
		boolean crouched = false;
		if (!mc.player.isSneaking()
				&& WorldUtil.RIGHTCLICKABLE_BLOCKS.contains(mc.world.getBlockState(pos).getBlock())) {
			mc.player.connection
					.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
			crouched = true;

		}
		if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {

			mc.player.connection.sendPacket(new CPacketHeldItemChange(newSlot));
			mc.player.inventory.currentItem = newSlot;
			mc.playerController.updateController();
		}
		if (mc.gameSettings.keyBindJump.isKeyDown()) {
			mc.player.motionX *= 0.3;
			mc.player.motionZ *= 0.3;
			mc.player.jump();
			if (this.timer.getPassedMillis(1500L)) {
				mc.player.motionY = -0.28;
				this.timer.reset();
			}
		}
		if (this.rotation.enabled && !this.prerotate.enabled) {
			float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()),
					new Vec3d((float) pos.getX() + 0.5f, (float) pos.getY() - 0.5f, (float) pos.getZ() + 0.5f));
			mc.player.connection.sendPacket(new CPacketPlayer.Rotation(angle[0],
					(float) MathHelper.normalizeAngle((int) angle[1], 360), mc.player.onGround));
		}
		mc.playerController.processRightClickBlock(mc.player, mc.world, pos, face, new Vec3d(0.5, 0.5, 0.5),
				EnumHand.MAIN_HAND);
		mc.player.swingArm(EnumHand.MAIN_HAND);
		mc.player.connection.sendPacket(new CPacketHeldItemChange(oldSlot));
		mc.player.inventory.currentItem = oldSlot;
		mc.playerController.updateController();
		final double[] dir = MathUtil.directionSpeed(1);

		if (crouched) {
			mc.player.connection
					.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
		} else {

			final Vec3d block = this.getFirstBlock(dir);

			if (this.refill.isEnabled() && block == null) {
				final int slot = this.findStackHotbar();
				if (slot != -1) {
					mc.player.inventory.currentItem = slot;
					mc.playerController.updateController();
				} else {
					final int invSlot = findStackInventory();
					if (invSlot != -1) {
						final int empty = findEmptyhotbar();
						mc.playerController.windowClick(mc.player.inventoryContainer.windowId, invSlot,
								empty == -1 ? mc.player.inventory.currentItem : empty, ClickType.SWAP, mc.player);
						mc.playerController.updateController();
						mc.player.setVelocity(0, 0, 0);
					}
				}
			}
		}
	}

	private int getBlockCount() {
		int count = 0;

		if (mc.player == null)
			return count;

		for (int i = 0; i < 36; i++) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (canPlace(stack) && stack.getItem() instanceof ItemBlock) {
				count += stack.getCount();
			}
		}

		return count;
	}

	private Vec3d getFirstBlock(double[] dir) {
		Vec3d pos = new Vec3d(mc.player.posX, mc.player.posY - 1, mc.player.posZ);
		Vec3d dirpos = new Vec3d(mc.player.posX + dir[0], mc.player.posY - 1, mc.player.posZ + dir[1]);
		if (mc.world.getBlockState(new BlockPos(pos.x, pos.y, pos.z)).getBlock() == Blocks.AIR)
			return pos;
		if (mc.world.getBlockState(new BlockPos(dirpos.x, dirpos.y, dirpos.z)).getBlock() == Blocks.AIR)
			if (mc.world.getBlockState(new BlockPos(pos.x, dirpos.y, dirpos.z)).getBlock() == Blocks.AIR
					&& mc.world.getBlockState(new BlockPos(dirpos.x, dirpos.y, pos.z)).getBlock() == Blocks.AIR) {
				return new Vec3d(dirpos.x, pos.y, pos.z);
			} else {
				return dirpos;
			}
		return null;
	}

	private int findEmptyhotbar() {
		for (int i = 0; i < 9; i++) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (stack.getItem() == Items.AIR) {
				return i;
			}
		}
		return -1;
	}

	private int findStackInventory() {
		for (int i = 9; i < 36; i++) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (canPlace(stack) && stack.getItem() instanceof ItemBlock) {
				return i;
			}
		}
		return -1;
	}

	private int findStackHotbar() {
		for (int i = 0; i < 9; i++) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (canPlace(stack) && stack.getItem() instanceof ItemBlock) {
				return i;
			}
		}
		return -1;
	}

	private boolean canPlace(ItemStack stack) {
		for (int i : this.blackList) {
			if (Item.getIdFromItem(stack.getItem()) == i) {
				return false;
			}
		}
		return true;
	}

}
