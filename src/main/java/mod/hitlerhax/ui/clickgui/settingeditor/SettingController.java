package mod.hitlerhax.ui.clickgui.settingeditor;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Mouse;

import mod.hitlerhax.Main;
import mod.hitlerhax.misc.StringParser;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.Setting;
import mod.hitlerhax.setting.settings.ColorSetting;
import mod.hitlerhax.ui.clickgui.ClickGuiController;
import mod.hitlerhax.ui.clickgui.ModuleButton;
import mod.hitlerhax.ui.guiitems.HtlrButton;
import mod.hitlerhax.ui.guiitems.HtlrTextField;
import mod.hitlerhax.util.render.ColorUtil;
import net.minecraft.client.gui.GuiScreen;

public class SettingController extends GuiScreen {

	private int scrollOffset = 0;

	public static final Map<HtlrTextField, Module> textFields = new HashMap<>();

	// color settings only
	public static final Map<HtlrTextField[], Entry<Module, Setting>> cTextFields = new HashMap<>();

	public SettingFrame frame;

	public Module module;

	public SettingController(int mouseX, int mouseY) {

		for (ModuleButton m : ClickGuiController.getButtons()) {
			if (mouseX >= m.x && mouseX <= m.x + m.width && mouseY >= m.y && mouseY <= m.y + m.height) {
				module = m.module;
			}
		}

		if (module != null)
			frame = new SettingFrame(module, 20, 20, new ColorUtil(255, 255, 255));
	}

	public void refresh(boolean kbClicked) {
		if (kbClicked) {
			frame = new SettingFrame(module, 20, 20, new ColorUtil(255, 0, 0));
		} else {
			frame = new SettingFrame(module, 20, 20, new ColorUtil(255, 255, 255));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		scroller();

		if (frame == null) {
			this.onGuiClosed();
			return;
		}

		frame.render(mouseX, mouseY);

		for (HtlrButton g : frame.module.buttons) {
			g.visible = true;
			g.enabled = true;
		}

		for (Map.Entry<HtlrTextField, Module> entry : textFields.entrySet()) {
			HtlrTextField textField = entry.getKey();
			if (entry.getValue() == frame.module)
				textField.drawTextBox();
		}
		for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : cTextFields.entrySet()) {
			HtlrTextField cTextFieldRed = entry.getKey()[0];
			HtlrTextField cTextFieldGreen = entry.getKey()[1];
			HtlrTextField cTextFieldBlue = entry.getKey()[2];

			if (entry.getValue().getKey() == frame.module) {
				cTextFieldRed.drawTextBox();
				cTextFieldGreen.drawTextBox();
				cTextFieldBlue.drawTextBox();
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		super.doesGuiPauseGame();
		return false;
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {

		if (frame == null)
			return;

		super.keyTyped(typedChar, keyCode);
		if (keyCode == 1) {

			for (HtlrButton g : frame.module.buttons) {
				g.visible = false;
				g.enabled = false;
			}

			for (Map.Entry<HtlrTextField, Module> entry : textFields.entrySet()) {
				HtlrTextField textField = entry.getKey();
				if (entry.getValue() == frame.module)
					textField.y -= scrollOffset;
			}
			frame.y -= scrollOffset;
			for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : cTextFields.entrySet()) {
				HtlrTextField cTextFieldRed = entry.getKey()[0];
				HtlrTextField cTextFieldGreen = entry.getKey()[1];
				HtlrTextField cTextFieldBlue = entry.getKey()[2];

				if (entry.getValue().getKey() == frame.module) {
					cTextFieldRed.y -= scrollOffset;
					cTextFieldGreen.y -= scrollOffset;
					cTextFieldBlue.y -= scrollOffset;
				}
			}

			frame.kbButton.y -= scrollOffset;

			for (SettingButton s : frame.settingButtons) {
				s.y -= scrollOffset;
			}

			scrollOffset = 0;

			for (HtlrTextField t : SettingController.textFields.keySet()) {
				t.setFocused(false);
				t.setTextColor(new Color(255, 255, 255).getRGB());
			}
			this.mc.displayGuiScreen(ClickGuiController.INSTANCE);

			if (this.mc.currentScreen == null) {
				this.mc.setIngameFocus();
			}
		}
		for (Map.Entry<HtlrTextField, Module> entry : textFields.entrySet()) {
			HtlrTextField textField = entry.getKey();
			if (entry.getValue() == frame.module)
				textField.textboxKeyTyped(typedChar, keyCode);
		}
		for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : cTextFields.entrySet()) {
			HtlrTextField cTextFieldRed = entry.getKey()[0];
			HtlrTextField cTextFieldGreen = entry.getKey()[1];
			HtlrTextField cTextFieldBlue = entry.getKey()[2];

			if (entry.getValue().getKey() == frame.module) {
				cTextFieldRed.textboxKeyTyped(typedChar, keyCode);
				cTextFieldGreen.textboxKeyTyped(typedChar, keyCode);
				cTextFieldBlue.textboxKeyTyped(typedChar, keyCode);
			}
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (frame == null)
			return;

		if (mouseButton == 0) {
			frame.onClick(mouseX, mouseY, mouseButton);

			for (HtlrButton g : frame.module.buttons) {
				g.y += scrollOffset;

				if (mouseX >= g.x && mouseX < g.x + g.width && mouseY >= g.y && mouseY < g.y + g.height)
					module.actionPerformed(g);
			}

			frame.y += scrollOffset;
			for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : cTextFields.entrySet()) {
				HtlrTextField cTextFieldRed = entry.getKey()[0];
				HtlrTextField cTextFieldGreen = entry.getKey()[1];
				HtlrTextField cTextFieldBlue = entry.getKey()[2];

				if (entry.getValue().getKey() == frame.module) {
					cTextFieldRed.y += scrollOffset;
					cTextFieldGreen.y += scrollOffset;
					cTextFieldBlue.y += scrollOffset;
				}
			}

			frame.kbButton.y += scrollOffset;

			for (SettingButton s : frame.settingButtons) {
				s.y += scrollOffset;
			}
		}

		for (Map.Entry<HtlrTextField, Module> entry : textFields.entrySet()) {
			HtlrTextField textField = entry.getKey();
			if (entry.getValue() == frame.module) {
				if (mouseX >= textField.x && mouseX < textField.x + textField.width && mouseY >= textField.y
						&& mouseY < textField.y + textField.height) {
					textField.setFocused(true);
				}
			}
			textField.mouseClicked(mouseX, mouseY, mouseButton);
		}
		for (Map.Entry<HtlrTextField[], Entry<Module, Setting>> entry : cTextFields.entrySet()) {
			HtlrTextField cTextFieldRed = entry.getKey()[0];
			HtlrTextField cTextFieldGreen = entry.getKey()[1];
			HtlrTextField cTextFieldBlue = entry.getKey()[2];

			ColorSetting cSetting = (ColorSetting) entry.getValue().getValue();

			if (!StringParser.isInteger(cTextFieldRed.getText()) || Integer.parseInt(cTextFieldRed.getText()) > 255
					|| Integer.parseInt(cTextFieldRed.getText()) < 0) {
				cTextFieldRed.setText(Integer.toString(cSetting.getColor().getRed()));
			} else {
				cSetting.setColor(new ColorUtil(Integer.parseInt(cTextFieldRed.getText()),
						cSetting.getColor().getGreen(), cSetting.getColor().getBlue()));
			}
			if (!StringParser.isInteger(cTextFieldGreen.getText()) || Integer.parseInt(cTextFieldGreen.getText()) > 255
					|| Integer.parseInt(cTextFieldGreen.getText()) < 0) {
				cTextFieldGreen.setText(Integer.toString(cSetting.getColor().getGreen()));
			} else {
				cSetting.setColor(new ColorUtil(cSetting.getColor().getRed(),
						Integer.parseInt(cTextFieldGreen.getText()), cSetting.getColor().getBlue()));
			}
			if (!StringParser.isInteger(cTextFieldBlue.getText()) || Integer.parseInt(cTextFieldBlue.getText()) > 255
					|| Integer.parseInt(cTextFieldBlue.getText()) < 0) {
				cTextFieldBlue.setText(Integer.toString(cSetting.getColor().getBlue()));
			} else {
				cSetting.setColor(new ColorUtil(cSetting.getColor().getRed(), cSetting.getColor().getGreen(),
						Integer.parseInt(cTextFieldBlue.getText())));
			}

			if (entry.getValue().getKey() == frame.module) {
				if (mouseX >= cTextFieldRed.x && mouseX < cTextFieldRed.x + cTextFieldRed.width
						&& mouseY >= cTextFieldRed.y && mouseY < cTextFieldRed.y + cTextFieldRed.height) {
					cTextFieldRed.setFocused(true);
				}
				if (mouseX >= cTextFieldGreen.x && mouseX < cTextFieldGreen.x + cTextFieldGreen.width
						&& mouseY >= cTextFieldGreen.y && mouseY < cTextFieldGreen.y + cTextFieldGreen.height) {
					cTextFieldGreen.setFocused(true);
				}
				if (mouseX >= cTextFieldBlue.x && mouseX < cTextFieldBlue.x + cTextFieldBlue.width
						&& mouseY >= cTextFieldBlue.y && mouseY < cTextFieldBlue.y + cTextFieldBlue.height) {
					cTextFieldBlue.setFocused(true);
				}
			}
		}
		Main.config.Save();
	}

	@Override
	public void updateScreen() {
		super.updateScreen();

		if (frame == null) {
			this.onGuiClosed();
			return;
		}

		for (Map.Entry<HtlrTextField, Module> entry : textFields.entrySet()) {
			HtlrTextField textField = entry.getKey();
			if (entry.getValue() == frame.module) {
				if (textField.isFocused())
					textField.updateCursorCounter();
			}
		}
		for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : cTextFields.entrySet()) {
			HtlrTextField cTextFieldRed = entry.getKey()[0];
			HtlrTextField cTextFieldGreen = entry.getKey()[1];
			HtlrTextField cTextFieldBlue = entry.getKey()[2];

			if (entry.getValue().getKey() == frame.module) {
				if (cTextFieldRed.isFocused())
					cTextFieldRed.updateCursorCounter();
				if (cTextFieldGreen.isFocused())
					cTextFieldGreen.updateCursorCounter();
				if (cTextFieldBlue.isFocused())
					cTextFieldBlue.updateCursorCounter();
			}
		}
	}

	private void scroller() {
		int dWheel = Mouse.getDWheel();
		if (dWheel < 0 && this.frame.y + this.frame.height > 0) {
			System.out.println("debug1");
			for (Map.Entry<HtlrTextField, Module> entry : textFields.entrySet()) {
				HtlrTextField textField = entry.getKey();
				if (entry.getValue() == frame.module)
					textField.y = textField.y - 10;
			}
			frame.y = frame.y - 10;
			for (SettingButton s : frame.settingButtons) {
				s.y -= 10;
			}
			frame.kbButton.y -= 10;
			for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : cTextFields.entrySet()) {
				HtlrTextField cTextFieldRed = entry.getKey()[0];
				HtlrTextField cTextFieldGreen = entry.getKey()[1];
				HtlrTextField cTextFieldBlue = entry.getKey()[2];

				if (entry.getValue().getKey() == frame.module) {
					cTextFieldRed.y -= 10;
					cTextFieldGreen.y -= 10;
					cTextFieldBlue.y -= 10;
				}
			}

			for (HtlrButton g : frame.module.buttons) {
				g.y -= 10;
			}

			scrollOffset -= 10;
		} else if (dWheel > 0 && this.frame.y <= this.height) {
			System.out.println("debug2");
			for (Map.Entry<HtlrTextField, Module> entry : textFields.entrySet()) {
				HtlrTextField textField = entry.getKey();
				if (entry.getValue() == frame.module)
					textField.y = textField.y + 10;
			}
			frame.y = frame.y + 10;
			for (SettingButton s : frame.settingButtons) {
				s.y += 10;
			}
			frame.kbButton.y += 10;
			for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : cTextFields.entrySet()) {
				HtlrTextField cTextFieldRed = entry.getKey()[0];
				HtlrTextField cTextFieldGreen = entry.getKey()[1];
				HtlrTextField cTextFieldBlue = entry.getKey()[2];

				if (entry.getValue().getKey() == frame.module) {
					cTextFieldRed.y = cTextFieldRed.y + 10;
					cTextFieldGreen.y = cTextFieldGreen.y + 10;
					cTextFieldBlue.y = cTextFieldBlue.y + 10;
				}
			}

			for (HtlrButton g : frame.module.buttons) {
				g.y += 10;
			}

			scrollOffset += 10;
		}
	}
}
