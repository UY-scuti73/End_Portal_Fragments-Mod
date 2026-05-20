package xyz.quazaros.epfragments73.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
// These two are the only ones needed for the Register itself
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.neoforged.bus.api.IEventBus;
import xyz.quazaros.epfragments73.epfragments73;
import xyz.quazaros.epfragments73.item.EndPortals.AbstractEndPortal;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(epfragments73.MODID);

    public static final DeferredBlock<Block> RUINED_ENDPORTAL_FRAME =
            registerBlock("ruined");
    public static final DeferredBlock<Block> GOLDEN_ENDPORTAL_FRAME =
            registerBlock("golden");
    public static final DeferredBlock<Block> ANCIENT_ENDPORTAL_FRAME =
            registerBlock("ancient");
    public static final DeferredBlock<Block> DARK_ENDPORTAL_FRAME =
            registerBlock("dark");
    public static final DeferredBlock<Block> WITHERED_ENDPORTAL_FRAME =
            registerBlock("withered");
    public static final DeferredBlock<Block> GUSTY_ENDPORTAL_FRAME =
            registerBlock("gusty");
    public static final DeferredBlock<Block> WEALTHY_ENDPORTAL_FRAME =
            registerBlock("wealthy");
    public static final DeferredBlock<Block> SANDY_ENDPORTAL_FRAME =
            registerBlock("sandy");

    private static DeferredBlock<Block> registerBlock(String type) {
        String name = type + "_end_portal_frame";

        DeferredBlock<Block> block = BLOCKS.register(name, (id) ->
                new AbstractEndPortal(
                        BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id)),
                        type
                )
        );

        ModItems.ITEMS.registerSimpleBlockItem(name, block);

        return block;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}