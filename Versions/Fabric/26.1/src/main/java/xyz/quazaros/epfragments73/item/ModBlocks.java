package xyz.quazaros.epfragments73.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import xyz.quazaros.epfragments73.item.EndPortals.AbstractEndPortal;
import xyz.quazaros.epfragments73.main;

public class ModBlocks {

    public static Block RUINED_ENDPORTAL_FRAME;
    public static Block GOLDEN_ENDPORTAL_FRAME;
    public static Block ANCIENT_ENDPORTAL_FRAME;
    public static Block DARK_ENDPORTAL_FRAME;
    public static Block WITHERED_ENDPORTAL_FRAME;
    public static Block GUSTY_ENDPORTAL_FRAME;
    public static Block WEALTHY_ENDPORTAL_FRAME;
    public static Block SANDY_ENDPORTAL_FRAME;

    public static void registerBlocks() {
        main.LOGGER.info("Registering Mod Blocks For " + main.MOD_ID);

        initializeBlocks();

        registerItemGroupEntries();
    }

    private static void initializeBlocks() {
        RUINED_ENDPORTAL_FRAME = registerBlock("ruined");
        GOLDEN_ENDPORTAL_FRAME = registerBlock("golden");
        ANCIENT_ENDPORTAL_FRAME = registerBlock("ancient");
        DARK_ENDPORTAL_FRAME = registerBlock("dark");
        WITHERED_ENDPORTAL_FRAME = registerBlock("withered");
        GUSTY_ENDPORTAL_FRAME = registerBlock("gusty");
        WEALTHY_ENDPORTAL_FRAME = registerBlock("wealthy");
        SANDY_ENDPORTAL_FRAME = registerBlock("sandy");
    }

    private static void registerItemGroupEntries() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.accept(RUINED_ENDPORTAL_FRAME);
            entries.accept(GOLDEN_ENDPORTAL_FRAME);
            entries.accept(ANCIENT_ENDPORTAL_FRAME);
            entries.accept(DARK_ENDPORTAL_FRAME);
            entries.accept(WITHERED_ENDPORTAL_FRAME);
            entries.accept(GUSTY_ENDPORTAL_FRAME);
            entries.accept(WEALTHY_ENDPORTAL_FRAME);
            entries.accept(SANDY_ENDPORTAL_FRAME);
        });
    }

    private static Block registerBlock(String type) {
        String name = type + "_end_portal_frame";

        Block block = new AbstractEndPortal(
                getBlockProperties(name),
                type
        );

        registerBlockItem(name, block);

        return Registry.register(
                BuiltInRegistries.BLOCK,
                Identifier.fromNamespaceAndPath(main.MOD_ID, name),
                block
        );
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(main.MOD_ID, name),
                new BlockItem(block, getItemProperties(name))
        );
    }

    private static Item.Properties getItemProperties(String name) {
        return new Item.Properties()
                .setId(ResourceKey.create(
                        BuiltInRegistries.ITEM.key(),
                        Identifier.fromNamespaceAndPath(main.MOD_ID, name)
                ));
    }

    private static BlockBehaviour.Properties getBlockProperties(String name) {
        return BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(
                        BuiltInRegistries.BLOCK.key(),
                        Identifier.fromNamespaceAndPath(main.MOD_ID, name)
                ));
    }
}