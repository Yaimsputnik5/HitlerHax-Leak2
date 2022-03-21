package mod.hitlerhax.ui.clickgui.settingeditor;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;

import mod.hitlerhax.misc.StringParser;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.Setting;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.ColorSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.setting.settings.ModeSetting;
import mod.hitlerhax.setting.settings.SearchBlockSelectorSetting;
import mod.hitlerhax.setting.settings.StringSetting;
import mod.hitlerhax.ui.clickgui.ClickGuiController;
import mod.hitlerhax.ui.clickgui.settingeditor.search.BlockSelectorGuiController;
import mod.hitlerhax.ui.guiitems.HtlrTextField;
import mod.hitlerhax.util.Globals;
import mod.hitlerhax.util.font.FontUtils;
import mod.hitlerhax.util.render.ColorUtil;
import net.minecraft.client.gui.GuiScreen;

public class SettingButton implements Globals {
	private HtlrTextField textField;

	// color setting only
	private HtlrTextField cTextFieldRed;
	private HtlrTextField cTextFieldGreen;
	private HtlrTextField cTextFieldBlue;

	final int x;
	int y;
	final int width;
	final int height;

	final Module module;

	final SettingFrame parent;

	final Setting setting;

	ModeSetting mSetting;
	IntSetting iSetting;
	FloatSetting fSetting;
	BooleanSetting bSetting;
	StringSetting sSetting;
	ColorSetting cSetting;
	SearchBlockSelectorSetting blockSetting;

	BlockSelectorGuiController blockController;

	public SettingButton(Module module, Setting setting, int x, int y, SettingFrame parent) {

		this.module = module;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.width = parent.width;
		this.height = 14;
		this.setting = setting;

		if (setting instanceof ModeSetting) {
			this.mSetting = (ModeSetting) setting;
		} else if (setting instanceof IntSetting) {
			this.iSetting = (IntSetting) setting;
		} else if (setting instanceof FloatSetting) {
			this.fSetting = (FloatSetting) setting;
		} else if (setting instanceof StringSetting) {
			this.sSetting = (StringSetting) setting;
		} else if (setting instanceof BooleanSetting) {
			this.bSetting = (BooleanSetting) setting;
		} else if (setting instanceof ColorSetting) {
			this.cSetting = (ColorSetting) setting;
		} else if (setting instanceof SearchBlockSelectorSetting) {
			this.blockSetting = (SearchBlockSelectorSetting) setting;
		}
	}

	public void draw(int mouseX, int mouseY) {

		for (Map.Entry<HtlrTextField, Module> entry : SettingController.textFields.entrySet()) {
			HtlrTextField t = entry.getKey();
			if (entry.getValue() == module) {
				if (t.height == FontUtils.getFontHeight() + 2
						&& t.width == 380 - FontUtils.getStringWidth(setting.name + ": ")
						&& t.x == x + 2 + FontUtils.getStringWidth(setting.name + ": ") && t.y == this.y) {
					textField = t;
				}
			}
		}

		for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : SettingController.cTextFields.entrySet()) {
			HtlrTextField[] t = entry.getKey();
			HtlrTextField r = t[0];
			HtlrTextField g = t[1];
			HtlrTextField b = t[2];
			if (entry.getValue().getKey() == module) {
				if (r.height == FontUtils.getFontHeight() + 2
						&& r.width == 380 - FontUtils.getStringWidth(setting.name + ": ")
						&& r.x == x + 2 + FontUtils.getStringWidth(setting.name + ": ")) {
					cTextFieldRed = r;
				}
				if (g.height == FontUtils.getFontHeight() + 2
						&& g.width == 380 - FontUtils.getStringWidth(setting.name + ": ")
						&& g.x == x + 2 + FontUtils.getStringWidth(setting.name + ": ")) {
					cTextFieldGreen = g;
				}
				if (b.height == FontUtils.getFontHeight() + 2
						&& b.width == 380 - FontUtils.getStringWidth(setting.name + ": ")
						&& b.x == x + 2 + FontUtils.getStringWidth(setting.name + ": ")) {
					cTextFieldBlue = b;
				}
			}
		}

		if (setting instanceof ModeSetting) {
			FontUtils.drawString(mSetting.name + ": " + mSetting.getMode(), x + 2, y + 2, new ColorUtil(255, 255, 255));
		} else if (setting instanceof IntSetting) {
			FontUtils.drawString(iSetting.name + ": ", x + 2, y + 2, new ColorUtil(255, 255, 255));
			int text = (int) FontUtils.drawString(Integer.toString(iSetting.value),
					x + 2 + FontUtils.getStringWidth(iSetting.name + ": "), y + 2, new ColorUtil(255, 255, 255));
			if (this.textField == null) {
				this.textField = new HtlrTextField(text, x + 2 + FontUtils.getStringWidth(iSetting.name + ": "), y,
						380 - FontUtils.getStringWidth(iSetting.name + ": "), FontUtils.getFontHeight() + 2);
				textField.setEnabled(true);
				SettingController.textFields.put(textField, module);
				textField.setText(Integer.toString(iSetting.value));
			}
			if (textField.isFocused()) {
				if (StringParser.isInteger(textField.getText())) {
					iSetting.value = Integer.parseInt(textField.getText());
					textField.setTextColor(new Color(0, 255, 0).getRGB());

				} else {
					textField.setTextColor(new Color(255, 0, 0).getRGB());
				}
			}
		} else if (setting instanceof FloatSetting) {
			FontUtils.drawString(fSetting.name + ": ", x + 2, y + 2, new ColorUtil(255, 255, 255));
			int text = (int) FontUtils.drawString(Float.toString(fSetting.value),
					x + 2 + FontUtils.getStringWidth(fSetting.name + ": "), y + 2, new ColorUtil(255, 255, 255));
			if (this.textField == null) {
				this.textField = new HtlrTextField(text, x + 2 + FontUtils.getStringWidth(fSetting.name + ": "), y,
						380 - FontUtils.getStringWidth(fSetting.name + ": "), FontUtils.getFontHeight() + 2);
				textField.setEnabled(true);
				SettingController.textFields.put(textField, module);
				textField.setText(Float.toString(fSetting.value));
			}
			if (textField.isFocused()) {
				if (StringParser.isFloat(textField.getText())) {
					fSetting.value = Float.parseFloat(textField.getText());
					textField.setTextColor(new Color(0, 255, 0).getRGB());
				} else {
					textField.setTextColor(new Color(255, 0, 0).getRGB());
				}
			}
		} else if (setting instanceof StringSetting) {
			FontUtils.drawString(sSetting.name + ": ", x + 2, y + 2, new ColorUtil(255, 255, 255));
			int text = (int) FontUtils.drawString(sSetting.value,
					x + 2 + FontUtils.getStringWidth(sSetting.name + ": "), y + 2, new ColorUtil(255, 255, 255));
			if (this.textField == null) {
				this.textField = new HtlrTextField(text, x + 2 + FontUtils.getStringWidth(sSetting.name + ": "), y,
						380 - FontUtils.getStringWidth(sSetting.name + ": "), FontUtils.getFontHeight() + 2);
				textField.setEnabled(true);
				SettingController.textFields.put(textField, module);
				textField.setText(sSetting.value);
			}
			if (textField.isFocused()) {
				sSetting.value = textField.getText();
				textField.setTextColor(new Color(0, 255, 0).getRGB());
			}
		} else if (setting instanceof BooleanSetting) {
			if (bSetting.enabled) {
				FontUtils.drawString(bSetting.name, x + 2, y + 2, new ColorUtil(255, 150, 50));
			} else {
				FontUtils.drawString(bSetting.name, x + 2, y + 2, new ColorUtil(180, 240, 255));
			}
		} else if (setting instanceof ColorSetting) {
			FontUtils.drawString(cSetting.name + ": ", x + 2, y + 2, new ColorUtil(cSetting.getColor().getRed(),
					cSetting.getColor().getGreen(), cSetting.getColor().getBlue()));
			int textRed = (int) FontUtils.drawString(Integer.toString(cSetting.getColor().getRed()),
					x + 2 + FontUtils.getStringWidth(cSetting.name + ": "), y + 2, new ColorUtil(255, 0, 0));
			int textGreen = (int) FontUtils.drawString(Integer.toString(cSetting.getColor().getGreen()),
					x + 2 + FontUtils.getStringWidth(cSetting.name + ": ") + 125, y + 2, new ColorUtil(0, 255, 0));
			int textBlue = (int) FontUtils.drawString(Integer.toString(cSetting.getColor().getBlue()),
					x + 2 + FontUtils.getStringWidth(cSetting.name + ": ") + 250, y + 2, new ColorUtil(0, 0, 255));
			if (cTextFieldRed == null && cTextFieldGreen == null && cTextFieldBlue == null) {
				cTextFieldRed = new HtlrTextField(textRed, x + 2 + FontUtils.getStringWidth(cSetting.name + ": "), y,
						50, FontUtils.getFontHeight() + 2);
				cTextFieldGreen = new HtlrTextField(textGreen,
						x + 2 + FontUtils.getStringWidth(cSetting.name + ": ") + 125, y, 50,
						FontUtils.getFontHeight() + 2);
				cTextFieldBlue = new HtlrTextField(textBlue,
						x + 2 + FontUtils.getStringWidth(cSetting.name + ": ") + 250, y, 50,
						FontUtils.getFontHeight() + 2);
				cTextFieldRed.setTextColor(new ColorUtil(255, 0, 0).getRGB());
				cTextFieldGreen.setTextColor(new ColorUtil(0, 255, 0).getRGB());
				cTextFieldBlue.setTextColor(new ColorUtil(0, 0, 255).getRGB());
				cTextFieldRed.setEnabled(true);
				cTextFieldGreen.setEnabled(true);
				cTextFieldBlue.setEnabled(true);
				HtlrTextField[] array = { cTextFieldRed, cTextFieldGreen, cTextFieldBlue };
				SettingController.cTextFields.put(array, new AbstractMap.SimpleEntry<>(module, setting));
				cTextFieldRed.setText(Integer.toString(cSetting.getColor().getRed()));
				cTextFieldGreen.setText(Integer.toString(cSetting.getColor().getGreen()));
				cTextFieldBlue.setText(Integer.toString(cSetting.getColor().getBlue()));

				if (cTextFieldRed.isFocused()) {
					if (StringParser.isInteger(cTextFieldRed.getText())
							&& Integer.parseInt(cTextFieldRed.getText()) <= 255
							&& Integer.parseInt(cTextFieldRed.getText()) >= 0)
						cSetting.setColor(new ColorUtil(Integer.parseInt(cTextFieldRed.getText()),
								cSetting.getColor().getGreen(), cSetting.getColor().getBlue()));
				}
				if (cTextFieldGreen.isFocused()) {
					if (StringParser.isInteger(cTextFieldGreen.getText())
							&& Integer.parseInt(cTextFieldGreen.getText()) <= 255
							&& Integer.parseInt(cTextFieldGreen.getText()) >= 0)
						cSetting.setColor(new ColorUtil(cSetting.getColor().getRed(),
								Integer.parseInt(cTextFieldGreen.getText()), cSetting.getColor().getBlue()));
				}
				if (cTextFieldBlue.isFocused()) {
					if (StringParser.isInteger(cTextFieldBlue.getText())
							&& Integer.parseInt(cTextFieldBlue.getText()) <= 255
							&& Integer.parseInt(cTextFieldBlue.getText()) >= 0)
						cSetting.setColor(new ColorUtil(cSetting.getColor().getRed(), cSetting.getColor().getGreen(),
								Integer.parseInt(cTextFieldBlue.getText())));
				}
			}
		} else if (setting instanceof SearchBlockSelectorSetting) {
			FontUtils.drawString(blockSetting.name, x + 2, y + 2, new ColorUtil(255, 255, 255));
		}
	}

	// FontUtils.drawString(module.getName(), x + 2, y + 2, new Color(255,
	// 255, 255));

	public void onClick(int x, int y, int button) {

		if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {

			if (setting instanceof ModeSetting) {
				mSetting.cycle();
				ClickGuiController.INSTANCE.settingController.refresh(false);
			} else if (setting instanceof BooleanSetting) {
				// we need to thin this down a bit, so it doesn't overlap with other buttons
				if (y >= this.y - 5 && y <= this.y + this.height - 5
						&& x <= this.x + FontUtils.getStringWidth(bSetting.name)) {
					bSetting.setEnabled(!bSetting.enabled);
				}
				ClickGuiController.INSTANCE.settingController.refresh(false);
			} else if (setting instanceof ColorSetting) {
				FontUtils.drawString(cSetting.name + ": ", x + 2, y + 2, new ColorUtil(cSetting.getColor().getRed(),
						cSetting.getColor().getGreen(), cSetting.getColor().getBlue()));
			} else if (setting instanceof SearchBlockSelectorSetting) {
				GuiScreen last = mc.currentScreen;
				assert mc.currentScreen != null;
				mc.currentScreen.onGuiClosed();
				this.blockController = new BlockSelectorGuiController(last, blockSetting.colorSettings, blockSetting);
				mc.displayGuiScreen(this.blockController);
			}
		} else {
			if (textField != null && textField.isFocused()) {
				this.textField.setTextColor(new Color(255, 255, 255).getRGB());
				this.textField.setFocused(false);
			}
			if (textField != null) {
				this.textField.setTextColor(new Color(255, 255, 255).getRGB());
				if (setting instanceof IntSetting) {
					this.textField.setText(Integer.toString(iSetting.value));
				} else if (setting instanceof FloatSetting) {
					this.textField.setText(Float.toString(fSetting.value));
				} else if (setting instanceof StringSetting) {
					this.textField.setText(sSetting.value);
				}
			}
		}

		if (cTextFieldRed != null && x <= cTextFieldRed.x && x >= cTextFieldRed.x + cTextFieldRed.width && y <= this.y
				&& y >= this.y + this.height) {
			cTextFieldRed.setFocused(false);
			cTextFieldRed.setText(Integer.toString(cSetting.getColor().getRed()));
		} else {
			for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : SettingController.cTextFields.entrySet()) {
				HtlrTextField r = entry.getKey()[0];
				HtlrTextField g = entry.getKey()[1];
				HtlrTextField b = entry.getKey()[2];

				if (r != this.cTextFieldRed) {
					r.setFocused(false);
				}
				g.setFocused(false);
				b.setFocused(false);
			}
		}

		if (cTextFieldGreen != null && x <= cTextFieldGreen.x && x >= cTextFieldGreen.x + cTextFieldGreen.width
				&& y <= this.y && y >= this.y + this.height) {
			cTextFieldGreen.setFocused(false);
			cTextFieldGreen.setText(Integer.toString(cSetting.getColor().getGreen()));
		} else {
			for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : SettingController.cTextFields.entrySet()) {
				HtlrTextField r = entry.getKey()[0];
				HtlrTextField g = entry.getKey()[1];
				HtlrTextField b = entry.getKey()[2];

				r.setFocused(false);
				if (g != this.cTextFieldGreen) {
					g.setFocused(false);
				}
				b.setFocused(false);
			}
		}

		if (cTextFieldBlue != null && x <= cTextFieldBlue.x && x >= cTextFieldBlue.x + cTextFieldBlue.width
				&& y <= this.y && y >= this.y + this.height) {
			cTextFieldBlue.setFocused(false);
			cTextFieldBlue.setText(Integer.toString(cSetting.getColor().getBlue()));
		} else {
			for (Entry<HtlrTextField[], Entry<Module, Setting>> entry : SettingController.cTextFields.entrySet()) {
				HtlrTextField r = entry.getKey()[0];
				HtlrTextField g = entry.getKey()[1];
				HtlrTextField b = entry.getKey()[2];

				r.setFocused(false);
				g.setFocused(false);
				if (b != this.cTextFieldBlue) {
					b.setFocused(false);
				}
			}
		}
		ClickGuiController.INSTANCE.settingController.refresh(false);
	}
}
