package xyz.quazaros.epfragments73.item.EndPortals;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.player.Player; // PlayerEntity is now often just Player in 26.1
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext; // ItemPlacementContext -> BlockPlaceContext
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes; // Note the package change to core
import net.minecraft.server.level.ServerLevel; // ServerWorld -> ServerLevel
import net.minecraft.world.level.block.SoundType; // BlockSoundGroup -> SoundType
import net.minecraft.sounds.SoundSource; // SoundCategory -> SoundSource
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition; // StateManager -> StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties; // Properties -> BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.InteractionResult; // ActionResult -> InteractionResult
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult; // util.hit -> phys
import net.minecraft.core.BlockPos; // util.math -> core
import net.minecraft.core.Direction; // util.math -> core
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes; // VoxelShapes -> Shapes
import net.minecraft.world.level.BlockGetter; // BlockView -> BlockGetter
import net.minecraft.world.level.Level; // World -> Level
import xyz.quazaros.epfragments73.item.ModItems;

import static xyz.quazaros.epfragments73.item.EndPortals.PortalHelper.tryPortal;

public class AbstractEndPortal extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty EYE = BlockStateProperties.EYE;

    private static final VoxelShape FRAME_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    private static final VoxelShape EYE_SHAPE = Block.box(4.0D, 13.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape FILLED_SHAPE = Shapes.or(FRAME_SHAPE, EYE_SHAPE);

    public String type;

    public AbstractEndPortal(Properties properties, String type) {
        super(properties
                .strength(-1.0F)
                .sound(SoundType.STONE)
                .lightLevel(state -> 1)
        );

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(EYE, false)
        );

        this.type = type;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, EYE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                .setValue(EYE, false);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (!state.getValue(EYE) && stack.is(getItem())) {
            if (!level.isClientSide()) {

                level.setBlock(pos, state.setValue(EYE, true), Block.UPDATE_ALL);

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                level.playSound(
                        null,
                        pos,
                        SoundEvents.END_PORTAL_FRAME_FILL,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                );

                if (level instanceof ServerLevel serverLevel) {
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.65;
                    double z = pos.getZ() + 0.5;

                    serverLevel.sendParticles(
                            ParticleTypes.SMOKE,
                            x, y, z, 20, 0.1, 0.2, 0.1, 0.0);
                }

                tryPortal(level, pos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(EYE) ? FILLED_SHAPE : FRAME_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(EYE) ? FILLED_SHAPE : FRAME_SHAPE;
    }

    private Item getItem() {
        return switch (this.type) {
            case "ruined" -> ModItems.RUINED_EYE;
            case "golden" -> ModItems.GOLDEN_EYE;
            case "ancient" -> ModItems.ANCIENT_EYE;
            case "dark" -> ModItems.DARK_EYE;
            case "withered" -> ModItems.WITHERED_EYE;
            case "gusty" -> ModItems.GUSTY_EYE;
            case "wealthy" -> ModItems.WEALTHY_EYE;
            case "sandy" -> ModItems.SANDY_EYE;
            default -> null;
        };
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction side) {
        return state.getValue(EYE) ? 15 : 0;
    }
}