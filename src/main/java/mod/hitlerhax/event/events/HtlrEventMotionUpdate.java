package mod.hitlerhax.event.events;

import mod.hitlerhax.event.HtlrEventCancellable;

public class HtlrEventMotionUpdate extends HtlrEventCancellable {

	public final int stage;

	public HtlrEventMotionUpdate(int stage) {
		super();
		this.stage = stage;
	}
	
	 public int getStage() {
	        return stage;
	    }


}