package xyz.quazaros.epfragments73.item.util;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;

import xyz.quazaros.epfragments73.item.EndPortals.PortalHelper;

public class PortalListener {

    public static void registerPortalListener() {

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            if (world.isClientSide()) {
                return InteractionResult.PASS;
            }

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);

            if (!state.is(Blocks.END_PORTAL_FRAME)) {
                return InteractionResult.PASS;
            }

            boolean success = PortalHelper.tryPortal(world, pos);

            // optional: consume interaction if portal triggered
            return success ? InteractionResult.SUCCESS : InteractionResult.PASS;
        });
    }
}