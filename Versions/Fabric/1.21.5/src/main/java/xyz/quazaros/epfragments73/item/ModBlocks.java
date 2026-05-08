package xyz.quazaros.epfragments73.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
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
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(RUINED_ENDPORTAL_FRAME);
            entries.add(GOLDEN_ENDPORTAL_FRAME);
            entries.add(ANCIENT_ENDPORTAL_FRAME);
            entries.add(DARK_ENDPORTAL_FRAME);
            entries.add(WITHERED_ENDPORTAL_FRAME);
            entries.add(GUSTY_ENDPORTAL_FRAME);
            entries.add(WEALTHY_ENDPORTAL_FRAME);
            entries.add(SANDY_ENDPORTAL_FRAME);
        });
    }

    private static Block registerBlock(String type) {
        String name = type + "_end_portal_frame";
        Block block = new AbstractEndPortal(getBlockSettings(name), type);
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(main.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(main.MOD_ID, name), new BlockItem(block, getItemSettings(name)));
    }

    private static Item.Settings getItemSettings(String name) {
        Item.Settings settings = new Item.Settings()
                .useItemPrefixedTranslationKey()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(main.MOD_ID, name)));
        return settings;
    }

    private static AbstractBlock.Settings getBlockSettings(String name) {
        AbstractBlock.Settings settings = AbstractBlock.Settings.create()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(main.MOD_ID, name)));
        return settings;
    }
}
