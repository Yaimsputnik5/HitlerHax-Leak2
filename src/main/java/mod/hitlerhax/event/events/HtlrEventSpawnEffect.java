package mod.hitlerhax.event.events;

import mod.hitlerhax.event.HtlrEventCancellable;

public class HtlrEventSpawnEffect extends HtlrEventCancellable {
	
	   private int particleID;

	    public HtlrEventSpawnEffect(int particleID) {
	        this.particleID = particleID;
	    }

	    public int getParticleID() {
	        return particleID;
	    }

	    public void setParticleID(int particleID) {
	        this.particleID = particleID;
	    }
	}