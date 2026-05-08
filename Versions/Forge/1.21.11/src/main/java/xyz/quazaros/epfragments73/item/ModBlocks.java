package xyz.quazaros.epfragments73.item;

import net.minecraft.client.ResourceLoadStateTracker;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.quazaros.epfragments73.epfragments73;
import xyz.quazaros.epfragments73.item.EndPortals.AbstractEndPortal;

import static xyz.quazaros.epfragments73.item.util.ParchmentMappings.getPath;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, epfragments73.MODID);

    public static final RegistryObject<Block> RUINED_ENDPORTAL_FRAME   = registerBlock("ruined");
    public static final RegistryObject<Block> GOLDEN_ENDPORTAL_FRAME   = registerBlock("golden");
    public static final RegistryObject<Block> ANCIENT_ENDPORTAL_FRAME  = registerBlock("ancient");
    public static final RegistryObject<Block> DARK_ENDPORTAL_FRAME     = registerBlock("dark");
    public static final RegistryObject<Block> WITHERED_ENDPORTAL_FRAME = registerBlock("withered");
    public static final RegistryObject<Block> GUSTY_ENDPORTAL_FRAME    = registerBlock("gusty");
    public static final RegistryObject<Block> WEALTHY_ENDPORTAL_FRAME  = registerBlock("wealthy");
    public static final RegistryObject<Block> SANDY_ENDPORTAL_FRAME    = registerBlock("sandy");

    private static RegistryObject<Block> registerBlock(String type) {
        String name = type + "_end_portal_frame";

        RegistryObject<Block> block = BLOCKS.register(name, () ->
                new AbstractEndPortal(
                        BlockBehaviour.Properties.of().setId(
                                ResourceKey.create(Registries.BLOCK, getPath(epfragments73.MODID, name))
                        ),
                        type
                )
        );

        ModItems.ITEMS.register(name, () ->
                new BlockItem(block.get(), new Item.Properties().setId(
                        ResourceKey.create(Registries.ITEM, getPath(epfragments73.MODID, name))
                ))
        );

        return block;
    }

    public static void register(BusGroup modBusGroup) {
        BLOCKS.register(modBusGroup);
    }
}