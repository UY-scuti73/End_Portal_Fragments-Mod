package xyz.quazaros.epfragments73.strongholdGeneration;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.*;

import net.minecraft.core.Direction;

import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import xyz.quazaros.epfragments73.item.ModBlocks;

public class StrongholdPortalPatcher {

    public static void register() {
        ServerChunkEvents.CHUNK_LOAD.register((level, chunk, state) -> {
            if (!(level instanceof ServerLevel server)) return;

            var structureRegistry = server.registryAccess().lookupOrThrow(Registries.STRUCTURE);
            Structure stronghold = structureRegistry.get(BuiltinStructures.STRONGHOLD)
                    .map(holder -> holder.value())
                    .orElse(null);

            if (stronghold == null) return;

            var start = server.structureManager().getStructureAt(chunk.getPos().getWorldPosition(), stronghold);

            if (start.isValid() && isChunkThePortalChunk(start, chunk.getPos())) {
                patchStronghold(server, start);
            }
        });
    }

    private static boolean isChunkThePortalChunk(StructureStart start, ChunkPos pos) {
        return start.getChunkPos().equals(pos);
    }

    private static void patchStronghold(ServerLevel world, StructureStart start) {
        for (StructurePiece piece : start.getPieces()) {
            if (piece.getType() == StructurePieceType.STRONGHOLD_PORTAL_ROOM) {
                // This is the actual center of the room where the spawner usually is
                BlockPos roomCenter = piece.getBoundingBox().getCenter();

                // We pass the center and the piece's actual box to ensure isInside() works
                generateCustomPortalFrames(world, roomCenter, piece.getBoundingBox());
                return;
            }
        }
    }

    private static void generateCustomPortalFrames(ServerLevel world, BlockPos center, BoundingBox roomBox) {
        int cx = center.getX();
        int cy = center.getY();
        int cz = center.getZ();

        // Use the actual Y of the room.
        // Usually, the frame is 1 or 2 blocks below the 'center' point of the piece's box.
        // Let's assume the frame floor is at cy - 1.
        int frameY = cy - 1;

        // NORTH (Face South)
        placeCustomBlock(world, cx - 1, frameY, cz + 2, Direction.SOUTH, roomBox, "ancient");
        placePortalFrame(world, cx, frameY, cz + 2, Direction.SOUTH, roomBox);
        placeCustomBlock(world, cx + 1, frameY, cz + 2, Direction.SOUTH, roomBox, "dark");

        // SOUTH (Face North)
        placeCustomBlock(world, cx - 1, frameY, cz - 2, Direction.NORTH, roomBox, "ruined");
        placePortalFrame(world, cx, frameY, cz - 2, Direction.NORTH, roomBox);
        placeCustomBlock(world, cx + 1, frameY, cz - 2, Direction.NORTH, roomBox, "sandy");

        // WEST (Face East)
        placeCustomBlock(world, cx - 2, frameY, cz - 1, Direction.EAST, roomBox, "golden");
        placePortalFrame(world, cx - 2, frameY, cz, Direction.EAST, roomBox);
        placeCustomBlock(world, cx - 2, frameY, cz + 1, Direction.EAST, roomBox, "gusty");

        // EAST (Face West)
        placeCustomBlock(world, cx + 2, frameY, cz - 1, Direction.WEST, roomBox, "wealthy");
        placePortalFrame(world, cx + 2, frameY, cz, Direction.WEST, roomBox);
        placeCustomBlock(world, cx + 2, frameY, cz + 1, Direction.WEST, roomBox, "withered");
    }

    private static void placePortalFrame(ServerLevel world, int x, int y, int z,
                                         Direction facing, BoundingBox box) {

        BlockPos pos = new BlockPos(x, y, z);

        if (!box.isInside(pos)) return;

        BlockState state = Blocks.END_PORTAL_FRAME.defaultBlockState()
                .setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING, facing)
                .setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.EYE, false);

        world.setBlock(pos, state, Block.UPDATE_ALL);
    }

    private static void placeCustomBlock(ServerLevel world, int x, int y, int z,
                                         Direction facing, BoundingBox box, String type) {

        BlockPos pos = new BlockPos(x, y, z);

        if (!box.isInside(pos)) return;

        Block block = getEndPortal(type);
        if (block == null) return;

        BlockState state = block.defaultBlockState()
                .setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING, facing);

        world.setBlock(pos, state, Block.UPDATE_ALL);
    }

    private static Block getEndPortal(String type) {
        return switch (type) {
            case "ruined" -> ModBlocks.RUINED_ENDPORTAL_FRAME;
            case "golden" -> ModBlocks.GOLDEN_ENDPORTAL_FRAME;
            case "ancient" -> ModBlocks.ANCIENT_ENDPORTAL_FRAME;
            case "dark" -> ModBlocks.DARK_ENDPORTAL_FRAME;
            case "withered" -> ModBlocks.WITHERED_ENDPORTAL_FRAME;
            case "gusty" -> ModBlocks.GUSTY_ENDPORTAL_FRAME;
            case "wealthy" -> ModBlocks.WEALTHY_ENDPORTAL_FRAME;
            case "sandy" -> ModBlocks.SANDY_ENDPORTAL_FRAME;
            default -> null;
        };
    }
}