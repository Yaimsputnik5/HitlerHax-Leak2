package mod.hitlerhax.util;

import static java.lang.Double.isNaN;

public class BlockInteractionUtil implements Globals {

	public static float[] getRotationsForPosition(double x, double y, double z) {
		return getRotationsForPosition(x, y, z, mc.player.posX, mc.player.posY + mc.player.getEyeHeight(),
				mc.player.posZ);
	}

	public static float[] getRotationsForPosition(double x, double y, double z, double sourceX, double sourceY,
			double sourceZ) {
		double deltaX = x - sourceX;
		double deltaY = y - sourceY;
		double deltaZ = z - sourceZ;

		double yawToEntity;

		final double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
		if (deltaZ < 0 && deltaX < 0) { // quadrant 3
			yawToEntity = 90D + v; // 90
			// degrees
			// forward
		} else if (deltaZ < 0 && deltaX > 0) { // quadrant 4
			yawToEntity = -90D + v; // 90
			// degrees
			// back
		} else { // quadrants one or two
			yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
		}

		double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

		double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));

		yawToEntity = wrapAngleTo180((float) yawToEntity);
		pitchToEntity = wrapAngleTo180((float) pitchToEntity);

		yawToEntity = isNaN(yawToEntity) ? 0 : yawToEntity;
		pitchToEntity = isNaN(pitchToEntity) ? 0 : pitchToEntity;

		return new float[] { (float) yawToEntity, (float) pitchToEntity };
	}

	public static float wrapAngleTo180(float angle) {
		angle %= 360.0F;

		while (angle >= 180.0F) {
			angle -= 360.0F;
		}
		while (angle < -180.0F) {
			angle += 360.0F;
		}

		return angle;
	}

}
