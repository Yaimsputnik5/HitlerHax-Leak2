package mod.hitlerhax.mixin;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(GuiScreen.class)
public abstract class HtlrMixinGuiScreen extends Gui implements GuiYesNoCallback {
	@Shadow
	public List<GuiButton> buttonList;

	@Shadow
	public int width;

	@Shadow
	public int height;

	@Shadow
	public FontRenderer fontRenderer;

	@Shadow
	protected abstract <T extends GuiButton> T addButton(T buttonIn);

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
