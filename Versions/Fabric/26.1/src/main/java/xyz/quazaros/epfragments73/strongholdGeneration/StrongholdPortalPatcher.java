package xyz.quazaros.epfragments73.strongholdGeneration;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.*;

import net.minecraft.core.Direction;

import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import xyz.quazaros.epfragments73.item.ModBlocks;

import java.util.*;

public class StrongholdPortalPatcher {

    private static final int TICK_DELAY = 20;

    private static final Map<BlockPos, BlockState> BLOCK_QUEUE = new HashMap<>();
    private static final Set<BlockPos> PATCHED_PORTALS = new HashSet<>();

    public static void register() {
        ServerChunkEvents.CHUNK_LOAD.register((level, chunk, state) -> {
            findStronghold(level, chunk);
            loadPortalsInChunk(level, chunk);
        });
    }

    private static void findStronghold(ServerLevel level, LevelChunk chunk) {
        //If not a stronghold then return
        if (!(level instanceof ServerLevel server)) return;
        if (!server.dimension().equals(ServerLevel.OVERWORLD)) return;
        var structureRegistry = server.registryAccess().lookupOrThrow(Registries.STRUCTURE);
        Structure stronghold = structureRegistry.get(BuiltinStructures.STRONGHOLD)
                .map(Holder.Reference::value)
                .orElse(null);
        if (stronghold == null) return;

        SectionPos sectionPos = SectionPos.of(chunk.getPos(), 0);
        var starts = server.structureManager().startsForStructure(sectionPos, stronghold);
        for (StructureStart candidate : starts) {
            if (!candidate.isValid()) continue;
            for (StructurePiece piece : candidate.getPieces()) {
                if (piece.getType() == StructurePieceType.STRONGHOLD_PORTAL_ROOM) {
                    // Get the center of the portal room
                    BlockPos portalCenter = piece.getBoundingBox().getCenter();
                    ChunkPos portalChunk = new ChunkPos(portalCenter.getX() >> 4, portalCenter.getZ() >> 4);

                    if (!portalChunk.equals(chunk.getPos())) {continue;}

                    if (!PATCHED_PORTALS.add(portalCenter)) {
                        return;
                    }

                    BoundingBox box = piece.getBoundingBox();
                    Direction facing = piece.getOrientation();

                    generateCustomPortalFrames(server, box, facing);
                    return;
                }
            }
        }
    }

    private static void loadPortalsInChunk(ServerLevel level, LevelChunk chunk) {
        ChunkPos loadedChunk = chunk.getPos();

        List<BlockPos> toRemove = new ArrayList<>();
        Map<BlockPos, BlockState> toLoad = new HashMap<>();

        for (Map.Entry<BlockPos, BlockState> entry : BLOCK_QUEUE.entrySet()) {
            BlockPos pos = entry.getKey();
            ChunkPos posChunk = new ChunkPos(pos.getX()>>4, pos.getZ()>>4);
            if (!posChunk.equals(loadedChunk)) {
                continue;
            }
            toLoad.put(pos, entry.getValue());
            toRemove.add(pos);
        }

        // Remove processed entries
        for (BlockPos pos : toRemove) {
            BLOCK_QUEUE.remove(pos);
        }

        // Add Blocks
        schedule(level, TICK_DELAY, () -> {
            for (Map.Entry<BlockPos, BlockState> entry : toLoad.entrySet()) {
                loadBlock(entry.getKey(), entry.getValue(), level);
            }
        });
    }

    private static void loadBlock(BlockPos pos, BlockState state, ServerLevel level) {
        level.setBlock(pos, state, 2 | 16);
    }

    private static void generateCustomPortalFrames(ServerLevel world, BoundingBox roomBox, Direction facing) {
        BlockPos center = findEndPortalCenter(roomBox, facing);

        ArrayList<Direction> directions = getDirection(facing);

        int cx = center.getX();
        int cy = center.getY();
        int cz = center.getZ();

        ArrayList<String> portals = getPortals(facing);

        // NORTH SIDE (facing SOUTH)
        placeCustomBlock(world, cx-1, cy, cz+2, directions.get(0), roomBox, portals.get(0));
        placePortalFrame(world, cx, cy, cz+2, directions.get(0), roomBox);
        placeCustomBlock(world, cx+1, cy, cz+2, directions.get(0), roomBox, portals.get(1));

        // SOUTH SIDE (facing NORTH)
        placeCustomBlock(world, cx-1, cy, cz-2, directions.get(1), roomBox, portals.get(2));
        placePortalFrame(world, cx, cy, cz-2, directions.get(1), roomBox);
        placeCustomBlock(world, cx+1, cy, cz-2, directions.get(1), roomBox, portals.get(3));

        // WEST SIDE (facing EAST)
        placeCustomBlock(world, cx-2, cy, cz-1, directions.get(2), roomBox, portals.get(4));
        placePortalFrame(world, cx-2, cy, cz, directions.get(2), roomBox);
        placeCustomBlock(world, cx-2, cy, cz+1, directions.get(2), roomBox, portals.get(5));

        // EAST SIDE (facing WEST)
        placeCustomBlock(world, cx+2, cy, cz-1, directions.get(3), roomBox, portals.get(6));
        placePortalFrame(world, cx+2, cy, cz, directions.get(3), roomBox);
        placeCustomBlock(world, cx+2, cy, cz+1, directions.get(3), roomBox, portals.get(7));
    }

    private static void placePortalFrame(ServerLevel world, int x, int y, int z,
                                         Direction facing, BoundingBox box) {

        BlockPos pos = new BlockPos(x, y, z);

        if (!box.isInside(pos)) return;

        BlockState state = Blocks.END_PORTAL_FRAME.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
                .setValue(BlockStateProperties.EYE, false);

        BLOCK_QUEUE.put(pos, state);
    }

    private static void placeCustomBlock(ServerLevel world, int x, int y, int z,
                                         Direction facing, BoundingBox box, String type) {
        BlockPos pos = new BlockPos(x, y, z);

        Block block = getEndPortal(type);
        if (block == null) {return;}

        BlockState state = block.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, facing);

        BLOCK_QUEUE.put(pos, state);
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

    private static ArrayList<Direction> getDirection(Direction facing) {
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.NORTH);
        directions.add(Direction.SOUTH);
        directions.add(Direction.EAST);
        directions.add(Direction.WEST);
        return directions;
    }

    private static ArrayList<String> getPortals(Direction facing) {
        ArrayList<String> portals = new ArrayList<>();
        switch (facing) {
            case SOUTH:
                portals.add("ancient");
                portals.add("dark");
                portals.add("ruined");
                portals.add("sandy");
                portals.add("golden");
                portals.add("gusty");
                portals.add("wealthy");
                portals.add("withered");
                break;
            case WEST:
                portals.add("withered");
                portals.add("wealthy");
                portals.add("gusty");
                portals.add("golden");
                portals.add("ancient");
                portals.add("dark");
                portals.add("ruined");
                portals.add("sandy");
                break;
            case NORTH:
                portals.add("sandy");
                portals.add("ruined");
                portals.add("dark");
                portals.add("ancient");
                portals.add("withered");
                portals.add("wealthy");
                portals.add("gusty");
                portals.add("golden");
                break;
            case EAST:
                portals.add("golden");
                portals.add("gusty");
                portals.add("wealthy");
                portals.add("withered");
                portals.add("sandy");
                portals.add("ruined");
                portals.add("dark");
                portals.add("ancient");
                break;
        }
        return portals;
    }

    public static Optional<Direction> getRoomFacingFromPortalFrames(ServerLevel world, BoundingBox box, BlockPos structureCenter, BlockPos center) {
        if (center == null) return Optional.empty();

        Direction direction;

        if (center.getX() == structureCenter.getX()) {
            direction = center.getZ() > structureCenter.getZ() ? Direction.SOUTH : Direction.NORTH;
        } else {
            direction = center.getX() > structureCenter.getX() ? Direction.EAST : Direction.WEST;
        }

        return Optional.of(direction);
    }

    public static BlockPos findEndPortalCenter(BoundingBox box, Direction facing) {
        if (facing == null) return null;

        // The relative center of the portal room is always at these coordinates
        int relX = 5;
        int relY = 3;
        int relZ = 10;

        int worldX = box.minX();
        int worldY = box.minY() + relY;
        int worldZ = box.minZ();
        switch (facing) {
            case NORTH -> { worldX = box.minX() + relX; worldZ = box.maxZ() - relZ; }
            case SOUTH -> { worldX = box.minX() + relX; worldZ = box.minZ() + relZ; }
            case WEST  -> { worldX = box.maxX() - relZ; worldZ = box.minZ() + relX; }
            case EAST  -> { worldX = box.minX() + relZ; worldZ = box.minZ() + relX; }
        }

        return new BlockPos(worldX, worldY, worldZ);
    }

    // SCHEDULER

    // Simple scheduled task structure
    private static class ScheduledTask {
        int ticksRemaining;
        Runnable action;
    }

    private static final List<ScheduledTask> TASKS = new ArrayList<>();

    public static void initScheduler() {
        ServerTickEvents.END_SERVER_TICK.register(StrongholdPortalPatcher::onEndServerTick);
    }

    private static void onEndServerTick(MinecraftServer server) {
        if (TASKS.isEmpty()) return;
        // Copy to avoid modification while iterating
        List<ScheduledTask> tasksCopy = new ArrayList<>(TASKS);
        TASKS.clear();
        for (ScheduledTask task : tasksCopy) {
            task.ticksRemaining--;
            if (task.ticksRemaining <= 0) {
                task.action.run();
            } else {
                // re-queue for next tick
                TASKS.add(task);
            }
        }
    }

    private static void schedule(ServerLevel level, int delayTicks, Runnable action) {
        ScheduledTask t = new ScheduledTask();
        t.ticksRemaining = delayTicks;
        t.action = action;
        TASKS.add(t);
    }
}