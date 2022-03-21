package mod.hitlerhax.module.modules.render;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import mod.hitlerhax.event.events.HtlrEventRender;
import mod.hitlerhax.module.Category;
import mod.hitlerhax.module.Module;
import mod.hitlerhax.setting.settings.BooleanSetting;
import mod.hitlerhax.setting.settings.ColorSetting;
import mod.hitlerhax.setting.settings.FloatSetting;
import mod.hitlerhax.util.render.ColorUtil;
import mod.hitlerhax.util.render.Geometry;
import mod.hitlerhax.util.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class HoleEsp extends Module {

	public final FloatSetting size = new FloatSetting("size", this, 0.1f);
	public final BooleanSetting outline = new BooleanSetting("outline", this, true);

	public final ColorSetting obbyColor = new ColorSetting("obbyColor", this, new ColorUtil(69, 48, 146, 110));
	public final ColorSetting bedrockColor = new ColorSetting("bedrockColor", this, new ColorUtil(0, 255, 0, 110));

	public HoleEsp() {
		super("HoleEsp", "Shows safe holes", Category.RENDER);

		addSetting(size);
		addSetting(outline);

	}

	private final BlockPos[] surroundOffset = { new BlockPos(0, -1, 0), // down
			new BlockPos(0, 0, -1), // north
			new BlockPos(1, 0, 0), // east
			new BlockPos(0, 0, 1), // south
			new BlockPos(-1, 0, 0) // west
	};

	private ConcurrentHashMap<BlockPos, Boolean> safeHoles;

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

	@Override
	public void onUpdate() {
		if (safeHoles == null) {
			safeHoles = new ConcurrentHashMap<>();
		} else {
			safeHoles.clear();
		}

		int range = (int) Math.ceil(8);

		List<BlockPos> blockPosList = getSphere(getPlayerPos(), range, range, false, true, 0);
		for (BlockPos pos : blockPosList) {

			if (!mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
				continue;
			}
			if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
				continue;
			}
			if (!mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
				continue;
			}

			boolean isSafe = true;
			boolean isBedrock = true;

			for (BlockPos offset : surroundOffset) {
				Block block = mc.world.getBlockState(pos.add(offset)).getBlock();
				if (block != Blocks.BEDROCK) {
					isBedrock = false;
				}
				if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST
						&& block != Blocks.ANVIL) {
					isSafe = false;
					break;
				}
			}
			if (isSafe) {
				safeHoles.put(pos, isBedrock);
			}
		}

	}

	@Override
	public void render(HtlrEventRender event) {
		if (mc.player == null || safeHoles == null) {
			return;
		}
		if (safeHoles.isEmpty()) {
			return;
		}

		safeHoles.forEach(this::drawBox);
		safeHoles.forEach(this::drawOutline);
	}

	private ColorUtil getColor(boolean isBedrock) {
		ColorUtil c;
		if (isBedrock)
			c = bedrockColor.getColor();
		else
			c = obbyColor.getColor();
		return new ColorUtil(c);
	}

	private void drawBox(BlockPos blockPos, boolean isBedrock) {
		ColorUtil color = getColor(isBedrock);
		RenderUtil.drawBox(blockPos, size.getValue(), color, Geometry.Quad.ALL);
	}

	private void drawOutline(BlockPos blockPos, boolean isBedrock) {
		ColorUtil color = getColor(isBedrock);
		if (outline.isEnabled()) {
			RenderUtil.drawBoundingBox(blockPos, size.getValue(), 2, color);
		}
	}

}
