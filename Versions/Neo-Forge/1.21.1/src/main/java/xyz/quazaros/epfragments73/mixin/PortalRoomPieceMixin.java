package xyz.quazaros.epfragments73.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.WorldGenLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.quazaros.epfragments73.item.ModBlocks;

import java.util.ArrayList;

@Mixin(StrongholdPieces.PortalRoom.class)
public abstract class PortalRoomPieceMixin extends StructurePiece {

    public PortalRoomPieceMixin(StructurePieceSerializationContext ctx, BoundingBox boundingBox) {
        super(null, 0, boundingBox);
    }

    @Inject(
            method = "postProcess",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyPortalGeneration(
            WorldGenLevel level,
            StructureManager structureManager,
            ChunkGenerator chunkGenerator,
            RandomSource random,
            BoundingBox chunkBox,
            ChunkPos chunkPos,
            BlockPos pivot,
            CallbackInfo ci
    ) {
        generateCustomPortalFrames(level, chunkBox);
        ci.cancel();
    }

    private void generateCustomPortalFrames(WorldGenLevel level, BoundingBox chunkBox) {
        int cx = 5;
        int cz = 10;

        ArrayList<Direction> directions = getDirection(this.getOrientation() != null
                ? this.getOrientation()
                : Direction.NORTH);

        // NORTH SIDE (facing SOUTH)
        placeCustomBlock(level, cx - 1, 3, cz + 2, directions.get(0), chunkBox, "ancient");
        placePortalFrame(level, cx,     3, cz + 2, directions.get(0), chunkBox);
        placeCustomBlock(level, cx + 1, 3, cz + 2, directions.get(0), chunkBox, "dark");

        // SOUTH SIDE (facing NORTH)
        placeCustomBlock(level, cx - 1, 3, cz - 2, directions.get(1), chunkBox, "ruined");
        placePortalFrame(level, cx,     3, cz - 2, directions.get(1), chunkBox);
        placeCustomBlock(level, cx + 1, 3, cz - 2, directions.get(1), chunkBox, "sandy");

        // WEST SIDE (facing EAST)
        placeCustomBlock(level, cx - 2, 3, cz - 1, directions.get(2), chunkBox, "golden");
        placePortalFrame(level, cx - 2, 3, cz,     directions.get(2), chunkBox);
        placeCustomBlock(level, cx - 2, 3, cz + 1, directions.get(2), chunkBox, "gusty");

        // EAST SIDE (facing WEST)
        placeCustomBlock(level, cx + 2, 3, cz - 1, directions.get(3), chunkBox, "wealthy");
        placePortalFrame(level, cx + 2, 3, cz,     directions.get(3), chunkBox);
        placeCustomBlock(level, cx + 2, 3, cz + 1, directions.get(3), chunkBox, "withered");
    }

    private ArrayList<String> getPortals(Direction facing) {
        ArrayList<String> portals = new ArrayList<>();
        if (facing == Direction.NORTH ||  facing == Direction.SOUTH) {
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
            WorldGenLevel level,
            int x, int y, int z,
            Direction facing,
            BoundingBox box
    ) {
        BlockPos pos = this.getWorldPos(x, y, z);
        if (box.isInside(pos)) {
            level.setBlock(
                    pos,
                    Blocks.END_PORTAL_FRAME.defaultBlockState()
                            .setValue(EndPortalFrameBlock.FACING, facing)
                            .setValue(EndPortalFrameBlock.HAS_EYE, false),
                    Block.UPDATE_ALL
            );
        }
    }

    private void placeCustomBlock(
            WorldGenLevel level,
            int x, int y, int z,
            Direction facing,
            BoundingBox box,
            String type
    ) {
        BlockPos pos = this.getWorldPos(x, y, z);
        if (box.isInside(pos)) {
            Block tempPortal = getEndPortal(type);
            BlockState state = tempPortal.defaultBlockState()
                    .setValue(EndPortalFrameBlock.FACING, facing);
            level.setBlock(pos, state, Block.UPDATE_ALL);
        }
    }

    private Block getEndPortal(String type) {
        return switch (type) {
            case "ruined"   -> ModBlocks.RUINED_ENDPORTAL_FRAME.get();
            case "golden"   -> ModBlocks.GOLDEN_ENDPORTAL_FRAME.get();
            case "ancient"  -> ModBlocks.ANCIENT_ENDPORTAL_FRAME.get();
            case "dark"     -> ModBlocks.DARK_ENDPORTAL_FRAME.get();
            case "withered" -> ModBlocks.WITHERED_ENDPORTAL_FRAME.get();
            case "gusty"    -> ModBlocks.GUSTY_ENDPORTAL_FRAME.get();
            case "wealthy"  -> ModBlocks.WEALTHY_ENDPORTAL_FRAME.get();
            case "sandy"    -> ModBlocks.SANDY_ENDPORTAL_FRAME.get();
            default         -> null;
        };
    }

    private ArrayList<Direction> getDirection(Direction facing) {
        ArrayList<Direction> directions = new ArrayList<>();
        switch (facing) {
            case NORTH -> { directions.add(Direction.SOUTH); directions.add(Direction.NORTH);
                directions.add(Direction.WEST);  directions.add(Direction.EAST); }
            case EAST  -> { directions.add(Direction.WEST);  directions.add(Direction.EAST);
                directions.add(Direction.NORTH); directions.add(Direction.SOUTH); }
            case SOUTH -> { directions.add(Direction.NORTH); directions.add(Direction.SOUTH);
                directions.add(Direction.EAST);  directions.add(Direction.WEST); }
            case WEST  -> { directions.add(Direction.EAST);  directions.add(Direction.WEST);
                directions.add(Direction.SOUTH); directions.add(Direction.NORTH); }
        }
        return directions;
    }
}