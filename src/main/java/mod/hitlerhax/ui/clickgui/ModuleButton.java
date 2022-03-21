package mod.hitlerhax.ui.clickgui;

import mod.hitlerhax.module.Module;
import mod.hitlerhax.util.font.FontUtils;
import mod.hitlerhax.util.render.ColorUtil;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModuleButton {
	public int x;
	public int y;
	public final int width;
	public final int height;

	public final Module module;

	final ClickGuiFrame parent;

	public ModuleButton(Module module, int x, int y, ClickGuiFrame parent) {
		this.module = module;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.width = parent.width;
		this.height = 14;
	}

	boolean increasing;

	public void draw(int mouseX, int mouseY) {
		if (module.toggled) {
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glBegin(GL11.GL_QUADS);
			Color c = ColorUtil.getRainbow(1000, 255);
			GL11.glColor4f((c.getRed()) / 255f, (c.getGreen()) / 255f, (c.getBlue()) / 255f, 0.6f);
			GL11.glVertex2f(this.x, this.y);
			GL11.glVertex2f(this.x + this.width, this.y);
			GL11.glVertex2f(this.x + this.width, this.y + this.height);
			GL11.glVertex2f(this.x, this.y + this.height);
			GL11.glEnd();

		} else {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glShadeModel(GL11.GL_SMOOTH);
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(1, 1, 1);
		GL11.glColor4f(1, 1, 1, 1);
		FontUtils.drawString(module.getName(), x + 2, y + 2, new ColorUtil(255, 255, 255));

	}

	public void onClick(int x, int y, int button) {
		if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
			module.toggle();
		}
	}
}
