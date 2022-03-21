package mod.hitlerhax.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.hitlerhax.Main;
import mod.hitlerhax.module.modules.utilities.BookWriter;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.nbt.NBTTagList;

@Mixin(GuiScreenBook.class)
public abstract class HtlrMixinGuiScreenBook extends GuiScreen {

	@Shadow
	private int currPage;

	@Shadow
	private NBTTagList bookPages;

	@Shadow
	@Final
	private boolean bookIsUnsigned;

	@Inject(method = "initGui", at = @At("RETURN"))
	public void onInitGui(CallbackInfo info) {
		if (this.bookIsUnsigned && Main.moduleManager.getModule("BookWriter").toggled) {

			((BookWriter) Main.moduleManager.getModule("BookWriter")).addButtons(this.width);
			this.addButton(((BookWriter) Main.moduleManager.getModule("BookWriter")).read);
			this.addButton(((BookWriter) Main.moduleManager.getModule("BookWriter")).reset);
			this.addButton(((BookWriter) Main.moduleManager.getModule("BookWriter")).write);
		}
	}

	@Inject(method = "actionPerformed", at = @At("RETURN"))
	protected void actionPerformed(GuiButton button, CallbackInfo info) {
		if (Main.moduleManager.getModule("BookWriter").toggled)
			((BookWriter) Main.moduleManager.getModule("BookWriter")).actionPerformed(button, this.bookPages,
					this.currPage);
	}

	@Inject(method = "drawScreen", at = @At("RETURN"))
	public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo info) {
		if (Main.moduleManager.getModule("BookWriter").toggled)
			((BookWriter) Main.moduleManager.getModule("BookWriter")).drawScreen(this.bookIsUnsigned, this.width,
					this.height, mouseX, mouseY, partialTicks);
	}

	@Inject(method = "updateButtons", at = @At("HEAD"))
	private void updateButtons(CallbackInfo info) {
		if (Main.moduleManager.getModule("BookWriter").toggled)
			((BookWriter) Main.moduleManager.getModule("BookWriter")).updateButtons(this.bookIsUnsigned);
	}

}
