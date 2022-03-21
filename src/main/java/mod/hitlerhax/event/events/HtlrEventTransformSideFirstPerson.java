package mod.hitlerhax.event.events;

import mod.hitlerhax.event.HtlrEventCancellable;
import net.minecraft.util.EnumHandSide;

public class HtlrEventTransformSideFirstPerson extends HtlrEventCancellable {

	private final EnumHandSide enumHandSide;

	public HtlrEventTransformSideFirstPerson(EnumHandSide enumHandSide){
		this.enumHandSide = enumHandSide;
	}

	public EnumHandSide getEnumHandSide(){
		return this.enumHandSide;
	}
}