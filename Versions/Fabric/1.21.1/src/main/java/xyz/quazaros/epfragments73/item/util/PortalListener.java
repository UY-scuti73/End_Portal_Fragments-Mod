package xyz.quazaros.epfragments73.item.util;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import static xyz.quazaros.epfragments73.item.EndPortals.PortalHelper.tryPortal;

public class PortalListener {
    public static void registerPortalListener() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient()) return ActionResult.PASS;
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);

            if (!state.isOf(Blocks.END_PORTAL_FRAME)) {
                return ActionResult.PASS;
            }
            if (!player.getInventory().getMainHandStack().isOf(Items.ENDER_EYE)) {
                return ActionResult.PASS;
            }

            tryPortal(world, pos);

            return ActionResult.PASS;
        });
    }
}
