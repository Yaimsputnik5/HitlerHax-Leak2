package mod.hitlerhax.module.modules.client;

import java.awt.Font;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.Main;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventSettings;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.ModeSetting;
import mod.hitlerhax.util.font.HtlrFontRenderer;
import net.minecraftforge.common.MinecraftForge;

public class ClientFont extends Module {
	public final ModeSetting font = new ModeSetting("font", this, "Comic Sans Ms", "Comic Sans Ms", "Arial", "Verdana");

	public ClientFont() {
		super("ClientFont", "changes the font the client uses.", Category.CLIENT);
		this.addSetting(font);
	}

	@Override
	public void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);

		HtlrEventBus.EVENT_BUS.subscribe(this);

		Main.config.Save();

		if (font.is("Comic Sans Ms")) {
			Main.customFontRenderer = new HtlrFontRenderer(new Font("Comic Sans MS", Font.PLAIN, 18), true, true);
		}

		if (font.is("Arial")) {
			Main.customFontRenderer = new HtlrFontRenderer(new Font("Arial", Font.PLAIN, 18), true, true);
		}

		if (font.is("Verdana")) {
			Main.customFontRenderer = new HtlrFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, true);
		}
	}

	@EventHandler
	private final Listener<HtlrEventSettings> settingChange = new Listener<>(event -> {
		if (event.module == this)
			this.onEnable();
	});
}