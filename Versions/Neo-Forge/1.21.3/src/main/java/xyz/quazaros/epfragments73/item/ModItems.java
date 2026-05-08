package xyz.quazaros.epfragments73.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.bus.api.IEventBus;
import xyz.quazaros.epfragments73.epfragments73;

public class ModItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(epfragments73.MODID);

    // Using the standard register method with a lambda supplier
    public static final DeferredItem<Item> RUINED_EYE =
            registerItem("ruined");
    public static final DeferredItem<Item> GOLDEN_EYE =
            registerItem("golden");
    public static final DeferredItem<Item> ANCIENT_EYE =
            registerItem("ancient");
    public static final DeferredItem<Item> DARK_EYE =
            registerItem("dark");
    public static final DeferredItem<Item> WITHERED_EYE =
            registerItem("withered");
    public static final DeferredItem<Item> GUSTY_EYE =
            registerItem("gusty");
    public static final DeferredItem<Item> WEALTHY_EYE =
            registerItem("wealthy");
    public static final DeferredItem<Item> SANDY_EYE =
            registerItem("sandy");

    public static DeferredItem<Item> registerItem(String type) {
        String name = type + "_eye";

        return ITEMS.register(name, (id) ->
                new Item(
                        new Item.Properties().setId(
                                ResourceKey.create(Registries.ITEM, id))
                ));
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}