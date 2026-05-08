package xyz.quazaros.epfragments73.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.quazaros.epfragments73.epfragments73;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, epfragments73.MODID);

    public static final RegistryObject<Item> RUINED_EYE   = registerItem("ruined");
    public static final RegistryObject<Item> GOLDEN_EYE   = registerItem("golden");
    public static final RegistryObject<Item> ANCIENT_EYE  = registerItem("ancient");
    public static final RegistryObject<Item> DARK_EYE     = registerItem("dark");
    public static final RegistryObject<Item> WITHERED_EYE = registerItem("withered");
    public static final RegistryObject<Item> GUSTY_EYE    = registerItem("gusty");
    public static final RegistryObject<Item> WEALTHY_EYE  = registerItem("wealthy");
    public static final RegistryObject<Item> SANDY_EYE    = registerItem("sandy");

    public static RegistryObject<Item> registerItem(String type) {
        String name = type + "_eye";
        return ITEMS.register(name, () ->
                new Item(
                        new Item.Properties().setId(
                                ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(epfragments73.MODID, name))
                        )
                ));
    }

    public static void register(IEventBus modBusGroup) {
        ITEMS.register(modBusGroup);
    }
}