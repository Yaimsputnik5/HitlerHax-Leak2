package mod.hitlerhax.module.modules.render;

import me.zero.alpine.event.type.Cancellable;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.event.events.HtlrEventMove;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.event.events.HtlrEventPush;
import mod.hitlerhax.event.events.HtlrEventSetOpaqueCube;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import mod.hitlerhax.util.MathUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class Freecam extends Module {
	final FloatSetting speedSetting = new FloatSetting("Speed", this, 1.0f);
	final BooleanSetting view = new BooleanSetting("View", this, false);
	final BooleanSetting packetCancel = new BooleanSetting("Packet Cancel", this, true);

	public Freecam() {
		super("Freecam", "Allows Spectator Mode Outside The Body", Category.RENDER);

		addSetting(speedSetting);
		addSetting(packetCancel);
		addSetting(view);
	}

	private AxisAlignedBB oldBoundingBox;
	private EntityOtherPlayerMP entity;
	private Vec3d position;
	private float yaw;
	private float pitch;

	@Override
	public void onEnable() {
		super.onEnable();

		if (mc.world == null) {
			this.toggle();
			return;
		}

		mc.player.dismountRidingEntity();

		this.oldBoundingBox = mc.player.getEntityBoundingBox();
		mc.player.setEntityBoundingBox(new AxisAlignedBB(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.posX,
				mc.player.posY, mc.player.posZ));
		(this.entity = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile()))
				.copyLocationAndAnglesFrom(mc.player);
		this.entity.rotationYaw = mc.player.rotationYaw;
		this.entity.rotationYawHead = mc.player.rotationYawHead;
		this.entity.inventory.copyInventory(mc.player.inventory);
		mc.world.addEntityToWorld(69420, this.entity);
		this.position = mc.player.getPositionVector();
		this.yaw = mc.player.rotationYaw;
		this.pitch = mc.player.rotationPitch;
		mc.player.noClip = true;
	}

	@Override
	public void onDisable() {
		super.onDisable();
		if (mc.world == null) {
			return;
		}
		mc.player.setEntityBoundingBox(this.oldBoundingBox);
		if (this.entity != null) {
			mc.world.removeEntity(this.entity);
		}
		if (this.position != null) {
			mc.player.setPosition(this.position.x, this.position.y, this.position.z);
		}
		mc.player.rotationYaw = this.yaw;
		mc.player.rotationPitch = this.pitch;
		mc.player.noClip = false;
	}

	@EventHandler
	private final Listener<HtlrEventMove> OnPlayerMove = new Listener<>(p_Event -> mc.player.noClip = true);

	@EventHandler
	private final Listener<HtlrEventSetOpaqueCube> OnEventSetOpaqueCube = new Listener<>(Cancellable::cancel);

	@Override
	public void onUpdate() {
		mc.player.noClip = true;
		mc.player.setVelocity(0.0, 0.0, 0.0);
		mc.player.jumpMovementFactor = this.speedSetting.getValue();
		final double[] dir = MathUtil.directionSpeed(this.speedSetting.getValue());
		if (mc.player.movementInput.moveStrafe != 0.0f || mc.player.movementInput.moveForward != 0.0f) {
			mc.player.motionX = dir[0];
			mc.player.motionZ = dir[1];
		} else {
			mc.player.motionX = 0.0;
			mc.player.motionZ = 0.0;
		}
		mc.player.setSprinting(false);
		if (this.view.enabled && !mc.gameSettings.keyBindSneak.isKeyDown()
				&& !mc.gameSettings.keyBindJump.isKeyDown()) {
			mc.player.motionY = this.speedSetting.getValue() * -MathUtil.degToRad(mc.player.rotationPitch)
					* mc.player.movementInput.moveForward;
		}
		if (mc.gameSettings.keyBindJump.isKeyDown()) {
			final EntityPlayerSP player = mc.player;
			player.motionY += this.speedSetting.getValue();
		}
		if (mc.gameSettings.keyBindSneak.isKeyDown()) {
			final EntityPlayerSP player2 = mc.player;
			player2.motionY -= this.speedSetting.getValue();
		}
	}

	@EventHandler
	private final Listener<EntityJoinWorldEvent> OnWorldEvent = new Listener<>(p_Event -> {
		if (p_Event.getEntity() == mc.player) {
			toggle();
		}
	});

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> PacketRecieveEvent = new Listener<>(event -> {
		if (event.get_packet() instanceof SPacketPlayerPosLook) {
			final SPacketPlayerPosLook packet2 = (SPacketPlayerPosLook) event.get_packet();
			if (this.packetCancel.enabled) {
				if (this.entity != null) {
					this.entity.setPositionAndRotation(packet2.getX(), packet2.getY(), packet2.getZ(), packet2.getYaw(),
							packet2.getPitch());
				}
				this.position = new Vec3d(packet2.getX(), packet2.getY(), packet2.getZ());
				mc.player.connection.sendPacket(new CPacketConfirmTeleport(packet2.getTeleportId()));
			}
			event.cancel();
		}
	});

	@EventHandler
	private final Listener<HtlrEventPacket.SendPacket> PacketSendEvent = new Listener<>(event -> {
		if (this.packetCancel.enabled) {
			if (event.get_packet() instanceof CPacketPlayer) {
				event.cancel();
			}
		} else if (!(event.get_packet() instanceof CPacketUseEntity)
				&& !(event.get_packet() instanceof CPacketPlayerTryUseItem)
				&& !(event.get_packet() instanceof CPacketPlayerTryUseItemOnBlock)
				&& !(event.get_packet() instanceof CPacketPlayer) && !(event.get_packet() instanceof CPacketVehicleMove)
				&& !(event.get_packet() instanceof CPacketChatMessage)
				&& !(event.get_packet() instanceof CPacketKeepAlive)) {
			event.cancel();
		}
	});

	@EventHandler
	private final Listener<HtlrEventPush> PushEvent = new Listener<>(event -> {
		if (event.stage == 1)
			event.cancel();
	});

}