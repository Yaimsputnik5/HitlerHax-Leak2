package mod.hitlerhax.module.modules.render;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.event.HtlrEventCancellable.Era;
import mod.hitlerhax.event.events.HtlrEventEntity;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.event.events.HtlrEventRain;
import mod.hitlerhax.event.events.HtlrEventSpawnEffect;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.ModeSetting;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;

public class NoRender extends Module{

	public final BooleanSetting rain = new BooleanSetting("rain", this, false);
	public final BooleanSetting skylight = new BooleanSetting("skylightUpdates", this, false);
	public final ModeSetting hurtCam = new ModeSetting("hurtCam", this, "yesHurtCam", "yesHurtCam", "noHurtCam", "penis");
	public final BooleanSetting fire = new BooleanSetting("fire", this, false);
	public final BooleanSetting portalEffect = new BooleanSetting("portalEffect", this, false);
	public final BooleanSetting potionIndicators = new BooleanSetting("potionIndicators", this, false);
	public final BooleanSetting crystals = new BooleanSetting("crystals", this, false);
	public final BooleanSetting totemAnimation = new BooleanSetting("totemAnimation", this, false);
	public final BooleanSetting enchantTables = new BooleanSetting("encahtTables", this, false);
	public final BooleanSetting armor = new BooleanSetting("armor", this, false);
	public final BooleanSetting tnt = new BooleanSetting("tnt", this, false);
	public final BooleanSetting items = new BooleanSetting("items", this, false);
	public final BooleanSetting withers = new BooleanSetting("withers", this, false);
	public final BooleanSetting skulls = new BooleanSetting("skulls", this, false);
	public final BooleanSetting fireworks = new BooleanSetting("fireworks", this, false);
	
	public BooleanSetting particles = new BooleanSetting("particles", this, false);
	public BooleanSetting signs = new BooleanSetting("signs", this, false);
	public BooleanSetting pistons = new BooleanSetting("pistons", this, false);
	
	public NoRender() {
		super("NoRender", "Doesnt render stuff", Category.RENDER);
		
		
		addSetting(rain);
		addSetting(skylight);
		addSetting(hurtCam);
		addSetting(fire);
		addSetting(portalEffect);
		addSetting(potionIndicators);
		addSetting(crystals);
		addSetting(totemAnimation);
		addSetting(enchantTables);
		addSetting(armor);
		addSetting(tnt);
		addSetting(items);
		addSetting(withers);
		addSetting(skulls);
		addSetting(fireworks);

		

	}
	
	
	@Override
	public void onDisable() {
		GuiIngameForge.renderPortal = true;
	}
	
	@Override
	public void onUpdate() {
		// hurtCam penis mode
		if(hurtCam.is("penis")) {
			mc.player.performHurtAnimation();
		}
		
		// portalEffect
		if(portalEffect.isEnabled()) {
			GuiIngameForge.renderPortal = false;
			mc.player.removeActivePotionEffect(MobEffects.NAUSEA);
		}
	}
	
	// rain
	@EventHandler
	private final Listener<HtlrEventRain> onRain = new Listener<>(event -> {
		if(rain.isEnabled()) {
		    if (mc.world == null)
		        return;
		    event.cancel();
		}
	});
	
	// totem animation
	@EventHandler
    private final Listener<HtlrEventPacket> PacketEvent = new Listener<>(event -> {
        if (mc.world == null || mc.player == null) return;
        if (event.get_packet() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus)event.get_packet();
            if (packet.getOpCode() == 35) {
                if (totemAnimation.isEnabled())
                    event.cancel();
            }
        }
    });
	
	// fire
	@EventHandler
    private final Listener<RenderBlockOverlayEvent> OnBlockOverlayEvent = new Listener<>(event -> {
        if (fire.isEnabled() && event.getOverlayType() == OverlayType.FIRE) event.setCanceled(true);
    });
	
	// crystals, tnt, items, withers, skulls, and fireworks
	
	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> onReceivePacket = new Listener<>(event -> {
		 if (event.get_era() == Era.EVENT_PRE) {
	            if (event.get_packet() instanceof SPacketSpawnMob) {
	                final SPacketSpawnMob packet = (SPacketSpawnMob) event.get_packet();

	                if (this.skulls.isEnabled()) {
	                    if (packet.getEntityType() == 19) {
	                        event.cancel();
	                    }
	                }
	            }
		 }
	});
	
	@EventHandler
	private final Listener<HtlrEventEntity> onRenderEntity = new Listener<>(event -> {
			if(crystals.isEnabled()) {
				if (event.get_entity() instanceof EntityEnderCrystal) event.cancel();
			}
			
			if(tnt.isEnabled()) {
				if (event.get_entity() instanceof EntityTNTPrimed) event.cancel();
			}
			
			if(items.isEnabled()) {
				if (event.get_entity() instanceof EntityItem) event.cancel();
			}
			
			if(withers.isEnabled()) {
				if (event.get_entity() instanceof EntityWither) event.cancel();
			}
			
			if(skulls.isEnabled()) {
				if (event.get_entity() instanceof EntityWitherSkull) event.cancel();
			}
			
			if(fireworks.isEnabled()) {
				if (event.get_entity() instanceof EntityFireworkRocket) event.cancel();
			}		
        
	});
	@EventHandler
	private final Listener<HtlrEventSpawnEffect> onSpawnEffectParticle = new Listener<>(event -> {
		if (fireworks.isEnabled()) {
            if (event.getParticleID() == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() || event.getParticleID() == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() ||
            		event.getParticleID() == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() || event.getParticleID() == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID()) {
                event.cancel();
            }
        }
	});
	
	@EventHandler
	private final Listener<HtlrEventEntity> onEntityAdd = new Listener<>(event -> {
		if (fireworks.isEnabled()) {
            if (event.get_entity() instanceof EntityFireworkRocket) {
                event.cancel();
            }
        }

        if (skulls.isEnabled()) {
            if (event.get_entity() instanceof EntityWitherSkull) {
                event.cancel();
            }
        }

        if (tnt.isEnabled()) {
            if (event.get_entity() instanceof EntityTNTPrimed) {
                event.cancel();
            }
        }
	});
	
	// hurtCam = MixinEntityRenderer
	// potionEffect = mixin... some sorta overlay idk
	// skylight = MixinWorld
	// armor = MixinLayerBipedArmor
}
