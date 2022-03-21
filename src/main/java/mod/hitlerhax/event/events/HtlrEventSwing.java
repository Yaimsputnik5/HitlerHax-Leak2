package mod.hitlerhax.event.events;

import mod.hitlerhax.event.HtlrEventCancellable;
import net.minecraft.util.EnumHand;

public class HtlrEventSwing extends HtlrEventCancellable {

	public final EnumHand hand;

	public HtlrEventSwing(EnumHand hand) {
		super();
		this.hand = hand;
	}

}