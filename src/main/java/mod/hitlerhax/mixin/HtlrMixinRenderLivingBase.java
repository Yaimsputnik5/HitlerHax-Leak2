package mod.hitlerhax.mixin;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.hitlerhax.Main;
import mod.hitlerhax.module.modules.render.Esp;
import mod.hitlerhax.util.render.ColorUtil;
import mod.hitlerhax.util.render.OutlineUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@Mixin(RenderLivingBase.class)
public abstract class HtlrMixinRenderLivingBase<T extends EntityLivingBase> extends HtlrMixinRender<T> {

	@Shadow
	protected ModelBase mainModel;

	// chams
	@Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At("HEAD"))
	private void injectChamsPre(final T a, final double b, final double c, final double d, final float e, final float f,
			final CallbackInfo g) {
		if (Main.moduleManager.getModule("Esp's") != null && Main.moduleManager.getModule("Esp's").isToggled()
				&& ((Esp) Main.moduleManager.getModule("Esp's")).chams.isEnabled()) {
			GL11.glEnable(32823);
			GL11.glPolygonOffset(1.0f, -1000000.0f);
		}
	}

	@Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At("RETURN"))
	private void injectChamsPost(final T a, final double b, final double c, final double d, final float e,
			final float f, final CallbackInfo g) {
		if (Main.moduleManager.getModule("Esp's") != null && Main.moduleManager.getModule("Esp's").isToggled()
				&& ((Esp) Main.moduleManager.getModule("Esp's")).chams.isEnabled()) {
			GL11.glPolygonOffset(1.0f, 1000000.0f);
			GL11.glDisable(32823);
		}
	}

	/**
	 * outline Esp's
	 */

	@Inject(method = "renderModel", at = @At("HEAD"))
	protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_,
			float p_77036_5_, float p_77036_6_, float scaleFactor, final CallbackInfo g) {
		// etc yea ok cool
		boolean flag = !entitylivingbaseIn.isInvisible();
		boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);

		if (flag || flag1) {
			if (!bindEntityTexture(entitylivingbaseIn)) {
				return;
			}

			if (flag1) {
				GlStateManager.pushMatrix();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
				GlStateManager.depthMask(false);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(770, 771);
				GlStateManager.alphaFunc(516, 0.003921569F);
			}

			if (Main.moduleManager.getModule("Esp's") != null && Main.moduleManager.getModule("Esp's").isToggled()) {
				if (entitylivingbaseIn instanceof EntityPlayer && entitylivingbaseIn != Minecraft.getMinecraft().player
						&& ((Esp) Main.moduleManager.getModule("Esp's")).entityMode.is("outline")) {
					Color n = new ColorUtil(((Esp) Main.moduleManager.getModule("Esp's")).playerColor.getColor());
					OutlineUtils.setColor(n);
					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_,
							scaleFactor);
					OutlineUtils.renderOne(((Esp) Main.moduleManager.getModule("Esp's")).lineWidth.getValue());
					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_,
							scaleFactor);
					OutlineUtils.renderTwo();
					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_,
							scaleFactor);
					OutlineUtils.renderThree();
					OutlineUtils.renderFour();
					OutlineUtils.setColor(n);
					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_,
							scaleFactor);
					OutlineUtils.renderFive();
					OutlineUtils.setColor(Color.WHITE);
				} else if (((Esp) Main.moduleManager.getModule("Esp's")).mob.isEnabled()
						&& ((Esp) Main.moduleManager.getModule("Esp's")).entityMode.is("outline")) {
					GL11.glLineWidth(5.0F);
					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_,
							scaleFactor);
					OutlineUtils.renderOne(((Esp) Main.moduleManager.getModule("Esp's")).lineWidth.getValue());
					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_,
							scaleFactor);
					OutlineUtils.renderTwo();
					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_,
							scaleFactor);
					OutlineUtils.renderThree();
					OutlineUtils.renderFour();
					mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_,
							scaleFactor);
					OutlineUtils.renderFive();
				}
			}

			this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_,
					scaleFactor);

			if (flag1) {
				GlStateManager.disableBlend();
				GlStateManager.alphaFunc(516, 0.1F);
				GlStateManager.popMatrix();
				GlStateManager.depthMask(true);
			}
		}
	}
}