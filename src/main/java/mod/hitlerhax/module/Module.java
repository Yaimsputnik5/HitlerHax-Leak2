package mod.hitlerhax.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zero.alpine.listener.Listenable;
import mod.hitlerhax.Main;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventRender;
import mod.hitlerhax.setting.Setting;
import mod.hitlerhax.ui.guiitems.HtlrButton;
import mod.hitlerhax.util.Globals;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Module implements Listenable, Globals {

	public final String name;
	public String description;
	public int key;

	private final Category category;
	public boolean toggled;
	protected boolean enabled;

	public final List<Setting> settings = new ArrayList<>();
	public final List<HtlrButton> buttons = new ArrayList<>();

	public Module(String name, String description, Category category) {
		super();
		this.name = name;
		this.description = description;
		this.key = 0;
		this.category = category;
		this.toggled = false;
		this.enabled = false;

	}

	public void addButton(HtlrButton... buttons) {
		this.buttons.addAll(Arrays.asList(buttons));
	}

	public void addSetting(Setting... settings) {
		this.settings.addAll(Arrays.asList(settings));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
		Main.config.Save();
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;

		if (this.toggled) {
			this.onEnable();
		} else {
			this.onDisable();
		}
	}

	public void toggle() {
		this.toggled = !this.toggled;

		if (this.toggled) {
			onEnable();
		} else {
			onDisable();
		}
	}

	protected void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);

		HtlrEventBus.EVENT_BUS.subscribe(this);

		Main.config.Save();
	}

	public void enable() {
		if (!this.isEnabled()) {
			this.enabled = true;
			try {
				this.onEnable();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void disable() {
		if (this.isEnabled()) {
			this.enabled = false;
			try {
				this.onDisable();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	protected void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);

		HtlrEventBus.EVENT_BUS.unsubscribe(this);

		Main.config.Save();
	}

	public String getName() {
		return this.name;
	}

	public Category getCategory() {
		return this.category;
	}

	public void onUpdate() {
	}

	public void render(HtlrEventRender event) {
		// 3d
	}

	public void render() {
		// 2d
	}

	public void onKeyPress() {
	}

	public void actionPerformed(HtlrButton b) {
	}

	public void onRenderWorldLast(HtlrEventRender event) {
	}

	public void onWorldRender(HtlrEventRender event) {

	}

	public void setup() {
	}

	public void onClientTick(TickEvent.ClientTickEvent event) {
	}

}