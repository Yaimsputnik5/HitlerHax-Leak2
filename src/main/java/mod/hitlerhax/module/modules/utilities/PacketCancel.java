package mod.hitlerhax.module.modules.utilities;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;

public class PacketCancel extends Module {
	public PacketCancel() {
		super("PacketCancel", "Cancels Packets", Category.UTILITIES);
		addSetting(cPacketAnimation);
		addSetting(cPacketChatMessage);
		addSetting(cPacketClickWindow);
		addSetting(cPacketClientSettings);
		addSetting(cPacketClientStatus);
		addSetting(cPacketCloseWindow);
		addSetting(cPacketConfirmTeleport);
		addSetting(cPacketConfirmTransaction);
		addSetting(cPacketCreativeInventoryAction);
		addSetting(cPacketCustomPayload);
		addSetting(cPacketEnchantItem);
		addSetting(cPacketEntityAction);
		addSetting(cPacketHeldItemChange);
		addSetting(cPacketInput);
		addSetting(cPacketKeepAlive);
		addSetting(cPacketPlaceRecipe);
		addSetting(cPacketPlayer);
		addSetting(cPacketPlayerPosition);
		addSetting(cPacketPlayerPositionRotation);
		addSetting(cPacketPlayerRotation);
		addSetting(cPacketPlayerAbilities);
		addSetting(cPacketPlayerDigging);
		addSetting(cPacketPlayerTryUseItem);
		addSetting(cPacketPlayerTryUseItemOnBlock);
		addSetting(cPacketRecipeInfo);
		addSetting(cPacketResourcePackStatus);
		addSetting(cPacketSeenAdvancements);
		addSetting(cPacketSpectate);
		addSetting(cPacketSteerBoat);
		addSetting(cPacketTabComplete);
		addSetting(cPacketUpdateSign);
		addSetting(cPacketUseEntity);
		addSetting(cPacketVehicleMove);
		addSetting(sPacketAdvancementInfo);
		addSetting(sPacketAnimation);
		addSetting(sPacketBlockAction);
		addSetting(sPacketBlockBreakAnim);
		addSetting(sPacketBlockChange);
		addSetting(sPacketCamera);
		addSetting(sPacketChangeGameState);
		addSetting(sPacketChat);
		addSetting(sPacketChunkData);
		addSetting(sPacketCloseWindow);
		addSetting(sPacketCollectItem);
		addSetting(sPacketCombatEvent);
		addSetting(sPacketConfirmTransaction);
		addSetting(sPacketCooldown);
		addSetting(sPacketCustomPayload);
		addSetting(sPacketCustomSound);
		addSetting(sPacketDestroyEntities);
		addSetting(sPacketDisconnect);
		addSetting(sPacketDisplayObjective);
		addSetting(sPacketEffect);
		addSetting(sPacketEntity);
		addSetting(sPacketEntityRelMove);
		addSetting(sPacketEntityLook);
		addSetting(sPacketEntityLookMove);
		addSetting(sPacketEntityAttach);
		addSetting(sPacketEntityEffect);
		addSetting(sPacketEntityEquipment);
		addSetting(sPacketEntityHeadLook);
		addSetting(sPacketEntityMetadata);
		addSetting(sPacketEntityProperties);
		addSetting(sPacketEntityStatus);
		addSetting(sPacketEntityTeleport);
		addSetting(sPacketEntityVelocity);
		addSetting(sPacketExplosion);
		addSetting(sPacketHeldItemChange);
		addSetting(sPacketJoinGame);
		addSetting(sPacketKeepAlive);
		addSetting(sPacketMaps);
		addSetting(sPacketMoveVehicle);
		addSetting(sPacketMultiBlockChange);
		addSetting(sPacketOpenWindow);
		addSetting(sPacketParticles);
		addSetting(sPacketPlaceGhostRecipe);
		addSetting(sPacketPlayerAbilities);
		addSetting(sPacketPlayerListHeaderFooter);
		addSetting(sPacketPlayerListItem);
		addSetting(sPacketPlayerPosLook);
		addSetting(sPacketRecipeBook);
		addSetting(sPacketRemoveEntityEffect);
		addSetting(sPacketResourcePackSend);
		addSetting(sPacketRespawn);
		addSetting(sPacketScoreboardObjective);
		addSetting(sPacketSelectAdvancementsTab);
		addSetting(sPacketServerDifficulty);
		addSetting(sPacketSetExperience);
		addSetting(sPacketSetPassengers);
		addSetting(sPacketSetSlot);
		addSetting(sPacketSignEditorOpen);
		addSetting(sPacketSoundEffect);
		addSetting(sPacketSpawnExperienceOrb);
		addSetting(sPacketSpawnGlobalEntity);
		addSetting(sPacketSpawnMob);
		addSetting(sPacketSpawnObject);
		addSetting(sPacketSpawnPainting);
		addSetting(sPacketSpawnPlayer);
		addSetting(sPacketSpawnPosition);
		addSetting(sPacketStatistics);
		addSetting(sPacketTabComplete);
		addSetting(sPacketTabComplete);
		addSetting(sPacketTeams);
		addSetting(sPacketTimeUpdate);
		addSetting(sPacketTitle);
		addSetting(sPacketUnloadChunk);
		addSetting(sPacketUpdateBossInfo);
		addSetting(sPacketUpdateHealth);
		addSetting(sPacketUpdateScore);
		addSetting(sPacketUpdateTileEntity);
	}

	final BooleanSetting cPacketAnimation = new BooleanSetting("CPacketAnimation", this, false);
	final BooleanSetting cPacketChatMessage = new BooleanSetting("CPacketChatMessage", this, false);
	final BooleanSetting cPacketClickWindow = new BooleanSetting("CPacketClickWindow", this, false);
	final BooleanSetting cPacketClientSettings = new BooleanSetting("CPacketClientSettings", this, false);
	final BooleanSetting cPacketClientStatus = new BooleanSetting("CPacketClientStatus", this, false);
	final BooleanSetting cPacketCloseWindow = new BooleanSetting("CPacketCloseWindow", this, false);
	final BooleanSetting cPacketConfirmTeleport = new BooleanSetting("CPacketConfirmTeleport", this, false);
	final BooleanSetting cPacketConfirmTransaction = new BooleanSetting("CPacketConfirmTransaction", this, false);
	final BooleanSetting cPacketCreativeInventoryAction = new BooleanSetting("CPacketCreativeInventoryAction", this, false);
	final BooleanSetting cPacketCustomPayload = new BooleanSetting("CPacketCustomPayload", this, false);
	final BooleanSetting cPacketEnchantItem = new BooleanSetting("CPacketEnchantItem", this, false);
	final BooleanSetting cPacketEntityAction = new BooleanSetting("CPacketEntityAction", this, false);
	final BooleanSetting cPacketHeldItemChange = new BooleanSetting("CPacketHeldItemChange", this, false);
	final BooleanSetting cPacketInput = new BooleanSetting("CPacketInput", this, false);
	final BooleanSetting cPacketKeepAlive = new BooleanSetting("CPacketKeepAlive", this, false);
	final BooleanSetting cPacketPlaceRecipe = new BooleanSetting("CPacketPlaceRecipe", this, false);
	final BooleanSetting cPacketPlayer = new BooleanSetting("CPacketPlayer", this, false);
	final BooleanSetting cPacketPlayerPosition = new BooleanSetting("CPacketPlayer.Position", this, false);
	final BooleanSetting cPacketPlayerPositionRotation = new BooleanSetting("CPacketPlayer.PositionRotation", this, false);
	final BooleanSetting cPacketPlayerRotation = new BooleanSetting("CPacketPlayer.Rotation", this, false);
	final BooleanSetting cPacketPlayerAbilities = new BooleanSetting("CPacketPlayerAbilities", this, false);
	final BooleanSetting cPacketPlayerDigging = new BooleanSetting("CPacketPlayerDigging", this, false);
	final BooleanSetting cPacketPlayerTryUseItem = new BooleanSetting("CPacketPlayerTryUseItem", this, false);
	final BooleanSetting cPacketPlayerTryUseItemOnBlock = new BooleanSetting("CPacketPlayerTryUseItemOnBlock", this, false);
	final BooleanSetting cPacketRecipeInfo = new BooleanSetting("CPacketRecipeInfo", this, false);
	final BooleanSetting cPacketResourcePackStatus = new BooleanSetting("CPacketResourcePackStatus", this, false);
	final BooleanSetting cPacketSeenAdvancements = new BooleanSetting("CPacketSeenAdvancements", this, false);
	final BooleanSetting cPacketSpectate = new BooleanSetting("CPacketSpectate", this, false);
	final BooleanSetting cPacketSteerBoat = new BooleanSetting("CPacketSteerBoat", this, false);
	final BooleanSetting cPacketTabComplete = new BooleanSetting("CPacketTabComplete", this, false);
	final BooleanSetting cPacketUpdateSign = new BooleanSetting("CPacketUpdateSign", this, false);
	final BooleanSetting cPacketUseEntity = new BooleanSetting("CPacketUseEntity", this, false);
	final BooleanSetting cPacketVehicleMove = new BooleanSetting("CPacketVehicleMove", this, false);
	final BooleanSetting sPacketAdvancementInfo = new BooleanSetting("SPacketAdvancementInfo", this, false);
	final BooleanSetting sPacketAnimation = new BooleanSetting("SPacketAnimation", this, false);
	final BooleanSetting sPacketBlockAction = new BooleanSetting("SPacketBlockAction", this, false);
	final BooleanSetting sPacketBlockBreakAnim = new BooleanSetting("SPacketBlockBreakAnim", this, false);
	final BooleanSetting sPacketBlockChange = new BooleanSetting("SPacketBlockChange", this, false);
	final BooleanSetting sPacketCamera = new BooleanSetting("SPacketCamera", this, false);
	final BooleanSetting sPacketChangeGameState = new BooleanSetting("SPacketChangeGameState", this, false);
	final BooleanSetting sPacketChat = new BooleanSetting("SPacketChat", this, false);
	final BooleanSetting sPacketChunkData = new BooleanSetting("SPacketChunkData", this, false);
	final BooleanSetting sPacketCloseWindow = new BooleanSetting("SPacketCloseWindow", this, false);
	final BooleanSetting sPacketCollectItem = new BooleanSetting("SPacketCollectItem", this, false);
	final BooleanSetting sPacketCombatEvent = new BooleanSetting("SPacketCombatEvent", this, false);
	final BooleanSetting sPacketConfirmTransaction = new BooleanSetting("SPacketConfirmTransaction", this, false);
	final BooleanSetting sPacketCooldown = new BooleanSetting("SPacketCooldown", this, false);
	final BooleanSetting sPacketCustomPayload = new BooleanSetting("SPacketCustomPayload", this, false);
	final BooleanSetting sPacketCustomSound = new BooleanSetting("SPacketCustomSound", this, false);
	final BooleanSetting sPacketDestroyEntities = new BooleanSetting("SPacketDestroyEntities", this, false);
	final BooleanSetting sPacketDisconnect = new BooleanSetting("SPacketDisconnect", this, false);
	final BooleanSetting sPacketDisplayObjective = new BooleanSetting("SPacketDisplayObjective", this, false);
	final BooleanSetting sPacketEffect = new BooleanSetting("SPacketEffect", this, false);
	final BooleanSetting sPacketEntity = new BooleanSetting("SPacketEntity", this, false);
	final BooleanSetting sPacketEntityRelMove = new BooleanSetting("SPacketEntityRelMove", this, false);
	final BooleanSetting sPacketEntityLook = new BooleanSetting("SPacketEntityLook", this, false);
	final BooleanSetting sPacketEntityLookMove = new BooleanSetting("SPacketEntityLookMove", this, false);
	final BooleanSetting sPacketEntityAttach = new BooleanSetting("SPacketEntityAttach", this, false);
	final BooleanSetting sPacketEntityEffect = new BooleanSetting("SPacketEntityEffect", this, false);
	final BooleanSetting sPacketEntityEquipment = new BooleanSetting("SPacketEntityEquipment", this, false);
	final BooleanSetting sPacketEntityHeadLook = new BooleanSetting("SPacketEntityHeadLook", this, false);
	final BooleanSetting sPacketEntityMetadata = new BooleanSetting("SPacketEntityMetadata", this, false);
	final BooleanSetting sPacketEntityProperties = new BooleanSetting("SPacketEntityProperties", this, false);
	final BooleanSetting sPacketEntityStatus = new BooleanSetting("SPacketEntityStatus", this, false);
	final BooleanSetting sPacketEntityTeleport = new BooleanSetting("SPacketEntityTeleport", this, false);
	final BooleanSetting sPacketEntityVelocity = new BooleanSetting("SPacketEntityVelocity", this, false);
	final BooleanSetting sPacketExplosion = new BooleanSetting("SPacketExplosion", this, false);
	final BooleanSetting sPacketHeldItemChange = new BooleanSetting("SPacketHeldItemChange", this, false);
	final BooleanSetting sPacketJoinGame = new BooleanSetting("SPacketJoinGame", this, false);
	final BooleanSetting sPacketKeepAlive = new BooleanSetting("SPacketKeepAlive", this, false);
	final BooleanSetting sPacketMaps = new BooleanSetting("SPacketMaps", this, false);
	final BooleanSetting sPacketMoveVehicle = new BooleanSetting("SPacketMoveVehicle", this, false);
	final BooleanSetting sPacketMultiBlockChange = new BooleanSetting("SPacketMultiBlockChange", this, false);
	final BooleanSetting sPacketOpenWindow = new BooleanSetting("SPacketOpenWindow", this, false);
	final BooleanSetting sPacketParticles = new BooleanSetting("SPacketParticles", this, false);
	final BooleanSetting sPacketPlaceGhostRecipe = new BooleanSetting("SPacketPlaceGhostRecipe", this, false);
	final BooleanSetting sPacketPlayerAbilities = new BooleanSetting("SPacketPlayerAbilities", this, false);
	final BooleanSetting sPacketPlayerListHeaderFooter = new BooleanSetting("SPacketPlayerListHeaderFooter", this, false);
	final BooleanSetting sPacketPlayerListItem = new BooleanSetting("SPacketPlayerListItem", this, false);
	final BooleanSetting sPacketPlayerPosLook = new BooleanSetting("SPacketPlayerPosLook", this, false);
	final BooleanSetting sPacketRecipeBook = new BooleanSetting("SPacketRecipeBook", this, false);
	final BooleanSetting sPacketRemoveEntityEffect = new BooleanSetting("SPacketRemoveEntityEffect", this, false);
	final BooleanSetting sPacketResourcePackSend = new BooleanSetting("SPacketResourcePackSend", this, false);
	final BooleanSetting sPacketRespawn = new BooleanSetting("SPacketRespawn", this, false);
	final BooleanSetting sPacketScoreboardObjective = new BooleanSetting("SPacketScoreboardObjective", this, false);
	final BooleanSetting sPacketSelectAdvancementsTab = new BooleanSetting("SPacketSelectAdvancementsTab", this, false);
	final BooleanSetting sPacketServerDifficulty = new BooleanSetting("SPacketServerDifficulty", this, false);
	final BooleanSetting sPacketSetExperience = new BooleanSetting("SPacketSetExperience", this, false);
	final BooleanSetting sPacketSetPassengers = new BooleanSetting("SPacketSetPassengers", this, false);
	final BooleanSetting sPacketSetSlot = new BooleanSetting("SPacketSetSlot", this, false);
	final BooleanSetting sPacketSignEditorOpen = new BooleanSetting("SPacketSignEditorOpen", this, false);
	final BooleanSetting sPacketSoundEffect = new BooleanSetting("SPacketSoundEffect", this, false);
	final BooleanSetting sPacketSpawnExperienceOrb = new BooleanSetting("SPacketSpawnExperienceOrb", this, false);
	final BooleanSetting sPacketSpawnGlobalEntity = new BooleanSetting("SPacketSpawnGlobalEntity", this, false);
	final BooleanSetting sPacketSpawnMob = new BooleanSetting("SPacketSpawnMob", this, false);
	final BooleanSetting sPacketSpawnObject = new BooleanSetting("SPacketSpawnObject", this, false);
	final BooleanSetting sPacketSpawnPainting = new BooleanSetting("SPacketSpawnPainting", this, false);
	final BooleanSetting sPacketSpawnPlayer = new BooleanSetting("SPacketSpawnPlayer", this, false);
	final BooleanSetting sPacketSpawnPosition = new BooleanSetting("SPacketSpawnPosition", this, false);
	final BooleanSetting sPacketStatistics = new BooleanSetting("SPacketStatistics", this, false);
	final BooleanSetting sPacketTabComplete = new BooleanSetting("SPacketTabComplete", this, false);
	final BooleanSetting sPacketTeams = new BooleanSetting("SPacketTeams", this, false);
	final BooleanSetting sPacketTimeUpdate = new BooleanSetting("SPacketTimeUpdate", this, false);
	final BooleanSetting sPacketTitle = new BooleanSetting("SPacketTitle", this, false);
	final BooleanSetting sPacketUnloadChunk = new BooleanSetting("SPacketUnloadChunk", this, false);
	final BooleanSetting sPacketUpdateBossInfo = new BooleanSetting("SPacketUpdateBossInfo", this, false);
	final BooleanSetting sPacketUpdateHealth = new BooleanSetting("SPacketUpdateHealth", this, false);
	final BooleanSetting sPacketUpdateScore = new BooleanSetting("SPacketUpdateScore", this, false);
	final BooleanSetting sPacketUpdateTileEntity = new BooleanSetting("SPacketUpdateTileEntity", this, false);
	final BooleanSetting sPacketUseBed = new BooleanSetting("SPacketUseBed", this, false);
	final BooleanSetting sPacketWindowItems = new BooleanSetting("SPacketWindowItems", this, false);
	final BooleanSetting sPacketWindowProperty = new BooleanSetting("SPacketWindowProperty", this, false);
	final BooleanSetting sPacketWorldBorder = new BooleanSetting("SPacketWorldBorder", this, false);

	@EventHandler
	private final Listener<HtlrEventPacket.SendPacket> PacketSendEvent = new Listener<>(event -> {
		// c-packets
		if (event.get_packet() instanceof CPacketAnimation) {
			if (this.cPacketAnimation.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketChatMessage) {
			if (this.cPacketChatMessage.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketClickWindow) {
			if (this.cPacketClickWindow.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketClientSettings) {
			if (this.cPacketClientSettings.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketClientStatus) {
			if (this.cPacketClientStatus.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketCloseWindow) {
			if (this.cPacketCloseWindow.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketConfirmTeleport) {
			if (this.cPacketConfirmTeleport.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketConfirmTransaction) {
			if (this.cPacketConfirmTransaction.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketCreativeInventoryAction) {
			if (this.cPacketCreativeInventoryAction.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketCustomPayload) {
			if (this.cPacketCustomPayload.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketEnchantItem) {
			if (this.cPacketEnchantItem.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketEntityAction) {
			if (this.cPacketEntityAction.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketHeldItemChange) {
			if (this.cPacketHeldItemChange.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketInput) {
			if (this.cPacketInput.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketKeepAlive) {
			if (this.cPacketKeepAlive.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketPlaceRecipe) {
			if (this.cPacketPlaceRecipe.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketPlayer) {
			if (this.cPacketPlayer.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketPlayer.Rotation) {
			if (this.cPacketPlayerRotation.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketPlayer.Position) {
			if (this.cPacketPlayerPosition.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketPlayer.PositionRotation) {
			if (this.cPacketPlayerPositionRotation.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketPlayerAbilities) {
			if (this.cPacketPlayerAbilities.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketPlayerDigging) {
			if (this.cPacketPlayerDigging.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketPlayerTryUseItem) {
			if (this.cPacketPlayerTryUseItem.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketRecipeInfo) {
			if (this.cPacketRecipeInfo.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketResourcePackStatus) {
			if (this.cPacketResourcePackStatus.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketSeenAdvancements) {
			if (this.cPacketSeenAdvancements.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketSpectate) {
			if (this.cPacketSpectate.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketSteerBoat) {
			if (this.cPacketSteerBoat.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketTabComplete) {
			if (this.cPacketTabComplete.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketUpdateSign) {
			if (this.cPacketUpdateSign.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketUseEntity) {
			if (this.cPacketUseEntity.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof CPacketVehicleMove) {
			if (this.cPacketVehicleMove.enabled)
				event.cancel();
		}
	});

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> PacketRecieveEvent = new Listener<>(event -> {
		// s-packets

		if (event.get_packet() instanceof SPacketAdvancementInfo) {
			if (this.sPacketAdvancementInfo.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketAnimation) {
			if (this.sPacketAnimation.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketBlockAction) {
			if (this.sPacketBlockAction.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketBlockBreakAnim) {
			if (this.sPacketBlockBreakAnim.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketBlockChange) {
			if (this.sPacketBlockChange.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketCamera) {
			if (this.sPacketCamera.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketChangeGameState) {
			if (this.sPacketChangeGameState.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketChat) {
			if (this.sPacketChat.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketChunkData) {
			if (this.sPacketChunkData.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketCloseWindow) {
			if (this.sPacketCloseWindow.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketCollectItem) {
			if (this.sPacketCollectItem.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketCombatEvent) {
			if (this.sPacketCombatEvent.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketConfirmTransaction) {
			if (this.sPacketConfirmTransaction.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketCooldown) {
			if (this.sPacketCooldown.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketCustomPayload) {
			if (this.sPacketCustomPayload.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketCustomSound) {
			if (this.sPacketCustomSound.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketDestroyEntities) {
			if (this.sPacketDestroyEntities.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketDisconnect) {
			if (this.sPacketDisconnect.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketDisplayObjective) {
			if (this.sPacketDisplayObjective.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEffect) {
			if (this.sPacketEffect.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntity) {
			if (this.sPacketEntity.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntity.S15PacketEntityRelMove) {
			if (this.sPacketEntityRelMove.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntity.S16PacketEntityLook) {
			if (this.sPacketEntityLook.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntity.S17PacketEntityLookMove) {
			if (this.sPacketEntityLookMove.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntityAttach) {
			if (this.sPacketEntityAttach.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntityEffect) {
			if (this.sPacketEntityEffect.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntityEquipment) {
			if (this.sPacketEntityEquipment.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntityHeadLook) {
			if (this.sPacketEntityHeadLook.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntityMetadata) {
			if (this.sPacketEntityMetadata.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntityProperties) {
			if (this.sPacketEntityProperties.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntityStatus) {
			if (this.sPacketEntityStatus.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntityTeleport) {
			if (this.sPacketEntityTeleport.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketEntityVelocity) {
			if (this.sPacketEntityVelocity.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketExplosion) {
			if (this.sPacketExplosion.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketHeldItemChange) {
			if (this.sPacketHeldItemChange.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketJoinGame) {
			if (this.sPacketJoinGame.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketKeepAlive) {
			if (this.sPacketKeepAlive.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketMaps) {
			if (this.sPacketMaps.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketMoveVehicle) {
			if (this.sPacketMoveVehicle.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketMultiBlockChange) {
			if (this.sPacketMultiBlockChange.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketOpenWindow) {
			if (this.sPacketOpenWindow.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketParticles) {
			if (this.sPacketParticles.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketPlaceGhostRecipe) {
			if (this.sPacketPlaceGhostRecipe.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketPlayerAbilities) {
			if (this.sPacketPlayerAbilities.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketPlayerListHeaderFooter) {
			if (this.sPacketPlayerListHeaderFooter.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketPlayerListItem) {
			if (this.sPacketPlayerListItem.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketPlayerPosLook) {
			if (this.sPacketPlayerPosLook.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketRecipeBook) {
			if (this.sPacketRecipeBook.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketRemoveEntityEffect) {
			if (this.sPacketRemoveEntityEffect.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketResourcePackSend) {
			if (this.sPacketResourcePackSend.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketRespawn) {
			if (this.sPacketRespawn.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketScoreboardObjective) {
			if (this.sPacketScoreboardObjective.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSelectAdvancementsTab) {
			if (this.sPacketSelectAdvancementsTab.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketServerDifficulty) {
			if (this.sPacketServerDifficulty.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSetExperience) {
			if (this.sPacketSetExperience.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSetPassengers) {
			if (this.sPacketSetPassengers.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSetSlot) {
			if (this.sPacketSetSlot.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSignEditorOpen) {
			if (this.sPacketSignEditorOpen.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSoundEffect) {
			if (this.sPacketSoundEffect.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSpawnExperienceOrb) {
			if (this.sPacketSpawnExperienceOrb.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSpawnGlobalEntity) {
			if (this.sPacketSpawnGlobalEntity.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSpawnMob) {
			if (this.sPacketSpawnMob.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSpawnObject) {
			if (this.sPacketSpawnObject.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSpawnPainting) {
			if (this.sPacketSpawnPainting.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSpawnPlayer) {
			if (this.sPacketSpawnPlayer.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketSpawnPosition) {
			if (this.sPacketSpawnPosition.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketStatistics) {
			if (this.sPacketStatistics.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketTabComplete) {
			if (this.sPacketTabComplete.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketTeams) {
			if (this.sPacketTeams.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketTimeUpdate) {
			if (this.sPacketTimeUpdate.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketTitle) {
			if (this.sPacketTitle.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketUnloadChunk) {
			if (this.sPacketUnloadChunk.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketUpdateBossInfo) {
			if (this.sPacketUpdateBossInfo.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketUpdateHealth) {
			if (this.sPacketUpdateHealth.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketUpdateScore) {
			if (this.sPacketUpdateScore.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketUpdateTileEntity) {
			if (this.sPacketUpdateTileEntity.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketUseBed) {
			if (this.sPacketUseBed.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketWindowItems) {
			if (this.sPacketWindowItems.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketWindowProperty) {
			if (this.sPacketWindowProperty.enabled)
				event.cancel();
		} else if (event.get_packet() instanceof SPacketWorldBorder) {
			if (this.sPacketWorldBorder.enabled)
				event.cancel();
		}
	});
}
