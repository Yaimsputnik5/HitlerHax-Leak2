package mod.hitlerhax.util;


import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.event.events.HtlrEventRotation;
import mod.hitlerhax.misc.Rotation;
import mod.hitlerhax.misc.Rotation.RotationMode;
import mod.hitlerhax.misc.RotationPriority;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Comparator;
import java.util.concurrent.LinkedBlockingQueue;

public class RotationUtil {

    public RotationUtil() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static final LinkedBlockingQueue<Rotation> rotationQueue = new LinkedBlockingQueue<>();
    public static Rotation serverRotation = null;
    public static Rotation currentRotation = null;

    public static final float yawleftOver = 0;
    public static final float pitchleftOver = 0;

    public static int tick = 5;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        rotationQueue.stream().sorted(Comparator.comparing(rotation -> rotation.rotationPriority.getPriority()));

        if (currentRotation != null)
            currentRotation = null;

        if (!rotationQueue.isEmpty()) {
            currentRotation = rotationQueue.poll();
            currentRotation.updateRotations();
        }

        tick++;
    }

    @SubscribeEvent
    public void onRotate(HtlrEventRotation event) {
        try {
            if (currentRotation != null && currentRotation.mode.equals(RotationMode.Packet)) {
                event.setCanceled(true);

                if (tick == 1) {
                    event.setYaw(currentRotation.yaw + yawleftOver);
                    event.setPitch(currentRotation.pitch + pitchleftOver);
                }

                else {
                    event.setYaw(currentRotation.yaw);
                    event.setPitch(currentRotation.pitch);
                }
            }
        } catch (Exception ignored) {

        }
    }

    @SubscribeEvent
    public void onPacketSend(HtlrEventPacket.SendPacket event) {
        if (currentRotation != null && !rotationQueue.isEmpty() && event.get_packet() instanceof CPacketPlayer) {
            if (((CPacketPlayer) event.get_packet()).rotating)
                serverRotation = new Rotation(((CPacketPlayer) event.get_packet()).yaw, ((CPacketPlayer) event.get_packet()).pitch, RotationMode.Packet, RotationPriority.Lowest);
        }
    }

}