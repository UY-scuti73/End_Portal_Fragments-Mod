package xyz.quazaros.epfragments73.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StrongholdGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.quazaros.epfragments73.item.ModBlocks;

import java.util.ArrayList;

@Mixin(StrongholdGenerator.PortalRoom.class)
public abstract class PortalRoomPieceMixin extends StructurePiece {

    public PortalRoomPieceMixin(int length, BlockBox boundingBox) {
        super(null, length, boundingBox);
    }
    @Inject(
            method = "generate",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyPortalGeneration(
            StructureWorldAccess world,
            StructureAccessor structureAccessor,
            ChunkGenerator chunkGenerator,
            Random random,
            BlockBox chunkBox,
            ChunkPos chunkPos,
            BlockPos pivot,
            CallbackInfo ci
    ) {
        generateCustomPortalFrames(world, chunkBox);
        ci.cancel();
    }

    private void generateCustomPortalFrames(
            StructureWorldAccess world,
            BlockBox chunkBox
    ) {
        int cx = 5;
        int cz = 10;

        Direction facing = this.getFacing() != null ? this.getFacing() : Direction.NORTH;

        ArrayList<Direction> directions = getDirection(facing);
        ArrayList<String> portals = getPortals(facing);

        // NORTH SIDE (Z = 8, facing SOUTH)
        placeCustomBlock(world, cx-1, 3, cz+2, directions.get(0), chunkBox, portals.get(0));
        placePortalFrame(world, cx, 3, cz+2, directions.get(0), chunkBox);
        placeCustomBlock(world, cx+1, 3, cz+2, directions.get(0), chunkBox, portals.get(1));

        // SOUTH SIDE (Z = 10, facing NORTH)
        placeCustomBlock(world, cx-1, 3, cz-2, directions.get(1), chunkBox, portals.get(2));
        placePortalFrame(world, cx, 3, cz-2, directions.get(1), chunkBox);
        placeCustomBlock(world, cx+1, 3, cz-2, directions.get(1), chunkBox, portals.get(3));

        // WEST SIDE (X = 3, facing EAST)
        placeCustomBlock(world, cx-2, 3, cz-1, directions.get(2), chunkBox, portals.get(4));
        placePortalFrame(world, cx-2, 3, cz, directions.get(2), chunkBox);
        placeCustomBlock(world, cx-2, 3, cz+1, directions.get(2), chunkBox, portals.get(5));

        // EAST SIDE (X = 5, facing WEST)
        placeCustomBlock(world, cx+2, 3, cz-1, directions.get(3), chunkBox, portals.get(6));
        placePortalFrame(world, cx+2, 3, cz, directions.get(3), chunkBox);
        placeCustomBlock(world, cx+2, 3, cz+1, directions.get(3), chunkBox, portals.get(7));
    }

    private ArrayList<String> getPortals(Direction facing) {
        ArrayList<String> portals = new ArrayList<>();
        if (facing == Direction.WEST ||  facing == Direction.SOUTH) {
            portals.add("ancient");
            portals.add("dark");
            portals.add("ruined");
            portals.add("sandy");
            portals.add("golden");
            portals.add("gusty");
            portals.add("wealthy");
            portals.add("withered");
        } else {
            portals.add("dark");
            portals.add("ancient");
            portals.add("sandy");
            portals.add("ruined");
            portals.add("wealthy");
            portals.add("withered");
            portals.add("golden");
            portals.add("gusty");
        }
        return portals;
    }

    private void placePortalFrame(
            StructureWorldAccess world,
            int x, int y, int z,
            Direction facing,
            BlockBox box
    ) {
        BlockPos pos = this.offsetPos(x, y, z);
        if (box.contains(pos)) {
            world.setBlockState(
                    pos,
                    Blocks.END_PORTAL_FRAME.getDefaultState()
                            .with(net.minecraft.block.EndPortalFrameBlock.FACING, facing)
                            .with(net.minecraft.block.EndPortalFrameBlock.EYE, false),
                    3
            );
        }
    }

    private void placeCustomBlock(
            StructureWorldAccess world,
            int x, int y, int z,
            Direction facing,
            BlockBox box,
            String type
    ) {
        BlockPos pos = this.offsetPos(x, y, z);
        if (box.contains(pos)) {

            Block tempPortal = getEndPortal(type);
            BlockState state = tempPortal.getDefaultState();
            state = state.with(EndPortalFrameBlock.FACING, facing);

            world.setBlockState(
                    pos,
                    state,
                    3
            );
        }
    }

    private Block getEndPortal(String type) {
        switch (type) {
            case "ruined":
                return ModBlocks.RUINED_ENDPORTAL_FRAME;
            case "golden":
                return ModBlocks.GOLDEN_ENDPORTAL_FRAME;
            case "ancient":
                return ModBlocks.ANCIENT_ENDPORTAL_FRAME;
            case "dark":
                return ModBlocks.DARK_ENDPORTAL_FRAME;
            case "withered":
                return ModBlocks.WITHERED_ENDPORTAL_FRAME;
            case "gusty":
                return ModBlocks.GUSTY_ENDPORTAL_FRAME;
            case "wealthy":
                return ModBlocks.WEALTHY_ENDPORTAL_FRAME;
            case "sandy":
                return ModBlocks.SANDY_ENDPORTAL_FRAME;
        }
        return null;
    }

    private ArrayList<Direction> getDirection(Direction facing) {
        ArrayList<Direction> directions = new ArrayList<>();
        switch (facing) {
            case NORTH:
                directions.add(Direction.SOUTH);
                directions.add(Direction.NORTH);
                directions.add(Direction.WEST);
                directions.add(Direction.EAST);
                break;
            case EAST:
                directions.add(Direction.WEST);
                directions.add(Direction.EAST);
                directions.add(Direction.NORTH);
                directions.add(Direction.SOUTH);
                break;
            case SOUTH:
                directions.add(Direction.NORTH);
                directions.add(Direction.SOUTH);
                directions.add(Direction.EAST);
                directions.add(Direction.WEST);
                break;
            case WEST:
                directions.add(Direction.EAST);
                directions.add(Direction.WEST);
                directions.add(Direction.SOUTH);
                directions.add(Direction.NORTH);
                break;
        }
        return directions;
    }
}