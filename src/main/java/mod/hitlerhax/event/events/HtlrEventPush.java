package mod.hitlerhax.event.events;

import me.zero.alpine.event.type.Cancellable;
import net.minecraft.entity.Entity;

public class HtlrEventPush extends Cancellable {
	public Entity entity;
	public double x;
	public double y;
	public double z;
	public boolean airbone;

	public int stage;

	public HtlrEventPush(Entity entity, double x, double y, double z, boolean airbone) {
		super();
		this.entity = entity;
		this.x = x;
		this.y = y;
		this.z = z;
		this.airbone = airbone;
	}

	public HtlrEventPush(int stage, Entity entity) {
		this.entity = entity;
		this.stage = stage;
	}

	public HtlrEventPush(int stage) {
		this.stage = stage;
	}
}