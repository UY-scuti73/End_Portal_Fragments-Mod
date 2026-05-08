package xyz.quazaros.epfragments73.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
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
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(RUINED_EYE);
            entries.add(GOLDEN_EYE);
            entries.add(ANCIENT_EYE);
            entries.add(DARK_EYE);
            entries.add(WITHERED_EYE);
            entries.add(GUSTY_EYE);
            entries.add(WEALTHY_EYE);
            entries.add(SANDY_EYE);
        });
    }

    private static Item registerItem(String name) {
        Item item = new Item(getItemSettings(name));
        return Registry.register(Registries.ITEM, Identifier.of(main.MOD_ID, name), item);
    }

    private static Item.Settings getItemSettings(String name) {
        Item.Settings settings = new Item.Settings()
                .useItemPrefixedTranslationKey()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(main.MOD_ID, name)));
        return settings;
    }
}
