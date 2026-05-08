package xyz.quazaros.epfragments73.item.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.quazaros.epfragments73.config.ConfigManager;
import xyz.quazaros.epfragments73.epfragments73;
import xyz.quazaros.epfragments73.item.ModItems;

@Mod.EventBusSubscriber(modid = epfragments73.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootTables {

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation id = event.getName();

        int ruined   = (int) (ConfigManager.get().ruined_chance  * 100);
        int withered = (int) (ConfigManager.get().withered_chance * 100);
        int golden   = (int) (ConfigManager.get().golden_chance  * 100 * 0.8);
        int gusty    = (int) (ConfigManager.get().gusty_chance   * 100 * 0.3);
        int ancient  = (int) (ConfigManager.get().ancient_chance * 100 * 0.1);
        int dark     = (int) (ConfigManager.get().dark_chance    * 100);
        int wealthy  = (int) (ConfigManager.get().wealthy_chance * 100);
        int sandy    = (int) (ConfigManager.get().sandy_chance   * 100);

        if (id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "entities/elder_guardian"))) {
            event.getTable().addPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.RUINED_EYE.get()).setWeight(ruined))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - ruined))
                    .build());
        }

        if (id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "entities/wither"))) {
            event.getTable().addPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.WITHERED_EYE.get()).setWeight(withered))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - withered))
                    .build());
        }

        if (id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "gameplay/piglin_bartering"))) {
            event.getTable().addPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.GOLDEN_EYE.get()).setWeight(golden))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - golden))
                    .build());
        }

        if (id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "chests/trial_chambers/reward_ominous_unique"))) {
            event.getTable().addPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.GUSTY_EYE.get()).setWeight(gusty))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - gusty))
                    .build());
        }

        if (id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "archaeology/trail_ruins_rare"))) {
            event.getTable().addPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.ANCIENT_EYE.get()).setWeight(ancient))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - ancient))
                    .build());
        }

        if (id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "chests/ancient_city"))) {
            event.getTable().addPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.DARK_EYE.get()).setWeight(dark))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - dark))
                    .build());
        }

        if (id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "chests/woodland_mansion"))) {
            event.getTable().addPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.WEALTHY_EYE.get()).setWeight(wealthy))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - wealthy))
                    .build());
        }

        if (id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "chests/desert_pyramid"))) {
            event.getTable().addPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.SANDY_EYE.get()).setWeight(sandy))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - sandy))
                    .build());
        }
    }
}