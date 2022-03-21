package mod.hitlerhax.module.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import mod.hitlerhax.Main;
import mod.hitlerhax.event.events.HtlrEventPacket;
import mod.hitlerhax.event.events.HtlrEventRender;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.ColorSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.setting.settings.ModeSetting;
import mod.hitlerhax.util.Timer;
import mod.hitlerhax.util.render.ColorUtil;
import mod.hitlerhax.util.render.RenderUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;

//credit: srgantmoomoo and postman client

//TODO rewrite this
public class CrystalAura extends Module {

	public final BooleanSetting breakCrystal = new BooleanSetting("BreakCrystal", this, true);
	public final BooleanSetting placeCrystal = new BooleanSetting("PlaceCrystal", this, true);
	public final ModeSetting switchHand = new ModeSetting("Switch", this, "Off", "Off", "OnEnable", "Detect");
	public final ModeSetting logic = new ModeSetting("Logic", this, "Break, Place", "Break, Place", "Place, Break");
	public final IntSetting breakSpeed = new IntSetting("BreakSpeed", this, 20);
	public final ModeSetting breakType = new ModeSetting("BreakType", this, "Packet", "Swing", "Packet");
	public final ModeSetting breakHand = new ModeSetting("BreakHand", this, "Both", "Main", "Offhand", "Both");
	public final ModeSetting breakMode = new ModeSetting("BreakMode", this, "All", "All", "Smart", "Own");
	public final FloatSetting breakRange = new FloatSetting("BreakRange", this, 4.4f);
	public final FloatSetting placeRange = new FloatSetting("PlaceRange", this, 4.4f);
	public final IntSetting facePlaceValue = new IntSetting("FacePlaceVal", this, 8);
	public final BooleanSetting highPing = new BooleanSetting("HighPing", this, true);
	public final BooleanSetting antiGhost = new BooleanSetting("AntiGhosting", this, true);
	public final BooleanSetting raytrace = new BooleanSetting("Raytrace", this, true);
	public final BooleanSetting rotate = new BooleanSetting("Rotate", this, true);
	public final BooleanSetting spoofRotations = new BooleanSetting("SpoofRotations", this, true);
	public final IntSetting minDmg = new IntSetting("MinDmg", this, 5);
	public final BooleanSetting multiplace = new BooleanSetting("Multiplace", this, false);
	public final IntSetting multiplaceValue = new IntSetting("MultiplaceValue", this, 2);
	public final BooleanSetting multiplacePlus = new BooleanSetting("MultiplacePlus", this, true);
	public final BooleanSetting antiSuicide = new BooleanSetting("AntiSuicide", this, false);
	public final IntSetting maxSelfDmg = new IntSetting("AntiSuicideValue", this, 10);
	public final BooleanSetting antiSelfPop = new BooleanSetting("AntiSelfPop", this, true);
	public final FloatSetting enemyRange = new FloatSetting("Range", this, 6.0f);
	public final FloatSetting wallsRange = new FloatSetting("WallsRange", this, 3.5f);
	public final BooleanSetting mode113 = new BooleanSetting("1.13Place", this, false);
	public final BooleanSetting strictInteract = new BooleanSetting("StrictInteract", this, true);
	public final BooleanSetting outline = new BooleanSetting("Outline", this, false);
	public final BooleanSetting showBlock = new BooleanSetting("ShowBlock", this, true);
	public final BooleanSetting showDamage = new BooleanSetting("ShowDamage", this, true);
	final ColorSetting color = new ColorSetting("Color", this, Main.COLOR);

	public CrystalAura() {
		super("CrystalAura", "places and breaks Crystals", Category.COMBAT);

		addSetting(breakCrystal);
		addSetting(placeCrystal);
		addSetting(switchHand);
		addSetting(logic);
		addSetting(breakSpeed);
		addSetting(breakType);
		addSetting(breakHand);
		addSetting(breakMode);
		addSetting(breakRange);
		addSetting(placeRange);
		addSetting(facePlaceValue);
		addSetting(highPing);
		addSetting(antiGhost);
		addSetting(raytrace);
		addSetting(rotate);
		addSetting(spoofRotations);
		addSetting(minDmg);
		addSetting(multiplace);
		addSetting(multiplaceValue);
		addSetting(multiplacePlus);
		addSetting(antiSuicide);
		addSetting(maxSelfDmg);
		addSetting(antiSelfPop);
		addSetting(enemyRange);
		addSetting(wallsRange);
		addSetting(mode113);
		addSetting(strictInteract);
		addSetting(outline);
		addSetting(showBlock);
		addSetting(showDamage);

	}

	private boolean switchCooldown = false;
	private BlockPos renderBlock;
	private EnumFacing enumFacing;
	private Entity renderEnt;
	public static final ArrayList<BlockPos> PlacedCrystals = new ArrayList<>();
	public static boolean ghosting = false;
	public boolean active = false;
	boolean offHand = false;
	private boolean togglePitch = false;
	int oldSlot;
	public static boolean placing = false;

	final Timer timer = new Timer();

	@Override
	public void onEnable() {
		if (mc.player == null || mc.world == null)
			return;
		oldSlot = mc.player.inventory.currentItem;

		PlacedCrystals.clear();
		resetRotation();

		active = false;
		placing = false;
		ghosting = false;
	}

	@Override
	public void onDisable() {
		super.onDisable();

		if (switchHand.is("OnEnable")) {
			mc.player.inventory.currentItem = oldSlot;
			mc.playerController.updateController();
		}

		renderBlock = null;
		renderEnt = null;

		PlacedCrystals.clear();

		active = false;
		placing = false;
		ghosting = false;
	}

	@Override
	public void onUpdate() {
		if (PlacedCrystals.size() > 3) {
			if (timer.getTimePassed() > 40L) {
				if (PlacedCrystals.size() > 3) {
					ghosting = true;
				}
			}
		}

		if (mc.player == null || mc.world == null || Main.moduleManager.getModule("Surround").isToggled())
			return;
		implementLogic();
	}

	private void implementLogic() {
		if (logic.is("Break, Place")) {
			breakLogic();
			placeLogic();
		} else if (logic.is("Place, Break")) {
			placeLogic();
			breakLogic();
		}
	}

	private void breakLogic() {
		EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
				.filter(entity -> entity instanceof EntityEnderCrystal)
				.filter(e -> mc.player.getDistance(e) <= breakRange.getValue()).filter(this::crystalCheck)
				.map(entity -> (EntityEnderCrystal) entity).min(Comparator.comparing(c -> mc.player.getDistance(c)))
				.orElse(null);

		if (breakCrystal.isEnabled() && crystal != null) {
			if (!mc.player.canEntityBeSeen(crystal) && mc.player.getDistance(crystal) > wallsRange.getValue())
				return;

			if (timer.getTimePassed() / 50 >= 20 - breakSpeed.getValue()) {
				timer.resetCurrent();
				active = true;

				if (rotate.isEnabled()) {
					lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, mc.player);
				}

				if (breakType.is("Swing")) {
					breakCrystal(crystal);
				}
				if (breakType.is("Packet")) {
					mc.player.connection.sendPacket(new CPacketUseEntity(crystal));
					swingArm();
				}

				if (highPing.isEnabled()) {
					crystal.setDead();
					mc.world.removeAllEntities();
					mc.world.getLoadedEntityList();
				}

				active = false;
			}
		} else {
			resetRotation();

			active = false;
		}
	}

	private void placeLogic() {
		int crystalSlot = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL
				? mc.player.inventory.currentItem
				: -1;
		if (crystalSlot == -1) {
			for (int l = 0; l < 9; ++l) {
				if (mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
					if (mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() != Items.END_CRYSTAL) {
						crystalSlot = l;
						break;
					}
				}
			}
		}

		offHand = mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;

		if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
			offHand = true;
		} else if (crystalSlot == -1) {
			return;
		}

		List<BlockPos> blocks = findCrystalBlocks();

		List<Entity> entities = new ArrayList<>(new ArrayList<>(mc.world.playerEntities));

		BlockPos blockPos1 = null;
		double damage = 0.5D;

		if (!placeCrystal.isEnabled())
			return;

		// switch system (onEnable)
		if (!offHand && mc.player.inventory.currentItem != crystalSlot) {
			if (this.switchHand.is("OnEnable")) {
				mc.player.inventory.currentItem = crystalSlot;
				mc.playerController.updateController();
				resetRotation();
				this.switchCooldown = true;
			}
		}

		for (Entity entity : entities) {

			if (entity == mc.player || ((EntityLivingBase) entity).getHealth() <= 0)
				continue;

			for (BlockPos blockPos : blocks) {
				double b = entity.getDistanceSq(blockPos);

				if (b >= Math.pow(enemyRange.getValue(), 2))
					continue;

				double d = calculateDamage(blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D, entity);

				if (d <= minDmg.getValue() && ((EntityLivingBase) entity).getHealth()
						+ ((EntityLivingBase) entity).getAbsorptionAmount() > facePlaceValue.getValue())
					continue;

				if (d > damage) {
					double self = calculateDamage(blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D,
							mc.player);

					if ((self > d && !(d < ((EntityLivingBase) entity).getHealth()))
							|| self - 0.5D > mc.player.getHealth() && antiSelfPop.isEnabled())
						continue;

					if (antiSuicide.isEnabled() && self > maxSelfDmg.getValue())
						continue;

					damage = d;
					blockPos1 = blockPos;
					renderEnt = entity;
				}
			}
		}

		if (damage == 0.5D) {
			renderBlock = null;
			renderEnt = null;
			resetRotation();
			return;
		}

		renderBlock = blockPos1;

		if (timer.getTimePassed() / 50 >= 20 - breakSpeed.getValue()) {

			if (rotate.isEnabled()) {
				lookAtPacket(blockPos1.getX() + 0.5D, blockPos1.getY() - 0.5D, blockPos1.getZ() + 0.5D, mc.player);
			}

			RayTraceResult result = mc.world.rayTraceBlocks(
					new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ),
					new Vec3d(blockPos1.getX() + 0.5D, blockPos1.getY() - 0.5D, blockPos1.getZ() + 0.5D));

			if (raytrace.isEnabled()) {
				if (result == null || result.sideHit == null) {
					enumFacing = null;
					renderBlock = null;
					resetRotation();
					return;
				} else {
					enumFacing = result.sideHit;
				}
			}

			if (this.switchCooldown) {
				this.switchCooldown = false;
				return;
			}

			Vec3d crystal = new Vec3d(blockPos1.getX() + 0.5D, blockPos1.getY() - 0.5D, blockPos1.getZ() + 0.5D);
			EnumFacing placeFace;

			if (blockPos1 != null) {
				if (!offHand && mc.player.inventory.currentItem != crystalSlot) {
					if (this.switchHand.is("Detect")) {
						mc.player.inventory.currentItem = crystalSlot;
						mc.playerController.updateController();
						resetRotation();
						this.switchCooldown = true;
					}
				}
				if (mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL
						&& mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL)
					return;
				if (raytrace.isEnabled() && enumFacing != null) {
					mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos1, enumFacing,
							offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
					placing = true;
				} else if (strictInteract.isEnabled()
						&& blockPos1.getY() > mc.player.getEntityBoundingBox().minY + mc.player.getEyeHeight()) {
					RayTraceResult lowestResult = mc.world.rayTraceBlocks(mc.player.getPositionEyes(1), crystal);
					placeFace = (lowestResult == null || lowestResult.sideHit == null) ? EnumFacing.DOWN
							: lowestResult.sideHit;
					mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos1, placeFace,
							offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
					placing = true;
				} else if (blockPos1.getY() == 255) {
					mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos1, EnumFacing.DOWN,
							offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
					placing = true;
				} else {
					mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(blockPos1, EnumFacing.UP,
							offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0, 0, 0));
					placing = true;
				}
				// switch system (detect)
				/*
				 * if(!switched) { mc.player.inventory.currentItem = oldSlot; resetRotation();
				 * this.switchCooldown = true; switched = false; }
				 */

				mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
				PlacedCrystals.add(blockPos1);

			}

			if (isSpoofingAngles) {
				if (togglePitch) {
					mc.player.rotationPitch += 0.0004;
					togglePitch = false;
				} else {
					mc.player.rotationPitch -= 0.0004;
					togglePitch = true;
				}
			}
			if (!placeCrystal.isEnabled())
				return;
			timer.resetCurrent();
		}

	}

	@Override
	public void render(HtlrEventRender event) {
		if (this.renderBlock != null && showBlock.isEnabled()) {
			RenderUtil.drawBox(this.renderBlock, 1.0d, new ColorUtil(color.getColor()), 255);
		}
		if (outline.isEnabled()) {
			if (this.renderBlock != null && this.renderEnt != null) {
				RenderUtil.drawBoundingBox(this.renderBlock, 1.0d, 1.0f, new ColorUtil(color.getColor(), 255));
			}
		}

		if (showDamage.isEnabled()) {
			if (this.renderBlock != null && this.renderEnt != null) {
				double d = calculateDamage(renderBlock.getX() + .5, renderBlock.getY() + 1, renderBlock.getZ() + .5,
						renderEnt);
				String[] damageText = new String[1];
				damageText[0] = (Math.floor(d) == d ? (int) d : String.format("%.1f", d)) + "";
				RenderUtil.drawNametag(renderBlock.getX() + 0.5, renderBlock.getY() + 0.5, renderBlock.getZ() + 0.5,
						damageText, new ColorUtil(255, 255, 255), 1);
			}
		}

	}

	private void breakCrystal(EntityEnderCrystal crystal) {
		mc.playerController.attackEntity(mc.player, crystal);

		swingArm();
	}

	private void swingArm() {
		if (breakHand.getMode().equalsIgnoreCase("Both") && mc.player.getHeldItemOffhand() != null) {
			mc.player.swingArm(EnumHand.MAIN_HAND);
			mc.player.swingArm(EnumHand.OFF_HAND);
		} else if (breakHand.getMode().equalsIgnoreCase("Offhand") && mc.player.getHeldItemOffhand() != null) {
			mc.player.swingArm(EnumHand.OFF_HAND);
		} else {
			mc.player.swingArm(EnumHand.MAIN_HAND);
		}
	}

	@EventHandler
	private final Listener<HtlrEventPacket.SendPacket> packetSendListener = new Listener<>(event -> {
		Packet<?> packet = event.get_packet();
		if (packet instanceof CPacketPlayer && spoofRotations.isEnabled()) {
			if (isSpoofingAngles) {
				((CPacketPlayer) packet).getYaw(yaw);
				((CPacketPlayer) packet).getPitch(pitch);
			}
		}
	});

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> packetReceiveListener = new Listener<>(event -> {
		if (event.get_packet() instanceof SPacketSoundEffect) {
			final SPacketSoundEffect packet = (SPacketSoundEffect) event.get_packet();
			if (packet.getCategory() == SoundCategory.BLOCKS
					&& packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
				for (Entity e : mc.world.loadedEntityList) {
					if (e instanceof EntityEnderCrystal) {
						if (e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
							e.setDead();
						}
					}
				}
			}
		}
	});

	@EventHandler
	private final Listener<HtlrEventPacket.ReceivePacket> packetReceiveListener2 = new Listener<>(event -> {
		if (event.get_packet() instanceof SPacketSoundEffect) {
			final SPacketSoundEffect packet = (SPacketSoundEffect) event.get_packet();
			if (packet.getCategory() == SoundCategory.BLOCKS
					&& packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
				for (BlockPos blockPos : PlacedCrystals) {
					if (blockPos.getDistance((int) packet.getX(), (int) packet.getY(), (int) packet.getZ()) <= 6) {
						CPacketUseEntity cPacketUseEntity = new CPacketUseEntity(
								new EntityEnderCrystal(mc.world, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
						mc.player.connection.sendPacket(cPacketUseEntity);
						PlacedCrystals.remove(blockPos);
						return;
					}
				}
				for (Entity e : mc.world.loadedEntityList) {
					if (e instanceof EntityEnderCrystal) {
						if (e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
							e.setDead();
						}
					}
				}
			}
		}
	});

	/*
	 * somewhat custom crystal utils
	 */

	public boolean canPlaceCrystal(BlockPos blockPos) {
		BlockPos airBlock1 = blockPos.add(0, 1, 0);
		BlockPos airBlock2 = blockPos.add(0, 2, 0);

		boolean crystal = mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal)
				.filter(e -> mc.player.getDistance(e) <= breakRange.getValue()).filter(this::crystalCheck)
				.map(entity -> (EntityEnderCrystal) entity).min(Comparator.comparing(c -> mc.player.getDistance(c)))
				.orElse(null) != null;

		if (mode113.isEnabled()) {
			return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
					|| mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)
					&& mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(airBlock1)).isEmpty()
					&& mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(airBlock2)).isEmpty();
		}

		if (!multiplace.isEnabled() && !highPing.isEnabled() && !crystal) {
			return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
					|| mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)
					&& mc.world.getBlockState(airBlock1).getBlock() == Blocks.AIR
					&& mc.world.getBlockState(airBlock2).getBlock() == Blocks.AIR
					&& mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(airBlock1)).isEmpty()
					&& mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(airBlock2)).isEmpty();
		} else if (!multiplace.isEnabled() && !highPing.isEnabled() && crystal)
			return false;

		if (multiplace.isEnabled() && !multiplacePlus.isEnabled()
				&& PlacedCrystals.size() > multiplaceValue.getValue()) {
			return false;
		} else if ((multiplace.isEnabled() && PlacedCrystals.size() <= multiplaceValue.getValue())
				|| (multiplace.isEnabled() && multiplacePlus.isEnabled())) {
			return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
					|| mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)
					&& mc.world.getBlockState(airBlock1).getBlock() == Blocks.AIR
					&& mc.world.getBlockState(airBlock2).getBlock() == Blocks.AIR
					&& mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(airBlock1)).isEmpty()
					&& mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(airBlock2)).isEmpty();
		}

		return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
				|| mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)
				&& mc.world.getBlockState(airBlock1).getBlock() == Blocks.AIR
				&& mc.world.getBlockState(airBlock2).getBlock() == Blocks.AIR
				&& mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(airBlock1)).isEmpty()
				&& mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(airBlock2)).isEmpty();
	}

	private List<BlockPos> findCrystalBlocks() {
		NonNullList<BlockPos> positions = NonNullList.create();
		positions.addAll(getSphere(getPlayerPos(), placeRange.getValue(), (int) placeRange.getValue(), false, true, 0)
				.stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
		return positions;
	}

	private boolean crystalCheck(Entity crystal) {

		if (!(crystal instanceof EntityEnderCrystal)) {
			return false;
		}

		if (breakMode.getMode().equalsIgnoreCase("All")) {
			return true;
		} else if (breakMode.getMode().equalsIgnoreCase("Own")) {
			for (BlockPos pos : new ArrayList<>(PlacedCrystals)) {
				if (pos != null && pos.getDistance((int) crystal.posX, (int) crystal.posY, (int) crystal.posZ) <= 3.0) {
					return true;
				}
			}
		} else if (breakMode.getMode().equalsIgnoreCase("Smart")) {
			EntityLivingBase target = renderEnt != null ? (EntityLivingBase) renderEnt : GetNearTarget(crystal);

			if (target == null || target == mc.player) {
				return false;
			}

			float targetDmg = calculateDamage(crystal.posX + 0.5, crystal.posY + 1, crystal.posZ + 0.5, target);

			return targetDmg >= minDmg.getValue()
					|| (targetDmg > minDmg.getValue()) && target.getHealth() > facePlaceValue.getValue();
		}

		return false;
	}

	private boolean validTarget(Entity entity) {
		if ((entity == null) || !(entity instanceof EntityLivingBase) || entity.isDead
				|| ((EntityLivingBase) entity).getHealth() <= 0.0F)
			return false;

		if (entity instanceof EntityPlayer) {
			return entity != mc.player;
		}

		return false;
	}

	private EntityLivingBase GetNearTarget(Entity distanceTarget) {
		return mc.world.loadedEntityList.stream().filter(this::validTarget).map(entity -> (EntityLivingBase) entity)
				.min(Comparator.comparing(distanceTarget::getDistance)).orElse(null);
	}

	private static float getDamageMultiplied(float damage) {
		int diff = mc.world.getDifficulty().getId();
		return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
	}

	public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
		float doubleExplosionSize = 12.0F;
		double distancedsize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
		Vec3d vec3d = new Vec3d(posX, posY, posZ);
		double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
		double v = (1.0D - distancedsize) * blockDensity;
		float damage = ((int) ((v * v + v) / 2.0D * 7.0D * doubleExplosionSize + 1.0D));
		double finald = 1.0D;

		if (entity instanceof EntityLivingBase) {
			finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage),
					new Explosion(mc.world, null, posX, posY, posZ, 6F, false, true));
		}
		return (float) finald;
	}

	public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer ep = (EntityPlayer) entity;
			DamageSource ds = DamageSource.causeExplosionDamage(explosion);
			damage = CombatRules.getDamageAfterAbsorb(damage, ep.getTotalArmorValue(),
					(float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

			int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
			float f = MathHelper.clamp(k, 0.0F, 20.0F);
			damage *= 1.0F - f / 25.0F;

			if (entity.isPotionActive(Objects.requireNonNull(Potion.getPotionById(11)))) {
				damage = damage - (damage / 4);
			}
			damage = Math.max(damage, 0.0F);
			return damage;
		}
		damage = CombatRules.getDamageAfterAbsorb(damage, entity.getTotalArmorValue(),
				(float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
		return damage;
	}

	public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
		List<BlockPos> circleblocks = new ArrayList<>();
		int cx = loc.getX();
		int cy = loc.getY();
		int cz = loc.getZ();
		for (int x = cx - (int) r; x <= cx + r; x++) {
			for (int z = cz - (int) r; z <= cz + r; z++) {
				for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
						BlockPos l = new BlockPos(x, y + plus_y, z);
						circleblocks.add(l);
					}
				}
			}
		}
		return circleblocks;
	}

	public static BlockPos getPlayerPos() {
		return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
	}

	private static void resetRotation() {
		if (isSpoofingAngles) {
			yaw = mc.player.rotationYaw;
			pitch = mc.player.rotationPitch;
			isSpoofingAngles = false;
		}
	}

	private static boolean isSpoofingAngles;
	private static float yaw;
	private static float pitch;

	public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
		double dirx = me.posX - px;
		double diry = me.posY - py;
		double dirz = me.posZ - pz;

		double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

		dirx /= len;
		diry /= len;
		dirz /= len;

		double pitch = Math.asin(diry);
		double yaw = Math.atan2(dirz, dirx);

		pitch = pitch * 180.0d / Math.PI;
		yaw = yaw * 180.0d / Math.PI;

		yaw += 90f;

		return new double[] { yaw, pitch };
	}

	private static void setYawAndPitch(float yaw1, float pitch1) {
		yaw = yaw1;
		pitch = pitch1;
		isSpoofingAngles = true;
	}

	private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
		double[] v = calculateLookAt(px, py, pz, me);
		setYawAndPitch((float) v[0], (float) v[1]);
	}

}
