package mod.hitlerhax.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import mod.hitlerhax.Main;

public class HtlrDiscordRichPresence {

	private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
	private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;

	public static void start(String mode) {
		DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
		eventHandlers.disconnected = ((var1, var2) -> System.out
				.println("Discord RPC disconnected, var1: " + var1 + ", var2: " + var2));

		if (mode.equalsIgnoreCase("HitlerHax")) {
			String discordID = "870753739500290060";
			discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);

			discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
			discordRichPresence.details = "by MaxRockatasky";
			discordRichPresence.largeImageKey = "dripler";
			discordRichPresence.largeImageText = "HitlerHax by MaxRockatasky";
			discordRichPresence.state = null;
		} else if (mode.equalsIgnoreCase("Vanilla")) {

			String discordID = "901118741436309505";
			discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);

			discordRichPresence.startTimestamp = Main.startTimeStamp / 1000L;
			discordRichPresence.details = null;
			discordRichPresence.largeImageKey = "icon";
			discordRichPresence.state = null;
		}

		discordRPC.Discord_UpdatePresence(discordRichPresence);
	}

	public static void stop() {
		discordRPC.Discord_Shutdown();
		discordRPC.Discord_ClearPresence();
	}
}
