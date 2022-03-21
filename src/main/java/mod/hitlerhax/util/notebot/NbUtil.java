package mod.hitlerhax.util.notebot;

import mod.hitlerhax.util.Globals;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class NbUtil implements Globals {

	public static float LongInterpolate(long val, long fmin, long fmax, float tmin, float tmax) {
		long fdist = fmax - fmin;
		float tdist = tmax - tmin;

		float scaled = (val - fmin) / (float) fdist;

		return tmin + (scaled * tdist);
	}

	public static void LookAt(double x, double y, double z) {
		EntityPlayerSP ply = mc.player;

		double px = ply.posX;
		double py = ply.posY;
		double pz = ply.posZ;

		double dirx = x - px;
		double diry = y - py;
		double dirz = z - pz;

		double dirlen = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

		dirx /= dirlen;
		diry /= dirlen;
		dirz /= dirlen;

		double pitch = Math.asin(diry);
		double yaw = Math.atan2(dirz, dirx);

		pitch = pitch * 180.0 / Math.PI;
		yaw = yaw * 180.0 / Math.PI;

		yaw -= 90f;

		ply.rotationPitch = -(float) pitch;
		ply.rotationYaw = (float) yaw;
	}

	public static void RightClick(BlockPos pos) {
		EntityPlayerSP ply = mc.player;
		// ItemStack item = ply.getHeldItemMainhand(); removed, item does NOT get used
		// && causing errors

		// Block block = mc.theWorld.getBlockState(pos).getBlock();

		TileEntity tile = mc.world.getTileEntity(pos);

		if (tile == null || tile.getBlockType().getLocalizedName().equals("tile.air"))
			return;

		LookAt(pos.getX() + 0.5f, pos.getY() - 1f, pos.getZ() + 0.5f);

		EnumFacing side = ply.getHorizontalFacing();

		if (mc.objectMouseOver != null)
			side = mc.objectMouseOver.sideHit;

		// onplayerrightclick
		ply.swingArm(EnumHand.MAIN_HAND);
		mc.playerController.processRightClickBlock(ply, mc.world, pos, side, mc.objectMouseOver.hitVec,
				EnumHand.MAIN_HAND);
	}

	public static void LeftClick(BlockPos pos) {
		EntityPlayerSP ply = mc.player;

		// Block block = mc.theWorld.getBlockState(pos).getBlock(); /this was previously
		// commented out
		TileEntity tile = mc.world.getTileEntity(pos);

		if (tile == null || tile.getBlockType().getLocalizedName().equals("tile.air"))
			return;

		LookAt(pos.getX() + 0.5f, pos.getY() - 1f, pos.getZ() + 0.5f);

		EnumFacing side = ply.getHorizontalFacing();

		if (mc.objectMouseOver != null)
			side = mc.objectMouseOver.sideHit;

		// clickblock
		ply.swingArm(EnumHand.MAIN_HAND);
		mc.playerController.clickBlock(pos, side);
	}

	public static String GetRealNote(int note) {
		int noter = note % 12;

		switch (noter) {
		case 0:
			return "F#";
		case 1:
			return "G";
		case 2:
			return "G#";
		case 3:
			return "A";
		case 4:
			return "A#";
		case 5:
			return "B";
		case 6:
			return "C";
		case 7:
			return "C#";
		case 8:
			return "D";
		case 9:
			return "D#";
		case 10:
			return "E";
		case 11:
			return "F";
		}

		return "null";
	}

}