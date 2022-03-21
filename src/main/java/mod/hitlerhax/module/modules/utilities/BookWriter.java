package mod.hitlerhax.module.modules.utilities;

import mod.hitlerhax.Client;
import mod.hitlerhax.Main;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.setting.settings.StringSetting;
import mod.hitlerhax.util.UnicodeReader;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;

public class BookWriter extends Module {
	public BookWriter() {
		super("BookWriter", "Auto-writes books", Category.UTILITIES);
		addSetting(path);
		addSetting(delay);
		if (path.value == null)
			path.value = "C:\\example.txt";
	}

	public final StringSetting path = new StringSetting("TXT Path", this, "C:\\example.txt");
	final IntSetting delay = new IntSetting("Typing Delay (ms)", this, 100);

	public GuiButton reset;
	public GuiButton read;
	public GuiButton write;

	public void addButtons(int width) {
		reset = new GuiButton(69420, (width / 8) * 7, 10, width / 8 - 10, 20, "Reset");
		read = new GuiButton(69421, (width / 8) * 7, 40, width / 8 - 10, 20, "Read File");
		write = new GuiButton(69422, (width / 8) * 7, 70, width / 8 - 10, 20, "Write Page");
	}

	public String fileContents = "";
	public String written = "";

	public void actionPerformed(GuiButton button, NBTTagList bookPages, int currPage) {
		if (button.id == 69420) {
			// clear strings
			fileContents = "";
			written = "";
		} else if (button.id == 69421) {
			fileContents = readFile(((BookWriter) Main.moduleManager.getModule("BookWriter")).path.value);
		} else if (button.id == 69422) {
			for (int i = 0; i < fileContents.length(); i++) {
				String currentPage = bookPages != null && currPage >= 0 && currPage < bookPages.tagCount()
						? bookPages.getStringTagAt(currPage)
						: "";
				int height = mc.fontRenderer.getWordWrappedHeight(currentPage + i + "" + TextFormatting.BLACK + "_",
						118);
				if (height <= 110 && currentPage.length() < 244) {
					if (bookPages != null && currPage >= 0 && currPage < bookPages.tagCount()) {
						bookPages.set(currPage, new NBTTagString(currentPage + fileContents.charAt(0)));
						written += fileContents.charAt(0);
						fileContents = fileContents.substring(1);
					}
				} else {
					break;
				}
			}
		}
	}

	public void updateButtons(boolean bookIsUnsigned) {
		if (this.isToggled() && bookIsUnsigned) {
			if (reset != null)
				reset.visible = true;
			if (read != null)
				read.visible = true;
			if (write != null)
				write.visible = true;
		} else {
			if (reset != null)
				reset.visible = false;
			if (read != null)
				read.visible = false;
			if (write != null)
				write.visible = false;
		}
	}

	static long timer;

	private static String readFile(String filePath) {
		StringBuilder builder = new StringBuilder();

		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new UnicodeReader(new FileInputStream(filePath), "UTF-8"));

			String str;

			while ((str = buffer.readLine()) != null) {

				builder.append(str).append("\n");
			}
		} catch (IOException e) {
			if (System.currentTimeMillis() - timer > 500)
				Client.addChatMessage("Invalid File Path");
			timer = System.currentTimeMillis();
			e.printStackTrace();
			return "";
		}

		return builder.toString();
	}

	public void drawScreen(boolean bookIsUnsigned, int width, int height, int mouseX, int mouseY, float partialTicks) {
		if (this.isToggled() && bookIsUnsigned) {
			reset.drawButton(mc, mouseX, mouseY, partialTicks);
			read.drawButton(mc, mouseX, mouseY, partialTicks);
			write.drawButton(mc, mouseX, mouseY, partialTicks);
			mc.fontRenderer.drawSplitString(
					written.length() < 100 ? written : written.substring(written.length() - 100), 10,
					10, width / 8, new Color(0, 255, 0).getRGB());

			mc.fontRenderer.drawSplitString(fileContents.length() < 250 ? fileContents : fileContents.substring(0, 250),
					10,
					mc.fontRenderer.getWordWrappedHeight(written.length() < 100 ? written
							: written.substring(written.length() - 100), width / 8) + 20,
					width / 8, new Color(255, 0, 0).getRGB());
		}
	}
}
