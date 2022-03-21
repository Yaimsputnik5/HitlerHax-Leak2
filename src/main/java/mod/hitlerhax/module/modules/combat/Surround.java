package mod.hitlerhax.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import mod.hitlerhax.Client;
import mod.hitlerhax.event.events.HtlrEventRender;
import mod.hitlerhax.misc.RenderBuilder;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.ColorSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import mod.hitlerhax.setting.settings.ModeSetting;
import mod.hitlerhax.util.BlockUtil;
import mod.hitlerhax.util.InventoryUtil;
import mod.hitlerhax.util.PlayerUtil;
import mod.hitlerhax.util.render.ColorUtil;
import mod.hitlerhax.util.render.RenderUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Surround extends Module {
	public Surround() {
		super("Surround", "Places Obsidian Around You", Category.COMBAT);

		addSetting(mode);
		addSetting(disableMode);
		addSetting(blocksPerTick);
		addSetting(timeout);
		addSetting(timeoutTick);
		addSetting(raytrace);
		addSetting(packet);
		addSetting(swingArm);
		addSetting(antiGlitch);
		addSetting(rotate);
		addSetting(strict);
		addSetting(autoSwitch);
		addSetting(centerPlayer);
		addSetting(onlyObsidian);
		addSetting(antiChainPop);
		addSetting(chorusSave);
		addSetting(renderSurround);
		addSetting(colorPicker);

	}

	final ModeSetting mode = new ModeSetting("Mode", this, "Standard", "Standard", "Full", "Anti-City");
	final ModeSetting disableMode = new ModeSetting("Disable", this, "Off-Ground", "Off-Ground", "Completion", "Never");
	final ModeSetting centerPlayer = new ModeSetting("Center", this, "Teleport", "Teleport", "NCP", "None");
	final FloatSetting blocksPerTick = new FloatSetting("Blocks Per Tick", this, 0.0f);
	final ModeSetting autoSwitch = new ModeSetting("Switch", this, "SwitchBack", "SwitchBack", "Normal", "Packet", "None");

	final BooleanSetting timeout = new BooleanSetting("Timeout", this, true);
	final FloatSetting timeoutTick = new FloatSetting("Timeout Ticks", this, 1.0f);

	final BooleanSetting raytrace = new BooleanSetting("Raytrace", this, true);
	final BooleanSetting packet = new BooleanSetting("Packet", this, false);
	final BooleanSetting swingArm = new BooleanSetting("Swing Arm", this, true);
	final BooleanSetting antiGlitch = new BooleanSetting("Anti-Glitch", this, false);

	final BooleanSetting rotate = new BooleanSetting("Rotate", this, false);
	final BooleanSetting strict = new BooleanSetting("StrictRotation", this, true);

	final BooleanSetting onlyObsidian = new BooleanSetting("Only Obsidian", this, true);
	final BooleanSetting antiChainPop = new BooleanSetting("Anti-ChainPop", this, true);
	final BooleanSetting chorusSave = new BooleanSetting("Chorus Save", this, false);

	final BooleanSetting renderSurround = new BooleanSetting("Render", this, true);
	final ColorSetting colorPicker = new ColorSetting("Color Picker", this, new ColorUtil(0, 121, 194, 100));

	int obsidianSlot;
	public static boolean hasPlaced;
	Vec3d center = Vec3d.ZERO;
	int blocksPlaced = 0;
	BlockPos renderBlock;

	@Override
	public void onEnable() {
		if (mc.player == null || mc.world == null)
			return;

		super.onEnable();

		obsidianSlot = InventoryUtil.getBlockInHotbar(Blocks.OBSIDIAN);

		if (obsidianSlot == -1) {
			Client.addChatMessage("No Obsidian, " + ChatFormatting.RED + "Disabling!");
			this.setToggled(false);
		}

		else {
			hasPlaced = false;
			center = PlayerUtil.getCenter(mc.player.posX, mc.player.posY, mc.player.posZ);

			switch (centerPlayer.getMode()) {
			case "Teleport":
				mc.player.motionX = 0;
				mc.player.motionZ = 0;
				mc.player.connection.sendPacket(new CPacketPlayer.Position(center.x, center.y, center.z, true));
				mc.player.setPosition(center.x, center.y, center.z);
				break;
			case "NCP":
				mc.player.motionX = (center.x - mc.player.posX) / 2;
				mc.player.motionZ = (center.z - mc.player.posZ) / 2;
				break;
			}
		}
	}

	@Override
	public void onUpdate() {
		if (mc.player == null || mc.world == null) {
			return;
		}

		switch (disableMode.getMode()) {
		case "Off-Ground":
			if (!mc.player.onGround)
				this.setToggled(false);
			break;

		case "Completion":
			if (hasPlaced)
				this.setToggled(false);
			break;

		case "Never":
			if (timeout.isEnabled() && !mode.getMode().equals("Never"))
				if (mc.player.ticksExisted % timeoutTick.getValue() == 0)
					this.setToggled(false);
			break;
		}

		surroundPlayer();

		if (blocksPlaced == 0)
			hasPlaced = true;
	}

	public void surroundPlayer() {
		int previousSlot = mc.player.inventory.currentItem;

		switch (autoSwitch.getMode()) {
		case "SwitchBack":

		case "Normal":
			InventoryUtil.switchToSlot(onlyObsidian.isEnabled() ? InventoryUtil.getBlockInHotbar(Blocks.OBSIDIAN)
					: InventoryUtil.getAnyBlockInHotbar());
			break;
		case "Packet":
			InventoryUtil.switchToSlotGhost(onlyObsidian.isEnabled() ? InventoryUtil.getBlockInHotbar(Blocks.OBSIDIAN)
					: InventoryUtil.getAnyBlockInHotbar());
			break;
		}

		for (Vec3d placePositions : getSurround()) {
			if (BlockUtil.getBlockResistance(new BlockPos(placePositions.add(mc.player.getPositionVector())))
					.equals(BlockUtil.BlockResistance.Blank)) {
				if (obsidianSlot != -1)
					BlockUtil.placeBlock(new BlockPos(placePositions.add(mc.player.getPositionVector())),
							rotate.isEnabled(), strict.isEnabled(), raytrace.isEnabled(), packet.isEnabled(),
							swingArm.isEnabled(), antiGlitch.isEnabled());

				renderBlock = new BlockPos(placePositions.add(mc.player.getPositionVector()));
				blocksPlaced++;

				if (blocksPlaced == blocksPerTick.getValue() && !disableMode.getMode().equals("Packet"))
					return;
			}
		}

		if (autoSwitch.getMode().equals("SwitchBack"))
			InventoryUtil.switchToSlot(previousSlot);
	}

	@Override
	public void render(HtlrEventRender event) {
		if (renderSurround.isEnabled() && renderBlock != null)
			RenderUtil.drawBoxBlockPos(renderBlock, 0, 0, 0, colorPicker.getColor(), RenderBuilder.RenderMode.Fill);
	}

	List<Vec3d> getSurround() {
		switch (mode.getMode()) {
		case "Standard":
			return standardSurround;
		case "Full":
			return fullSurround;
		case "Anti-City":
			return antiCitySurround;
		}

		return standardSurround;
	}

	final List<Vec3d> standardSurround = new ArrayList<>(Arrays.asList(new Vec3d(0, -1, 0), new Vec3d(1, 0, 0),
																	   new Vec3d(-1, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 0, -1)));

	final List<Vec3d> fullSurround = new ArrayList<>(Arrays.asList(new Vec3d(0, -1, 0), new Vec3d(1, -1, 0),
																   new Vec3d(0, -1, 1), new Vec3d(-1, -1, 0), new Vec3d(0, -1, -1), new Vec3d(1, 0, 0), new Vec3d(0, 0, 1),
																   new Vec3d(-1, 0, 0), new Vec3d(0, 0, -1)));

	final List<Vec3d> antiCitySurround = new ArrayList<>(Arrays.asList(new Vec3d(0, -1, 0), new Vec3d(1, 0, 0),
																	   new Vec3d(-1, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 0, -1), new Vec3d(2, 0, 0), new Vec3d(-2, 0, 0),
																	   new Vec3d(0, 0, 2), new Vec3d(0, 0, -2), new Vec3d(3, 0, 0), new Vec3d(-3, 0, 0), new Vec3d(0, 0, 3),
																	   new Vec3d(0, 0, -3)));
}