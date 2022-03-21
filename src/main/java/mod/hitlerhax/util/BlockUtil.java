package mod.hitlerhax.util;

import mod.hitlerhax.misc.Rotation;
import mod.hitlerhax.misc.Rotation.RotationMode;
import mod.hitlerhax.misc.RotationPriority;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;

public class BlockUtil implements Globals {
	public static final List<Block> blackList;
	public static final List<Block> shulkerList;

	


	public static boolean isScaffoldPos(final BlockPos pos) {
		return BlockUtil.mc.world.isAirBlock(pos)
				|| BlockUtil.mc.world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER
				|| BlockUtil.mc.world.getBlockState(pos).getBlock() == Blocks.TALLGRASS
				|| BlockUtil.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid;
	}

	@SuppressWarnings("deprecation")
	public static boolean isValidBlock(final BlockPos pos) {
		final Block block = BlockUtil.mc.world.getBlockState(pos).getBlock();
		return !(block instanceof BlockLiquid) && block.getMaterial(null) != Material.AIR;
	}

	//BlockOverlay
	
	public static IBlockState getState(BlockPos pos)
	{
		return mc.world.getBlockState(pos);
	}
	
	public static Block getBlock(BlockPos pos)
	{
		return getState(pos).getBlock();
	}
	
	static{
		blackList = Arrays.asList(Blocks.ENDER_CHEST, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER);
		shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX,
				Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
	}
	
	 public static Block getBlock(double x, double y, double z) {
	        return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
	    }
	
	public static EnumFacing getPlaceableSide(BlockPos pos){

		for (EnumFacing side : EnumFacing.values()){

			BlockPos neighbour = pos.offset(side);

			if (!mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)){
				continue;
			}

			IBlockState blockState = mc.world.getBlockState(neighbour);
			if (!blockState.getMaterial().isReplaceable()){
				return side;
			}
		}

		return null;
	}
	
	public static boolean canBeClicked(BlockPos pos)
	{
		return !getBlock(pos).canCollideCheck(getState(pos), false);
	}
	public static void faceVectorPacketInstant(Vec3d vec){
		float[] rotations = getNeededRotations(vec);

		mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0],
				rotations[1], mc.player.onGround));
	}
	private static float[] getNeededRotations(Vec3d vec){
		Vec3d eyesPos = getEyesPos();

		double diffX = vec.x - eyesPos.x;
		double diffY = vec.y - eyesPos.y;
		double diffZ = vec.z - eyesPos.z;

		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

		float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

		return new float[]{
				mc.player.rotationYaw
						+ MathHelper.wrapDegrees(yaw - mc.player.rotationYaw),
				mc.player.rotationPitch + MathHelper
						.wrapDegrees(pitch - mc.player.rotationPitch)};
	}

	public static Vec3d getEyesPos(){
		return new Vec3d(mc.player.posX,
				mc.player.posY + mc.player.getEyeHeight(),
				mc.player.posZ);
	}

	 @SuppressWarnings("deprecation")
	    public static BlockResistance getBlockResistance(BlockPos block) {
	        if (mc.world.isAirBlock(block))
	            return BlockResistance.Blank;

	        else if (mc.world.getBlockState(block).getBlock().getBlockHardness(mc.world.getBlockState(block), mc.world, block) != -1 && !(mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) || mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) || mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) || mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST)))
	            return BlockResistance.Breakable;

	        else if (mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) || mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) || mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) || mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST))
	            return BlockResistance.Resistant;

	        else if (mc.world.getBlockState(block).getBlock().equals(Blocks.BEDROCK))
	            return BlockResistance.Unbreakable;

	        return null;
	 }
	 
	 public static void placeBlock(BlockPos pos, boolean rotate, boolean strict, boolean raytrace, boolean packet, boolean swingArm, boolean antiGlitch) {
	        for (EnumFacing enumFacing : EnumFacing.values()) {
	            if (!(getBlockResistance(pos.offset(enumFacing)) == BlockResistance.Blank) && !EntityUtil.isIntercepted(pos)) {
	                Vec3d vec = new Vec3d(pos.getX() + 0.5D + (double) enumFacing.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) enumFacing.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) enumFacing.getZOffset() * 0.5D);

	                float[] old = new float[] {
	                        mc.player.rotationYaw, mc.player.rotationPitch
	                };

	                if (strict)
	                    RotationUtil.rotationQueue.add(new Rotation((float) Math.toDegrees(Math.atan2((vec.z - mc.player.posZ), (vec.x - mc.player.posX))) - 90.0F, (float) (-Math.toDegrees(Math.atan2((vec.y - (mc.player.posY + (double) mc.player.getEyeHeight())), (Math.sqrt((vec.x - mc.player.posX) * (vec.x - mc.player.posX) + (vec.z - mc.player.posZ) * (vec.z - mc.player.posZ)))))), RotationMode.Packet, RotationPriority.High));

	                if (rotate)
	                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float) Math.toDegrees(Math.atan2((vec.z - mc.player.posZ), (vec.x - mc.player.posX))) - 90.0F, (float) (-Math.toDegrees(Math.atan2((vec.y - (mc.player.posY + (double) mc.player.getEyeHeight())), (Math.sqrt((vec.x - mc.player.posX) * (vec.x - mc.player.posX) + (vec.z - mc.player.posZ) * (vec.z - mc.player.posZ)))))), mc.player.onGround));

	                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));

	                if (packet)
	                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, raytrace ? enumFacing : EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
	                else
	                    mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(enumFacing), raytrace ? enumFacing.getOpposite() : EnumFacing.UP, new Vec3d(pos), EnumHand.MAIN_HAND);

	                if (swingArm)
	                    mc.player.swingArm(EnumHand.MAIN_HAND);

	                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));

	                if (rotate)
	                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(old[0], old[1], mc.player.onGround));

	                if (antiGlitch)
	                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos.offset(enumFacing), enumFacing.getOpposite()));

	                return;
	            }
	        }
	    }
	
	 public enum BlockResistance {
	        Blank,
	        Breakable,
	        Resistant,
	        Unbreakable
	    }
	
}
