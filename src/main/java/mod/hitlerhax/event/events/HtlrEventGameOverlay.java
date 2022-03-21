package mod.hitlerhax.event.events;

import mod.hitlerhax.event.HtlrEventCancellable;
import net.minecraft.client.gui.ScaledResolution;

public class HtlrEventGameOverlay extends HtlrEventCancellable {

	public final float partial_ticks;
	private final ScaledResolution scaled_resolution;

	public HtlrEventGameOverlay(float partial_ticks, ScaledResolution scaled_resolution) {

		this.partial_ticks = partial_ticks;
		this.scaled_resolution = scaled_resolution;

	}

	public ScaledResolution get_scaled_resoltion() {
		return scaled_resolution;
	}

}