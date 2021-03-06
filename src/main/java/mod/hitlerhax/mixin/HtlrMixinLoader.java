package mod.hitlerhax.mixin;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

public class HtlrMixinLoader implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Nullable
	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.hitlerhax.json");
		MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
