package mod.hitlerhax.event.events;


import mod.hitlerhax.event.HtlrEventCancellable;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

public class HtlrEventEntity extends HtlrEventCancellable {
	
	
	
	private Entity entity;

	public HtlrEventEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity get_entity() {
		return this.entity;
	}
	 public void set_Entity(Entity entity) {
	        this.entity = entity;
	    }
	
	public HtlrEventEntity(Entity entityIn, ICamera camera, double camX, double camY, double camZ) {
        entity = entityIn;
    }

	public static class ScEventCollision extends HtlrEventEntity {
		private double x, y, z;

		public ScEventCollision(Entity entity, double x, double y, double z) {
			super(entity);

			this.x = x;
			this.y = y;
			this.z = z;
		}

		public void set_x(double x) {
			this.x = x;
		}

		public void set_y(double y) {
			this.y = y;
		}

		public void set_z(double z) {
			this.z = z;
		}

		public double get_x() {
			return this.x;
		}

		public double get_y() {
			return this.y;
		}

		public double get_z() {
			return this.z;
		}
	}
}