package mod.hitlerhax.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityUtil implements Globals {

	public static BlockPos getPlayerPosWithEntity() {
		return new BlockPos(
				(mc.player.getRidingEntity() != null) ? EntityUtil.mc.player.getRidingEntity().posX
						: EntityUtil.mc.player.posX,
				(EntityUtil.mc.player.getRidingEntity() != null) ? EntityUtil.mc.player.getRidingEntity().posY
						: EntityUtil.mc.player.posY,
				(EntityUtil.mc.player.getRidingEntity() != null) ? EntityUtil.mc.player.getRidingEntity().posZ
						: EntityUtil.mc.player.posZ);
	}
	
	

	public static boolean isPassive(Entity e) {
		if (e instanceof EntityWolf && ((EntityWolf) e).isAngry())
			return false;
		if (e instanceof EntityAgeable || e instanceof EntityAmbientCreature || e instanceof EntitySquid)
			return true;
		return e instanceof EntityIronGolem && ((EntityIronGolem) e).getRevengeTarget() == null;
	}

	public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
		return new Vec3d((entity.posX - entity.lastTickPosX) * x, 0 * y, (entity.posZ - entity.lastTickPosZ) * z);
	}

	public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
		return getInterpolatedAmount(entity, ticks, ticks, ticks);
	}

	public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
		return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)
				.add(getInterpolatedAmount(entity, ticks));
	}

    public static boolean isIntercepted(BlockPos pos) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox()))
                return true;
        }

        return false;
    }


	public static boolean isNeutralMob(Entity entity) {
		return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
	}

	public static boolean isHostileMob(Entity entity) {
		return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !EntityUtil.isNeutralMob(entity));
	}
	
	 public static float getArmor(EntityPlayer target) {
	        float armorDurability = 0;
	        for (ItemStack stack : target.getArmorInventoryList()) {
	            if (stack == null || stack.getItem() == Items.AIR)
	                continue;

	            armorDurability += ((float) (stack.getMaxDamage() - stack.getItemDamage()) / (float) stack.getMaxDamage()) * 100.0f;
	        }

	        return armorDurability;
	    }

}
