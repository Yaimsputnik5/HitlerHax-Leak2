package mod.hitlerhax.ui.clickgui;

import java.util.ArrayList;
import java.util.Objects;

import org.lwjgl.opengl.GL11;

import mod.hitlerhax.Main;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.util.Globals;
import mod.hitlerhax.util.font.FontUtils;
import mod.hitlerhax.util.render.ColorUtil;

public class ClickGuiFrame implements Globals {
	boolean dragging = false;
	int draggingX;
	int draggingY;
	int prevX;
	int prevY;

	public int x;
	public int y;
	final int width;
	int height;

	public final Category category;

	final ArrayList<ModuleButton> moduleButtons;

	public ClickGuiFrame(Category category, int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 73;
		this.height = 0;
		this.category = category;

		moduleButtons = new ArrayList<>();
		int offsetY = 14;
		for (Module module : Main.moduleManager.getModulesByCategory(category)) {
			if (Objects.equals(module.name, "Esp2dHelper") || Objects.equals(module.name, "FootEXP"))
				continue;
			moduleButtons.add(new ModuleButton(module, x, y + offsetY, this));
			offsetY += 14;
		}

		this.height = offsetY;
	}

	public void render(int mouseX, int mouseY) {
		if (dragging) {
			this.x = mouseX - this.draggingX;
			this.y = mouseY - this.draggingY;
			for (ModuleButton b : moduleButtons) {
				b.x += this.x - prevX;
				b.y += this.y - prevY;
			}
			prevX = this.x;
			prevY = this.y;
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glLineWidth(1);

		GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, y + height);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x + width, y);
		GL11.glEnd();

		for (ModuleButton moduleButton : moduleButtons) {
			moduleButton.draw(mouseX, mouseY);
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		if (!dragging) {
			GL11.glColor4f(0.0f, 0.8f, 0.8f, 0.8f);
		} else {
			GL11.glColor4f(0.8f, 0.8f, 0.8f, 0.8f);
		}
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + width, y);
		GL11.glVertex2f(x + width, y + 5 + mc.fontRenderer.FONT_HEIGHT);
		GL11.glVertex2f(x, y + 5 + mc.fontRenderer.FONT_HEIGHT);
		GL11.glEnd();

		GL11.glColor3f(0.0f, 1.0f, 1.0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, y + height);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x + width, y);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glColor3f(1, 1, 1);

		ColorUtil categoryColor = new ColorUtil(255, 255, 255);

		FontUtils.drawString(category.toString(), x + 2, y + 2, categoryColor);
	}

	public void onClick(int x, int y, int button) {
		for (ModuleButton moduleButton : moduleButtons) {
			moduleButton.onClick(x, y, button);
		}
		if (x > this.x && x < this.x + this.width && y > this.y && y < this.y + 5 + mc.fontRenderer.FONT_HEIGHT) {
			this.dragging = !dragging;
			this.draggingX = x - this.x;
			this.draggingY = y - this.y;
			this.prevX = this.x;
			this.prevY = this.y;
		}
		Main.config.Save();
	}

	public void onGuiClosed() {
		if (this.dragging) {
			this.dragging = !dragging;
			this.draggingX = x - this.x;
			this.draggingY = y - this.y;
			this.prevX = this.x;
			this.prevY = this.y;
			Main.config.Save();
		}
	}
}
