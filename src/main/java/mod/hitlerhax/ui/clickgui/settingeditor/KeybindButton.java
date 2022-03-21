package mod.hitlerhax.ui.clickgui.settingeditor;

import mod.hitlerhax.Client;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.ui.clickgui.ClickGuiController;
import mod.hitlerhax.util.font.FontUtils;
import mod.hitlerhax.util.render.ColorUtil;
import org.lwjgl.input.Keyboard;

public class KeybindButton {
	final int x;
	int y;
	final int width;
	final int height;

	final Module module;

	final SettingFrame parent;

	final ColorUtil color;

	public KeybindButton(Module module, int key, int x, int y, SettingFrame parent, ColorUtil c) {
		this.module = module;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.width = parent.width;
		this.height = 14;
		this.color = c;
	}

	public void draw(int mouseX, int mouseY) {
		FontUtils.drawString("Keybind: " + Keyboard.getKeyName(module.getKey()), x + 2, y + 2, color);

	}

	public void onClick(int x, int y, int button) {
		if (x >= this.x && x <= this.x + FontUtils.getStringWidth("Keybind: ") + 50 && y >= this.y - 5
				&& y <= this.y + this.height - 5) {
			if (!Client.getNextKeyPressForKeybinding) {
				Client.waitForKeybindKeypress(module);
				ClickGuiController.INSTANCE.settingController.refresh(true);
			} else {
				Client.stopWaitForKeybindPress();
				ClickGuiController.INSTANCE.settingController.refresh(false);
			}
		}
	}
}
