package xyz.quazaros.epfragments73.item.EndPortals;

import net.minecraft.core.BlockPos;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.Arrays;

import static xyz.quazaros.epfragments73.item.EndPortals.AbstractEndPortal.EYE;

public class PortalHelper {

    public static boolean tryPortal(Level world, BlockPos pos) {

        // 8x8 search area
        ArrayList<BlockPos> blockArea = new ArrayList<>();

        for (int x = pos.getX() - 4; x <= pos.getX() + 4; x++) {
            for (int z = pos.getZ() - 4; z <= pos.getZ() + 4; z++) {
                blockArea.add(new BlockPos(x, pos.getY(), z));
            }
        }

        // find normal end portal frames
        ArrayList<BlockPos> normalFrames = new ArrayList<>();

        for (BlockPos blockPos : blockArea) {
            if (world.getBlockState(blockPos).getBlock() == Blocks.END_PORTAL_FRAME) {
                normalFrames.add(blockPos);
            }
        }

        if (normalFrames.size() < 4) {
            return false;
        }

        int top = normalFrames.get(0).getX();
        int bottom = normalFrames.get(0).getX();
        int right = normalFrames.get(0).getZ();
        int left = normalFrames.get(0).getZ();

        for (BlockPos blockPos : normalFrames) {
            if (blockPos.getX() > top) top = blockPos.getX();
            if (blockPos.getX() < bottom) bottom = blockPos.getX();
            if (blockPos.getZ() > right) right = blockPos.getZ();
            if (blockPos.getZ() < left) left = blockPos.getZ();
        }

        ArrayList<BlockPos> limitBlockArea = new ArrayList<>();

        for (BlockPos blockPos : blockArea) {
            if (blockPos.getX() > top) continue;
            if (blockPos.getX() < bottom) continue;
            if (blockPos.getZ() > right) continue;
            if (blockPos.getZ() < left) continue;
            limitBlockArea.add(blockPos);
        }

        ArrayList<BlockPos> allFrames = new ArrayList<>();
        allFrames.add(limitBlockArea.get(1));
        allFrames.add(limitBlockArea.get(3));
        allFrames.add(limitBlockArea.get(5));
        allFrames.add(limitBlockArea.get(9));
        allFrames.add(limitBlockArea.get(15));
        allFrames.add(limitBlockArea.get(19));
        allFrames.add(limitBlockArea.get(21));
        allFrames.add(limitBlockArea.get(23));

        ArrayList<String> types = new ArrayList<>(
                Arrays.asList("ruined", "golden", "ancient", "dark", "withered", "gusty", "wealthy", "sandy")
        );

        for (BlockPos blockPos : allFrames) {

            BlockState state = world.getBlockState(blockPos);

            if (!(state.getBlock() instanceof AbstractEndPortal)) {
                return false;
            }

            if (!state.getValue(EYE)) {
                return false;
            }

            types.remove(((AbstractEndPortal) state.getBlock()).type);
        }

        for (BlockPos blockPos : normalFrames) {

            BlockState state = world.getBlockState(blockPos);

            if (blockPos.equals(pos)) continue;
            if (!state.getValue(EYE)) return false;
        }

        if (!types.isEmpty()) return false;

        ArrayList<BlockPos> portalPos = new ArrayList<>();
        portalPos.add(limitBlockArea.get(6));
        portalPos.add(limitBlockArea.get(7));
        portalPos.add(limitBlockArea.get(8));
        portalPos.add(limitBlockArea.get(11));
        portalPos.add(limitBlockArea.get(12));
        portalPos.add(limitBlockArea.get(13));
        portalPos.add(limitBlockArea.get(16));
        portalPos.add(limitBlockArea.get(17));
        portalPos.add(limitBlockArea.get(18));

        for (BlockPos blockPos : portalPos) {
            world.setBlock(blockPos, Blocks.END_PORTAL.defaultBlockState(), 3);
        }

        world.playSound(
                null,
                portalPos.get(4),
                SoundEvents.END_PORTAL_SPAWN,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );

        return true;
    }
}