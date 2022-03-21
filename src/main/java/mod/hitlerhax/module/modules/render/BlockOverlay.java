package mod.hitlerhax.module.modules.render;

import mod.hitlerhax.event.events.HtlrEventRender;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.util.BlockUtil;
import mod.hitlerhax.util.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;


public class BlockOverlay extends Module {

	public BlockOverlay() {
		super("BlockOverlay", "Highlights the selected block", Category.RENDER);
		
		
		
	}
	@Override
	public void render(HtlrEventRender event) {
		if (mc.player == null || mc.world == null)
			return;
		
		if (mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            Block block = BlockUtil.getBlock(mc.objectMouseOver.getBlockPos());
            BlockPos blockPos = mc.objectMouseOver.getBlockPos();

            if (Block.getIdFromBlock(block) == 0) {
                return;
            }
            RenderUtil.drawBlockESP(blockPos, 1F, 1F, 1F);
        }
		
		super.onRenderWorldLast(event);
	}

}
