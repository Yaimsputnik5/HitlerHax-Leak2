package mod.hitlerhax.module.modules.movement;

import java.util.Collections;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.Main;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.event.events.HtlrEventPlayerTravel;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraftforge.common.MinecraftForge;

public class BoatFly extends Module {
	// ported to java from lambda client
	// https://github.com/lambda-client/lambda/blob/master/src/main/kotlin/com/lambda/client/module/modules/movement/BoatFly.kt

	public BoatFly() {
		super("BoatFly", "Allows Flight In Boats", Category.MOVEMENT);

		this.addSetting(speed);
		this.addSetting(upSpeed);
		this.addSetting(glideSpeed);
		this.addSetting(antiStuck);
		this.addSetting(remount);
		this.addSetting(antiForceLook);
		this.addSetting(forceInteract);
		this.addSetting(teleportSpoof);
		this.addSetting(cancelPlayer);
		this.addSetting(antiDesync);
	}

	private final FloatSetting speed = new FloatSetting("Speed", this, 0.1f);
	private final FloatSetting upSpeed = new FloatSetting("Up Speed", this, 1.0f);
	private final FloatSetting glideSpeed = new FloatSetting("Glide Speed", this, 0.1f);
	private final BooleanSetting antiStuck = new BooleanSetting("Anti Stuck", this, true);
	private final BooleanSetting remount = new BooleanSetting("Remount", this, true);
	private final BooleanSetting antiForceLook = new BooleanSetting("Anti Force Look", this, true);
	private final BooleanSetting forceInteract = new BooleanSetting("Force Interact", this, true);
	private final BooleanSetting teleportSpoof = new BooleanSetting("Teleport Spoof", this, false);
	private final BooleanSetting cancelPlayer = new BooleanSetting("Cancel Player Packets", this, false);
	private final BooleanSetting antiDesync = new BooleanSetting("Anti Desync", this, false);

	@Override
	public void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);

		HtlrEventBus.EVENT_BUS.unsubscribe(this);

		Main.config.Save();

		if (antiDesync.enabled) {
			mc.player.connection.sendPacket(new CPacketInput(0.0f, 0.0f, false, true));
			mc.player.dismountRidingEntity();
		}
	}

	@EventHandler
	private final Listener<HtlrEventPacket.SendPacket> PacketSendEvent = new Listener<>(event -> {
		if (mc.player == null || mc.world == null)
			return;
		Entity ridingEntity = mc.player.ridingEntity;
		if (!(ridingEntity instanceof EntityBoat) || !cancelPlayer.enabled)
			return;

		if (event.get_packet() instanceof CPacketPlayer || event.get_packet() instanceof CPacketInput
				|| event.get_packet() instanceof CPacketSteerBoat) {
			if (event.get_packet() instanceof CPacketInput
					&& event.get_packet().equals(new CPacketInput(0.0f, 0.0f, false, true))) {
				return;
			} else {
				event.cancel();
			}
		}
	});

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> PacketReceiveEvent = new Listener<>(event -> {
		if (mc.player == null || mc.world == null)
			return;
		Entity ridingEntity = mc.player.ridingEntity;
		if (!(ridingEntity instanceof EntityBoat))
			return;

		if (event.get_packet() instanceof SPacketSetPassengers) {
			Entity entity = mc.world.getEntityByID(((SPacketSetPassengers) event.get_packet()).getEntityId());
			if (remount.enabled && entity != null) {
				if (!Collections.singletonList(((SPacketSetPassengers) event.get_packet()).getPassengerIds())
						.contains(mc.player.getEntityId())
						&& ridingEntity.entityId == ((SPacketSetPassengers) event.get_packet()).getEntityId()) {
					if (teleportSpoof.enabled)
						event.cancel();
					mc.player.connection.sendPacket(new CPacketUseEntity(entity, EnumHand.OFF_HAND));
				} else if ((!Collections.singletonList(((SPacketSetPassengers) event.get_packet()).getPassengerIds())
						.isEmpty())
						&& Collections.singletonList(((SPacketSetPassengers) event.get_packet()).getPassengerIds())
								.contains(mc.player.getEntityId())) {
					if (antiForceLook.enabled) {
						entity.rotationYaw = mc.player.prevRotationYaw;
						entity.rotationPitch = mc.player.prevRotationPitch;
					}
				}
			}
		} else if (event.get_packet() instanceof SPacketPlayerPosLook) {
			if (antiForceLook.enabled) {
				((SPacketPlayerPosLook) event.get_packet()).yaw = mc.player.rotationYaw;
				((SPacketPlayerPosLook) event.get_packet()).pitch = mc.player.rotationPitch;
			}
		} else if (event.get_packet() instanceof SPacketEntityTeleport) {
			if (teleportSpoof.enabled) {
				if (((SPacketEntityTeleport) event.get_packet()).getEntityId() == ridingEntity.entityId) {
					if (mc.player.getPositionVector()
							.distanceTo(new Vec3d(((SPacketEntityTeleport) event.get_packet()).getX(),
									((SPacketEntityTeleport) event.get_packet()).getY(),
									((SPacketEntityTeleport) event.get_packet()).getZ())) > 20) {
						Entity entity = mc.world
								.getEntityByID(((SPacketEntityTeleport) event.get_packet()).getEntityId());
						if (entity != null)
							mc.player.connection.sendPacket(new CPacketUseEntity(entity, EnumHand.OFF_HAND));
					} else {
						if (antiForceLook.enabled)
							event.cancel();

						ridingEntity.posX = ((SPacketEntityTeleport) event.get_packet()).getX();
						ridingEntity.posY = ((SPacketEntityTeleport) event.get_packet()).getY();
						ridingEntity.posZ = ((SPacketEntityTeleport) event.get_packet()).getZ();
					}
				}
			}
		} else if (event.get_packet() instanceof SPacketMoveVehicle) {
			if (forceInteract.enabled)
				event.cancel();
		}
	});

	@EventHandler
	private final Listener<HtlrEventPlayerTravel> playerTravel = new Listener<>(event -> {
		Entity r = mc.player.ridingEntity;
		if (!(r instanceof EntityBoat))
			return;
		EntityBoat ridingEntity = (EntityBoat) r;
		ridingEntity.rotationYaw = mc.player.rotationYaw;
		ridingEntity.updateInputs(false, false, false, false);
		ridingEntity.setNoGravity(true);
		ridingEntity.motionY = 0.0;
		if (glideSpeed.value > 0 && !mc.gameSettings.keyBindJump.isKeyDown())
			ridingEntity.motionY = -glideSpeed.value;

		if (mc.gameSettings.keyBindJump.isKeyDown())
			ridingEntity.motionY = upSpeed.value;
		if (mc.gameSettings.keyBindBack.isKeyDown())
			ridingEntity.motionY = -upSpeed.value;

		steerEntity(ridingEntity, speed.value, antiStuck.enabled);
	});

	public void steerEntity(Entity entity, Float speed, Boolean antiStuck) {
		double yawRad = calcMoveYaw();

		double motionX = -Math.sin(yawRad) * speed;
		double motionZ = Math.cos(yawRad) * speed;

		if ((mc.player.movementInput.moveForward != 0.0f || mc.player.movementInput.moveStrafe != 0.0f)
				&& !isBorderingChunk(entity, motionX, motionZ, antiStuck)) {
			mc.player.ridingEntity.motionX = motionX;
			mc.player.ridingEntity.motionZ = motionZ;
		} else {
			mc.player.ridingEntity.motionX = 0.0;
			mc.player.ridingEntity.motionZ = 0.0;
		}
	}

	public double calcMoveYaw() {
		float yawIn = mc.player.rotationYaw;
		float moveForward = getRoundedMovementInput(mc.player.movementInput.moveForward);
		float moveString = getRoundedMovementInput(mc.player.movementInput.moveStrafe);
		float strafe = 90 * moveString;
		strafe *= moveForward != 0F ? moveForward * 0.5F : 1F;

		float yaw = yawIn - strafe;
		yaw -= moveForward < 0F ? 180 : 0;

		return Math.toRadians(yaw);
	}

	public float getRoundedMovementInput(float input) {
		if (input > 0f)
			return 1f;
		if (input < 0f)
			return -1f;
		return 0f;
	}

	public boolean isBorderingChunk(Entity entity, double motionX, double motionZ, boolean antiStuck) {
		return antiStuck && (mc.world.getChunk(((int) (entity.posX + motionX)) >> 4,
				((int) (entity.posZ + motionZ)) >> 4) instanceof EmptyChunk);
	}
}
