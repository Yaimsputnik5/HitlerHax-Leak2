package mod.hitlerhax.module.modules.combat;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.setting.settings.ModeSetting;
import mod.hitlerhax.util.BlockUtil;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SelfFill extends Module {

	
	public final ModeSetting mode = new ModeSetting("mode", this, "instant", "instant", "jump", "tp");
	public final BooleanSetting autoSwitch = new BooleanSetting("autoSwitch", this, true);
	public final BooleanSetting rotations = new BooleanSetting("rotate", this, false);
	public final FloatSetting offset = new FloatSetting("offset", this, 4.0f);
	public final IntSetting rubberbandDelay = new IntSetting("delay", this, 13);
	public final BooleanSetting autoDisable = new BooleanSetting("autoDisable", this, true);
	
	private final double[] jump = {0.41999998688698D, 0.7531999805211997D, 1.00133597911214D, 1.16610926093821D};
	private boolean placed;
	private boolean jumped;
	private BlockPos startPos;
	private int ticks;
	private int startSlot;
	public SelfFill() {
		super("SelfFill", "Puts you in a hole", Category.COMBAT);
		
		
		addSetting(mode);
		addSetting(autoSwitch);
		addSetting(rotations);
		addSetting(offset);
		addSetting(rubberbandDelay);
		addSetting(autoDisable);

		
		
	}
	
	@Override
	public void onEnable() {
		startPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		startSlot = mc.player.inventory.currentItem;
		
		if (intersectsWithEntity(startPos) || findBlockSlot() == -1) {
            onDisable();
            return;
        }
		
		if (autoSwitch.isEnabled()) {
			mc.player.connection.sendPacket(new CPacketHeldItemChange(findBlockSlot()));
			mc.playerController.updateController();
		}
		
	}
	
	@Override 
	public void onDisable() {
		placed = false;
		jumped = false;
		ticks = 0;
	}
	
	
	@Override
	public void onUpdate() {
		if (mc.player == null || mc.world == null) return;
		if (!mode.is("instant")) {
			ticks++;
			if (!jumped) {
				mc.player.jump();
				jumped = true;
				if (ticks == rubberbandDelay.getValue() && !placed) {
					placeBlock(startPos, rotations.isEnabled(), false, true);
					placed = true;
					if(mode.is("jump")) mc.player.jump();
					else mc.player.motionY = offset.getValue();
					if (autoSwitch.isEnabled()) {
						mc.player.connection.sendPacket(new CPacketHeldItemChange(startSlot));
						mc.player.inventory.currentItem = startSlot;
						mc.playerController.updateController();
					}
					mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
					if(autoDisable.isEnabled()) this.setToggled(false);
				}
			}
		}
		else {
			for (int i = 0; i < 4; ++i) {
		        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + jump[i], mc.player.posZ, true));
			}
			
			placeBlock(startPos, rotations.isEnabled(), true, false);
			mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset.getValue(), mc.player.posZ, false));
			
			if (autoSwitch.isEnabled()) {
				mc.player.connection.sendPacket(new CPacketHeldItemChange(startSlot));
				mc.player.inventory.currentItem = startSlot;
				mc.playerController.updateController();
			}
			mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
			if(autoDisable.isEnabled()) this.setToggled(false);
		}
	}
	
	private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : mc.world.loadedEntityList) {
            if (entity.equals(mc.player)) continue;
            if (entity instanceof EntityItem) continue;
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) return true;
        }
        return false;
    }
	
    private
	void placeBlock(BlockPos pos, boolean rotate, boolean packet, boolean isSneaking) {
        Block block = mc.world.getBlockState(pos).getBlock();

        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return;
        }

        EnumFacing side = BlockUtil.getPlaceableSide(pos);

        if (side == null){
            return;
        }

        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();

        if (BlockUtil.canBeClicked(neighbour)) {
            return;
        }

        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();

        int obsidianSlot = findBlockSlot();

        if (mc.player.inventory.currentItem != obsidianSlot && obsidianSlot != -1) {

            mc.player.inventory.currentItem = obsidianSlot;
        }

        if (!isSneaking && BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock)) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            isSneaking = true;
        }

        if (rotate) {
            BlockUtil.faceVectorPacketInstant(hitVec);
        }

        rightClickBlock(neighbour, hitVec, EnumHand.MAIN_HAND, opposite, true);
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.rightClickDelayTimer = 4;

	}

    private int findBlockSlot() {
        int slot = -1;

        for (int i = 0; i < 9; i++){
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block instanceof BlockObsidian || block instanceof BlockEnderChest){
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public static void rightClickBlock(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet) {
        if (packet) {
            float f = (float) (vec.x - (double) pos.getX());
            float f1 = (float) (vec.y - (double) pos.getY());
            float f2 = (float) (vec.z - (double) pos.getZ());
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
        } else {
            mc.playerController.processRightClickBlock(mc.player, mc.world, pos, direction, vec, hand);
        }
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.rightClickDelayTimer = 4;
    }
    
}
