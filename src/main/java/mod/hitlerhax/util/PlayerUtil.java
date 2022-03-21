package mod.hitlerhax.util;

import net.minecraft.item.ItemFood;
import net.minecraft.util.math.Vec3d;

public class PlayerUtil implements Globals {

	public static boolean IsEating() {
		return mc.player != null && mc.player.getHeldItemMainhand().getItem() instanceof ItemFood
				&& mc.player.isHandActive();
	}

	public static Vec3d getCenter(double posX, double posY, double posZ) {
	        return new Vec3d(Math.floor(posX) + 0.5D, Math.floor(posY), Math.floor(posZ) + 0.5D);
	    }
	 
	 public static double getHealth() {
	        return mc.player.getHealth() + mc.player.getAbsorptionAmount();
	    }
	 
}