package mod.hitlerhax.event.events;

import mod.hitlerhax.event.HtlrEventCancellable;

public class HtlrEventRotation extends HtlrEventCancellable {
	
	  float yaw;
	    float pitch;
	    
	    public HtlrEventRotation() {}

	    public float getYaw() {
	        return yaw;
	    }

	    public float getPitch() {
	        return pitch;
	    }

	    public void setYaw(float yaw) {
	        this.yaw = yaw;
	    }

	    public void setPitch(float pitch) {
	        this.pitch = pitch;
	    }

	}
