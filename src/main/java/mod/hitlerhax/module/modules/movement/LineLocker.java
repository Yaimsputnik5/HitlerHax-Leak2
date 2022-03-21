package mod.hitlerhax.module.modules.movement;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.FloatSetting;

public class LineLocker extends Module {
	public LineLocker() {
		super("LineLocker", "Locks the player on a line between 2 points", Category.MOVEMENT);
		addSetting(x1);
		addSetting(z1);
		addSetting(x2);
		addSetting(z2);
		addSetting(speed);
	}

	final FloatSetting x1 = new FloatSetting("X1", this, 0.0f);
	final FloatSetting z1 = new FloatSetting("Z1", this, 0.0f);
	final FloatSetting x2 = new FloatSetting("X2", this, 0.0f);
	final FloatSetting z2 = new FloatSetting("Z2", this, 0.0f);
	final FloatSetting speed = new FloatSetting("Speed", this, 1.0f);

	@Override
	public void onUpdate() {
		double dxc = mc.player.posX - x1.value;
		double dzc = mc.player.posZ - z1.value;

		double dxl = mc.player.posX - x2.value;
		double dzl = mc.player.posZ - z2.value;

		double cross = (int) (dxc * dzl - dzc * dxl);// int cast to give it 1 block of space to move

		if (cross != 0) {
			double[] nearest = new double[2];
			nearest[0] = nearestPointOnLine(x1.value, z1.value, x2.value, z2.value, mc.player.posX, mc.player.posZ)[0];
			nearest[1] = nearestPointOnLine(x1.value, z1.value, x2.value, z2.value, mc.player.posX, mc.player.posZ)[1];

			double distX = mc.player.posX - nearest[0];
			double distZ = mc.player.posZ - nearest[1];

			if (!Double.isNaN((speed.value / 100) * (distX / Math.max(Math.abs(distX), Math.abs(distZ))))) {
				mc.player.motionX = -(speed.value / 100) * (distX / Math.max(Math.abs(distX), Math.abs(distZ)));
				if (mc.player.isRiding() && mc.player.ridingEntity != null && distX + distZ < 3)
					mc.player.ridingEntity.setPosition(nearest[0], mc.player.ridingEntity.posY, nearest[1]);
			}
			if (!Double.isNaN((speed.value / 100) * (distZ / Math.max(Math.abs(distX), Math.abs(distZ))))) {
				mc.player.motionZ = -(speed.value / 100) * (distZ / Math.max(Math.abs(distX), Math.abs(distZ)));
				if (mc.player.isRiding() && mc.player.ridingEntity != null && distX + distZ < 3)
					mc.player.ridingEntity.setPosition(nearest[0], mc.player.ridingEntity.posY, nearest[1]);
			}
		}
	}

	public static double[] nearestPointOnLine(double ax, double ay, double bx, double by, double px, double py) {
		double apx = px - ax;
		double apy = py - ay;
		double abx = bx - ax;
		double aby = by - ay;

		double ab2 = abx * abx + aby * aby;
		double ap_ab = apx * abx + apy * aby;
		double t = ap_ab / ab2;

		return new double[] { ax + abx * t, ay + aby * t };
	}
}
