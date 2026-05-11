package xyz.quazaros.epfragments73.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.quazaros.epfragments73.item.ModBlocks;

import java.util.ArrayList;

@Mixin(StrongholdPieces.PortalRoom.class)
public abstract class PortalRoomPieceMixin extends StructurePiece {

    /**
     * This constructor is required for the Java compiler because StructurePiece
     * does not have a zero-argument constructor.
     * Mixin will ignore this at runtime during the injection process.
     */
    protected PortalRoomPieceMixin(StructurePieceType type, CompoundTag tag) {
        super(type, tag);
    }

    @Inject(
            method = "postProcess",
            at = @At("TAIL"),
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
        // Since we 'extend' StructurePiece, we can call these vanilla methods directly
        epf$generateCustomPortalFrames(level, chunkBox);

        // Stop the original room generation (stops vanilla frames and the portal block)
        ci.cancel();
    }

    @Unique
    private void epf$generateCustomPortalFrames(WorldGenLevel level, BoundingBox chunkBox) {
        int cx = 5;
        int cz = 10;

        Direction facing = this.getOrientation() != null
                ? this.getOrientation()
                : Direction.NORTH;

        ArrayList<Direction> directions = epf$getDirection(facing);
        ArrayList<String> portals = epf$getPortals(facing);

        // NORTH SIDE (facing SOUTH)
        epf$placeCustomBlock(level, cx - 1, 3, cz + 2, directions.get(0), chunkBox, portals.get(0));
        epf$placePortalFrame(level, cx,     3, cz + 2, directions.get(0), chunkBox);
        epf$placeCustomBlock(level, cx + 1, 3, cz + 2, directions.get(0), chunkBox, portals.get(1));

        // SOUTH SIDE (facing NORTH)
        epf$placeCustomBlock(level, cx - 1, 3, cz - 2, directions.get(1), chunkBox, portals.get(2));
        epf$placePortalFrame(level, cx,     3, cz - 2, directions.get(1), chunkBox);
        epf$placeCustomBlock(level, cx + 1, 3, cz - 2, directions.get(1), chunkBox, portals.get(3));

        // WEST SIDE (facing EAST)
        epf$placeCustomBlock(level, cx - 2, 3, cz - 1, directions.get(2), chunkBox, portals.get(4));
        epf$placePortalFrame(level, cx - 2, 3, cz,     directions.get(2), chunkBox);
        epf$placeCustomBlock(level, cx - 2, 3, cz + 1, directions.get(2), chunkBox, portals.get(5));

        // EAST SIDE (facing WEST)
        epf$placeCustomBlock(level, cx + 2, 3, cz - 1, directions.get(3), chunkBox, portals.get(6));
        epf$placePortalFrame(level, cx + 2, 3, cz,     directions.get(3), chunkBox);
        epf$placeCustomBlock(level, cx + 2, 3, cz + 1, directions.get(3), chunkBox, portals.get(7));
    }

    @Unique
    private ArrayList<String> epf$getPortals(Direction facing) {
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

    @Unique
    private void epf$placePortalFrame(WorldGenLevel level, int x, int y, int z, Direction facing, BoundingBox box) {
        // Using this.getWorldPos() directly from parent
        BlockPos pos = this.getWorldPos(x, y, z);
        if (box.isInside(pos)) {
            level.setBlock(
                    pos,
                    Blocks.END_PORTAL_FRAME.defaultBlockState()
                            .setValue(EndPortalFrameBlock.FACING, facing)
                            .setValue(EndPortalFrameBlock.HAS_EYE, false),
                    3
            );
        }
    }

    @Unique
    private void epf$placeCustomBlock(WorldGenLevel level, int x, int y, int z, Direction facing, BoundingBox box, String type) {
        BlockPos pos = this.getWorldPos(x, y, z);
        if (box.isInside(pos)) {
            Block tempPortal = epf$getEndPortal(type);
            if (tempPortal != null) {
                BlockState state = tempPortal.defaultBlockState()
                        .setValue(EndPortalFrameBlock.FACING, facing);
                level.setBlock(pos, state, 3);
            }
        }
    }

    @Unique
    private Block epf$getEndPortal(String type) {
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

    @Unique
    private ArrayList<Direction> epf$getDirection(Direction facing) {
        ArrayList<Direction> directions = new ArrayList<>();
        if (facing == Direction.NORTH) {
            directions.add(Direction.SOUTH); directions.add(Direction.NORTH);
            directions.add(Direction.WEST);  directions.add(Direction.EAST);
        } else if (facing == Direction.EAST) {
            directions.add(Direction.WEST);  directions.add(Direction.EAST);
            directions.add(Direction.NORTH); directions.add(Direction.SOUTH);
        } else if (facing == Direction.SOUTH) {
            directions.add(Direction.NORTH); directions.add(Direction.SOUTH);
            directions.add(Direction.EAST);  directions.add(Direction.WEST);
        } else if (facing == Direction.WEST) {
            directions.add(Direction.EAST);  directions.add(Direction.WEST);
            directions.add(Direction.SOUTH); directions.add(Direction.NORTH);
        }
        return directions;
    }
}