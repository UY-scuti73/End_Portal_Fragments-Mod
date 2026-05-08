package xyz.quazaros.epfragments73.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import xyz.quazaros.epfragments73.main;

public class ModItems {

    public static Item RUINED_EYE;
    public static Item GOLDEN_EYE;
    public static Item ANCIENT_EYE;
    public static Item DARK_EYE;
    public static Item WITHERED_EYE;
    public static Item GUSTY_EYE;
    public static Item WEALTHY_EYE;
    public static Item SANDY_EYE;

    public static void registerItems() {
        main.LOGGER.info("Registering Mod Items For " + main.MOD_ID);

        initializeItems();

        registerItemGroupEntries();
    }

    public static void initializeItems() {
        RUINED_EYE = registerItem("ruined_eye");
        GOLDEN_EYE = registerItem("golden_eye");
        ANCIENT_EYE = registerItem("ancient_eye");
        DARK_EYE = registerItem("dark_eye");
        WITHERED_EYE = registerItem("withered_eye");
        GUSTY_EYE = registerItem("gusty_eye");
        WEALTHY_EYE = registerItem("wealthy_eye");
        SANDY_EYE = registerItem("sandy_eye");
    }

    public static void registerItemGroupEntries() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.accept(RUINED_EYE);
            entries.accept(GOLDEN_EYE);
            entries.accept(ANCIENT_EYE);
            entries.accept(DARK_EYE);
            entries.accept(WITHERED_EYE);
            entries.accept(GUSTY_EYE);
            entries.accept(WEALTHY_EYE);
            entries.accept(SANDY_EYE);
        });
    }

    private static Item registerItem(String name) {
        Item item = new Item(getItemSettings(name));
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(main.MOD_ID, name), item);
    }

    private static Item.Properties getItemSettings(String name) {
        return new Item.Properties()
                .setId(ResourceKey.create(
                        Registries.ITEM,
                        Identifier.fromNamespaceAndPath(main.MOD_ID, name)
                ));
    }
}