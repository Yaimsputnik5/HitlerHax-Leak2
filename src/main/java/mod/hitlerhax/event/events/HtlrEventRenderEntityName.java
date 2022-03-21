package mod.hitlerhax.event.events;

import mod.hitlerhax.event.HtlrEventCancellable;
import net.minecraft.client.entity.AbstractClientPlayer;

public class HtlrEventRenderEntityName extends HtlrEventCancellable {
	public final AbstractClientPlayer Entity;
	public double X;
	public double Y;
	public double Z;
	public final String Name;
	public final double DistanceSq;

	public HtlrEventRenderEntityName(AbstractClientPlayer entityIn, double x, double y, double z, String name,
			double distanceSq) {
		super();

		Entity = entityIn;
		Name = name;
		DistanceSq = distanceSq;
	}

}