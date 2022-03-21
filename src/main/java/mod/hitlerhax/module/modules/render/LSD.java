package mod.hitlerhax.module.modules.render;

import mod.hitlerhax.Main;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class LSD extends Module {
	public LSD() {
		super("LSD", "Drugz", Category.RENDER);
	}

	@Override
	public void onEnable() {
		if (mc.world == null) {
			this.enabled = false;
			return;
		}
		mc.renderGlobal.loadRenderers();
		if (OpenGlHelper.shadersSupported)
			if (mc.getRenderViewEntity() instanceof EntityPlayer) {
				if (mc.entityRenderer.getShaderGroup() != null)
					mc.entityRenderer.getShaderGroup().deleteShaderGroup();

				mc.entityRenderer.shaderIndex = 19;

				if (mc.entityRenderer.shaderIndex != EntityRenderer.SHADER_COUNT)
					mc.entityRenderer.loadShader(EntityRenderer.SHADERS_TEXTURES[19]);
				else
					mc.entityRenderer.shaderGroup = null;
			}

		MinecraftForge.EVENT_BUS.register(this);

		HtlrEventBus.EVENT_BUS.subscribe(this);

		Main.config.Save();
	}

	@Override
	public void onDisable() {
		this.enabled = false;
		if (mc.entityRenderer.getShaderGroup() != null) {
			mc.entityRenderer.getShaderGroup().deleteShaderGroup();
			mc.entityRenderer.shaderGroup = null;
		}
		mc.gameSettings.smoothCamera = false;

		MinecraftForge.EVENT_BUS.unregister(this);

		HtlrEventBus.EVENT_BUS.unsubscribe(this);

		Main.config.Save();
	}

	@Override
	public void onUpdate() {
		System.out.println("a");
		if (mc.gameSettings.thirdPersonView != 0) {
			this.toggle();
		}
	}
}
