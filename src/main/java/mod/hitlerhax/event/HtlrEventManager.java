package mod.hitlerhax.event;

import me.zero.alpine.listener.Listenable;
import mod.hitlerhax.Client;
import mod.hitlerhax.Main;
import mod.hitlerhax.event.events.HtlrEventGameOverlay;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.module.modules.utilities.Reconnect;
import mod.hitlerhax.ui.clickgui.ClickGuiController;
import mod.hitlerhax.util.Globals;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class HtlrEventManager implements Listenable, Globals {

	@SubscribeEvent
	public void onUpdate(LivingEvent.LivingUpdateEvent event) {

	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.player == null) {
			return;
		}

		Main.moduleManager.update();
	}

	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if (event.isCanceled()) {
			return;
		}
		Main.moduleManager.render(event);
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Post event) {
		if (event.isCanceled()) {
			return;
		}

		HtlrEventBus.EVENT_BUS.post(new HtlrEventGameOverlay(event.getPartialTicks(), new ScaledResolution(mc)));
	}

	@SubscribeEvent
	public void onInputUpdate(InputUpdateEvent event) {
		HtlrEventBus.EVENT_BUS.post(event);
	}

	@SubscribeEvent
	public void key(KeyInputEvent k) {
		if (mc.world == null || mc.player == null)
			return;
		try {
			if (Keyboard.isCreated()) {
				if (Keyboard.getEventKeyState()) {
					int keyCode = Keyboard.getEventKey();
					if (keyCode <= 0)
						return;
					for (Module m : Main.moduleManager.modules) {
						if (m.toggled)
							m.onKeyPress();
						if (m.getKey() == keyCode && keyCode > 0) {
							m.toggle();
							return;
						}
					}
					if (keyCode == Keyboard.KEY_PERIOD) {
						mc.displayGuiScreen(new GuiChat("."));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void tickKeybind(TickEvent event) {
		if (Client.getNextKeyPressForKeybinding) {
			for (int i = 0; i < Keyboard.getKeyCount(); i++) {
				if (Keyboard.isKeyDown(i)) {
					Client.getNextKeyPressForKeybinding = false;
					Client.keybindModule.setKey(i);
					Client.keybindModule = null;
					ClickGuiController.INSTANCE.settingController.refresh(false);
					Main.config.Save();
					return;
				}
			}
		}
	}

	@SubscribeEvent
	public void onChatMessage(ClientChatEvent event) {
		String message = event.getMessage();
		if (message.startsWith(Client.getCommandPrefix())) {
			String command = message.substring(message.indexOf(Client.getCommandPrefix()) + 1);
			Client.addChatMessage("\247c" + message, false);
			Main.cmdManager.callCommand(command);
			event.setCanceled(true);
			mc.ingameGUI.getChatGUI().addToSentMessages(message);
		}
	}

	@SubscribeEvent
	public void sendPacket(GuiOpenEvent event) {
		if (event.getGui() instanceof GuiDisconnected) {
			ServerData data = mc.getCurrentServerData();
			if (data != null) {
				((Reconnect) Main.moduleManager.getModule("Reconnect")).serverData = data;
			}
		}
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		ServerData data = mc.getCurrentServerData();
		Module freecam = Main.moduleManager.getModule("freecam");
		if (freecam.isToggled())
			freecam.setToggled(false);
		if (data != null) {
			((Reconnect) Main.moduleManager.getModule("Reconnect")).serverData = data;
		}
	}
}
