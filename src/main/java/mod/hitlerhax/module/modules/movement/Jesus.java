package mod.hitlerhax.module.modules.movement;

import mod.hitlerhax.Main;
import mod.hitlerhax.event.HtlrEventBus;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import mod.hitlerhax.setting.settings.ModeSetting;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

public class Jesus extends Module {

	final ModeSetting mode = new ModeSetting("Mode", this, "bounce", "bounce", "packet", "spacebar");
	final FloatSetting speed = new FloatSetting("Float Speed", this, 1.0f);
	final BooleanSetting dmg = new BooleanSetting("Packet Anti-FallDamage", this, false);

	public Jesus() {
		super("Jesus", "makes you walk on water", Category.MOVEMENT);

		this.addSetting(mode);
		this.addSetting(speed);
		this.addSetting(dmg);
	}

	@Override
	protected void onEnable() {

		MinecraftForge.EVENT_BUS.register(this);

		HtlrEventBus.EVENT_BUS.subscribe(this);

		Main.config.Save();
	}

	@Override
	public void onUpdate() {
		if (mc.player.isRiding())
			return;
		if (mode.getMode().equalsIgnoreCase("bounce")) {
			if (mc.player.isInWater()) {
				if (!mc.player.isSneaking())
					mc.player.motionY = 0.03999999910593033D * speed.value;
				else
					mc.player.motionY = -0.03999999910593033D * speed.value;
			}
		}
		if (mode.getMode().equalsIgnoreCase("packet")) {
			if (!mc.player.isSneaking()) {
				if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.2f, mc.player.posZ))
						.getBlock() == Block.getBlockFromName("water") && mc.player.motionY < 0.0D) {
					mc.player.posY -= mc.player.motionY;
					mc.player.motionY = 0;
					if (dmg.enabled)
						mc.player.fallDistance = 0;
				}
				if (mc.player.isInWater()) {
					mc.player.motionY = 0.03999999910593033D * speed.value;
				}
			}
		}
		if (mode.getMode().equalsIgnoreCase("spacebar")) {
			if (mc.player.isInWater() && !mc.player.isSneaking()) {
				mc.player.motionY += 0.03999999910593033D;
			}
		}
	}
}
