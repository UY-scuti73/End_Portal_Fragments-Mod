package xyz.quazaros.epfragments73.item.util;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.bus.api.SubscribeEvent;
import xyz.quazaros.epfragments73.config.ConfigManager;
import xyz.quazaros.epfragments73.item.ModItems;

public class LootTables {

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        Identifier id = event.getName();

        int ruined  = (int) (ConfigManager.get().ruined_chance  * 100);
        int withered = (int) (ConfigManager.get().withered_chance * 100);
        int golden  = (int) (ConfigManager.get().golden_chance  * 100 * 0.8);
        int gusty   = (int) (ConfigManager.get().gusty_chance   * 100 * 0.3);
        int ancient = (int) (ConfigManager.get().ancient_chance * 100 * 0.1);
        int dark    = (int) (ConfigManager.get().dark_chance    * 100);
        int wealthy = (int) (ConfigManager.get().wealthy_chance * 100);
        int sandy   = (int) (ConfigManager.get().sandy_chance   * 100);

        // Elder Guardian
        if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "entities/elder_guardian"))) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.RUINED_EYE.get()).setWeight(ruined))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - ruined))
                    .build();
            event.getTable().addPool(pool);
        }

        // Wither
        if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "entities/wither"))) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.WITHERED_EYE.get()).setWeight(withered))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - withered))
                    .build();
            event.getTable().addPool(pool);
        }

        // Piglin Bartering
        if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "gameplay/piglin_bartering"))) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.GOLDEN_EYE.get()).setWeight(golden))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - golden))
                    .build();
            event.getTable().addPool(pool);
        }

        // Trial Chambers
        if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/trial_chambers/reward_ominous_unique"))) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.GUSTY_EYE.get()).setWeight(gusty))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - gusty))
                    .build();
            event.getTable().addPool(pool);
        }

        // Trail Ruins
        if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "archaeology/trail_ruins_rare"))) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.ANCIENT_EYE.get()).setWeight(ancient))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - ancient))
                    .build();
            event.getTable().addPool(pool);
        }

        // Ancient City
        if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/ancient_city"))) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.DARK_EYE.get()).setWeight(dark))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - dark))
                    .build();
            event.getTable().addPool(pool);
        }

        // Woodland Mansion
        if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/woodland_mansion"))) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.WEALTHY_EYE.get()).setWeight(wealthy))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - wealthy))
                    .build();
            event.getTable().addPool(pool);
        }

        // Desert Pyramid
        if (id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/desert_pyramid"))) {
            LootPool pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.SANDY_EYE.get()).setWeight(sandy))
                    .add(EmptyLootItem.emptyItem().setWeight(100 - sandy))
                    .build();
            event.getTable().addPool(pool);
        }
    }
}