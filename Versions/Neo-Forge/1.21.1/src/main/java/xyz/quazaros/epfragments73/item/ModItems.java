package xyz.quazaros.epfragments73.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import xyz.quazaros.epfragments73.epfragments73;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, epfragments73.MODID);

    public static final DeferredHolder<Item, Item> RUINED_EYE =
            ITEMS.register("ruined_eye", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> GOLDEN_EYE =
            ITEMS.register("golden_eye", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ANCIENT_EYE =
            ITEMS.register("ancient_eye", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> DARK_EYE =
            ITEMS.register("dark_eye", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WITHERED_EYE =
            ITEMS.register("withered_eye", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> GUSTY_EYE =
            ITEMS.register("gusty_eye", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> WEALTHY_EYE =
            ITEMS.register("wealthy_eye", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SANDY_EYE =
            ITEMS.register("sandy_eye", () -> new Item(new Item.Properties()));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}