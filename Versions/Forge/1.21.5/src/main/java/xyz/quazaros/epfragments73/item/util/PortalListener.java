package xyz.quazaros.epfragments73.item.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.quazaros.epfragments73.epfragments73;

import static xyz.quazaros.epfragments73.item.EndPortals.PortalHelper.tryPortal;

@Mod.EventBusSubscriber(modid = epfragments73.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PortalListener {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;

        BlockPos pos = event.getPos();
        BlockState state = event.getLevel().getBlockState(pos);
        Player p = event.getEntity();

        if (!state.is(Blocks.END_PORTAL_FRAME)) return;
        if (!p.getItemInHand(InteractionHand.MAIN_HAND).is(Items.ENDER_EYE)) return;

        tryPortal(event.getLevel(), pos);
    }
}