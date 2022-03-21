package mod.hitlerhax;

import mod.hitlerhax.command.CommandManager;
import mod.hitlerhax.config.Config;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.HtlrEventHandler;
import mod.hitlerhax.event.HtlrEventManager;
import mod.hitlerhax.module.ModuleManager;
import mod.hitlerhax.module.modules.hud.*;
import mod.hitlerhax.setting.SettingManager;
import mod.hitlerhax.sound.SongManager;
import mod.hitlerhax.util.font.HtlrFontRenderer;
import mod.hitlerhax.util.render.ColorUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//main class, contains all event handlers etc.
@Mod(
	modid = Main.MODID,
	name = Main.NAME,
	version = Main.VERSION,
	acceptedMinecraftVersions = Main.MC_VERSIONS
)
@SideOnly(Side.CLIENT)
public class Main {

	public static final String MODID = "htlr";
	public static final String NAME = "HitlerHax";
	public static final String VERSION = "1.3.1";
	public static final String DEV_VERSION = "1.3.1";
	public static final String MC_VERSIONS = "[1.12.2]";
	public static final ColorUtil COLOR = new ColorUtil(121, 193, 255, 100);

	public static long startTimeStamp = 0;
	public static HtlrFontRenderer customFontRenderer;

	public static ModuleManager moduleManager;
	public static Config config;

	public static final HudCoords hudCoords = new HudCoords();
	public static final HudArrayList hudArrayList = new HudArrayList();
	public static final HudWatermark hudVersion = new HudWatermark();
	public static final HudFPS hudFps = new HudFPS();
	public static final HudBPS hudBps = new HudBPS();
	public static final HudArmor hudArmor = new HudArmor();
	public static final HudWelcome hudWelcome = new HudWelcome();
	public static final HudRadar hudRadar = new HudRadar();

	public static CommandManager cmdManager;
	public static SettingManager settingManager;
	public static HtlrEventManager eventManager;
	public static SongManager songManager;
	public static boolean configLoaded = false;

	@Instance
	public Main instance;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (event.getSide() == Side.SERVER)
			return;

		HtlrEventHandler.INSTANCE = new HtlrEventHandler();

		// register HUD
		MinecraftForge.EVENT_BUS.register(instance);
		MinecraftForge.EVENT_BUS.register(hudCoords);
		MinecraftForge.EVENT_BUS.register(hudArrayList);
		MinecraftForge.EVENT_BUS.register(hudVersion);
		MinecraftForge.EVENT_BUS.register(hudFps);
		MinecraftForge.EVENT_BUS.register(hudBps);
		MinecraftForge.EVENT_BUS.register(hudArmor);
		MinecraftForge.EVENT_BUS.register(hudWelcome);
		MinecraftForge.EVENT_BUS.register(hudRadar);

		moduleManager = new ModuleManager();
		cmdManager = new CommandManager();
		settingManager = new SettingManager();
		eventManager = new HtlrEventManager();
		songManager = new SongManager();
		config = new Config();
		config.Load();
		configLoaded = true;

		MinecraftForge.EVENT_BUS.register(eventManager);

		HtlrEventBus.EVENT_BUS.subscribe(eventManager);

		startTimeStamp = System.currentTimeMillis();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (event.getSide() == Side.SERVER)
			return;
		Main.moduleManager.getModule("ClientFont").toggle();
		Main.moduleManager.getModule("ClientFont").toggle();
	}

}
