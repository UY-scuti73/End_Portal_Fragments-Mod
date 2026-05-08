package xyz.quazaros.epfragments73.item.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import static xyz.quazaros.epfragments73.item.EndPortals.PortalHelper.tryPortal;

public class PortalListener {

    public static void register() {
        PlayerInteractEvent.RightClickBlock.BUS.addListener(PortalListener::onRightClickBlock);
    }

    private static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState state = event.getLevel().getBlockState(pos);

        if (!state.is(Blocks.END_PORTAL_FRAME)) return;

        tryPortal(event.getLevel(), pos);
    }
}