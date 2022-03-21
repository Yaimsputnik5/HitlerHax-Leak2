package mod.hitlerhax.event;

import me.zero.alpine.event.type.Cancellable;
import mod.hitlerhax.util.Globals;

public class HtlrEventCancellable extends Cancellable implements Globals {
    private boolean canceled;

	private final Era era_switch = Era.EVENT_PRE;
	private final float partial_ticks;

	public HtlrEventCancellable() {
		partial_ticks = mc.getRenderPartialTicks();
	}

	public Era get_era() {
		return era_switch;
	}

	public float get_partial_ticks() {
		return partial_ticks;
	}

    public HtlrEventCancellable(boolean canceled) {
        this.canceled = canceled;
		this.partial_ticks = 0;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

	public enum Era {
		EVENT_PRE, EVENT_POST
	}
}
