package mod.hitlerhax.module.modules.client;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.ui.clickgui.ClickGuiController;

public class ClickGui extends Module {

	public ClickGui() {
		super("ClickGUI", "GUI interface to interact with modules", Category.CLIENT);
	}

	@Override
	protected void onEnable() {
		mc.displayGuiScreen(ClickGuiController.INSTANCE);
		this.toggled = false;
	}

}
