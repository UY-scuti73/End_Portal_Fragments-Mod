package xyz.quazaros.epfragments73.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import xyz.quazaros.epfragments73.epfragments73;
import xyz.quazaros.epfragments73.item.EndPortals.AbstractEndPortal;
import xyz.quazaros.epfragments73.item.ModItems;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, epfragments73.MODID);

    public static final DeferredHolder<Block, Block> RUINED_ENDPORTAL_FRAME =
            registerBlock("ruined");
    public static final DeferredHolder<Block, Block> GOLDEN_ENDPORTAL_FRAME =
            registerBlock("golden");
    public static final DeferredHolder<Block, Block> ANCIENT_ENDPORTAL_FRAME =
            registerBlock("ancient");
    public static final DeferredHolder<Block, Block> DARK_ENDPORTAL_FRAME =
            registerBlock("dark");
    public static final DeferredHolder<Block, Block> WITHERED_ENDPORTAL_FRAME =
            registerBlock("withered");
    public static final DeferredHolder<Block, Block> GUSTY_ENDPORTAL_FRAME =
            registerBlock("gusty");
    public static final DeferredHolder<Block, Block> WEALTHY_ENDPORTAL_FRAME =
            registerBlock("wealthy");
    public static final DeferredHolder<Block, Block> SANDY_ENDPORTAL_FRAME =
            registerBlock("sandy");

    private static DeferredHolder<Block, Block> registerBlock(String type) {
        String name = type + "_end_portal_frame";
        DeferredHolder<Block, Block> block = BLOCKS.register(name,
                () -> new AbstractEndPortal(Block.Properties.of(), type));
        ModItems.ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}