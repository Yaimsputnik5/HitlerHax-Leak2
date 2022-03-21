package mod.hitlerhax.module.modules.render;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.FloatSetting;
import net.minecraft.client.renderer.ItemRenderer;

public class LowOffHand extends Module {

	final FloatSetting height = new FloatSetting("Height", this, 0.7f);
	
	public LowOffHand() {
		super("LowOffHand", "Lowers your offhand", Category.RENDER);
		
		addSetting(height);
		
		
	}
	
	
	final ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;
	
	@Override 
	public void onUpdate() {
		itemRenderer.equippedProgressOffHand = height.getValue();
	}
}
