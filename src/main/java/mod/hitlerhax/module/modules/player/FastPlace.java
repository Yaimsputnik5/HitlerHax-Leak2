package mod.hitlerhax.module.modules.player;

import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.IntSetting;
import mod.hitlerhax.util.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Items;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", "Allows you to place blocks & crystals faster", Category.PLAYER);
        
        addSetting(delay);
        addSetting(blocks);
        addSetting(crystal);
        addSetting(fireworks);
        addSetting(spawnEggs);
    }

    final IntSetting delay = new IntSetting("Delay", this, 1);
    final BooleanSetting blocks = new BooleanSetting("Blocks", this, false);
    final BooleanSetting crystal = new BooleanSetting("Crystals", this, true);
    final BooleanSetting fireworks = new BooleanSetting("Fireworks", this, false);
    final BooleanSetting spawnEggs = new BooleanSetting("Spawn Eggs", this, false);

     
    

    @Override
    public void onUpdate() {
        if (InventoryUtil.getHeldItem(Items.END_CRYSTAL) && crystal.isEnabled() || InventoryUtil.getHeldItem(Items.FIREWORKS) && fireworks.isEnabled() || InventoryUtil.getHeldItem(Items.SPAWN_EGG) && spawnEggs.isEnabled())
            mc.rightClickDelayTimer = delay.value;

        if (Block.getBlockFromItem(mc.player.getHeldItemMainhand().getItem()).getDefaultState().isFullBlock() && blocks.isEnabled())
            mc.rightClickDelayTimer = delay.value;
    }
}
