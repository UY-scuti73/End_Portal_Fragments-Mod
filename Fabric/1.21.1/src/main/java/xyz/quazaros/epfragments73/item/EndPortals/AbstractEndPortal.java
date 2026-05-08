package xyz.quazaros.epfragments73.item.EndPortals;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import xyz.quazaros.epfragments73.item.ModItems;

import static xyz.quazaros.epfragments73.item.EndPortals.PortalHelper.tryPortal;

public class AbstractEndPortal extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty EYE = Properties.EYE;

    private static final VoxelShape FRAME_SHAPE =
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    private static final VoxelShape EYE_SHAPE =
            Block.createCuboidShape(4.0D, 13.0D, 4.0D,
                    12.0D, 16.0D, 12.0D);
    private static final VoxelShape FILLED_SHAPE = VoxelShapes.union(FRAME_SHAPE, EYE_SHAPE);

    public String type;

    public AbstractEndPortal(Settings settings, String type) {
        super(settings
                .strength(-1.0F)
                .sounds(BlockSoundGroup.STONE)
                .luminance(luminance -> 1)
        );

        this.setDefaultState(this.getStateManager().getDefaultState()
               .with(FACING, Direction.NORTH)
                .with(EYE, false)
        );

        this.type = type;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, EYE);
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getPlayer().getHorizontalFacing().getOpposite())
                .with(EYE, false);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getMainHandStack();
        if (!state.get(EYE) && stack.isOf(getItem())) {
            if (!world.isClient()) {

                world.setBlockState(pos, state.with(EYE, true), Block.NOTIFY_ALL);
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }

                world.playSound(
                        null,
                        pos,
                        SoundEvents.BLOCK_END_PORTAL_FRAME_FILL,
                        SoundCategory.BLOCKS,
                        1.0F,
                        1.0F
                );

                if (!world.isClient()) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.65;
                    double z = pos.getZ() + 0.5;

                    serverWorld.spawnParticles(
                            ParticleTypes.SMOKE,
                            x, y, z, 20, 0.1, 0.2, 0.1, 0.0);
                }

                tryPortal(world, pos);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(EYE) ? FILLED_SHAPE : FRAME_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(EYE) ? FILLED_SHAPE : FRAME_SHAPE;
    }

    private Item getItem() {
        switch (this.type) {
            case "ruined":
                return ModItems.RUINED_EYE;
            case "golden":
                return ModItems.GOLDEN_EYE;
            case "ancient":
                return ModItems.ANCIENT_EYE;
            case "dark":
                return ModItems.DARK_EYE;
            case "withered":
                return ModItems.WITHERED_EYE;
            case "gusty":
                return ModItems.GUSTY_EYE;
            case "wealthy":
                return ModItems.WEALTHY_EYE;
            case "sandy":
                return ModItems.SANDY_EYE;
        }
        return null;
    }

    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
        return (Boolean)state.get(EYE) ? 15 : 0;
    }
}
