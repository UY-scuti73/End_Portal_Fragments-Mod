package xyz.quazaros.epfragments73.item.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.world.InteractionResult;

import static xyz.quazaros.epfragments73.item.EndPortals.PortalHelper.tryPortal;

public class PortalListener {

    public static void register() {
        NeoForge.EVENT_BUS.register(new PortalListener());
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState state = event.getLevel().getBlockState(pos);
        Player p = event.getEntity();

        if (!state.is(Blocks.END_PORTAL_FRAME)) return;
        if (!p.getItemInHand(InteractionHand.MAIN_HAND).is(Items.ENDER_EYE)) return;

        tryPortal(event.getLevel(), pos);
    }
}
