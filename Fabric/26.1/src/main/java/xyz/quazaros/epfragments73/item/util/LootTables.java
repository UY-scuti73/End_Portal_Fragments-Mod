package xyz.quazaros.epfragments73.item.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

import net.minecraft.resources.Identifier;

import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;

import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import xyz.quazaros.epfragments73.config.ConfigManager;
import xyz.quazaros.epfragments73.item.ModItems;

public class LootTables {

    private static final Identifier ELDER_GUARDIAN = Identifier.fromNamespaceAndPath("minecraft", "entities/elder_guardian");
    private static final Identifier WITHER =  Identifier.fromNamespaceAndPath("minecraft", "entities/wither");

    public static void registerLootTables() {

        double ruined = ConfigManager.get().ruined_chance;
        double withered = ConfigManager.get().withered_chance;
        double golden = ConfigManager.get().golden_chance;
        double gusty = ConfigManager.get().gusty_chance;
        double ancient = ConfigManager.get().ancient_chance;
        double dark = ConfigManager.get().dark_chance;
        double wealthy = ConfigManager.get().wealthy_chance;
        double sandy = ConfigManager.get().sandy_chance;

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (source.isBuiltin() && key.identifier().equals(ELDER_GUARDIAN)) {

                int chance = (int) (ruined * 100);

                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.RUINED_EYE).setWeight(chance))
                        .add(EmptyLootItem.emptyItem().setWeight(100 - chance));

                tableBuilder.pool(poolBuilder.build());
            }

            if (source.isBuiltin() && key.identifier().equals(WITHER)) {

                int chance = (int) (withered * 100);

                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.WITHERED_EYE).setWeight(chance))
                        .add(EmptyLootItem.emptyItem().setWeight(100 - chance));

                tableBuilder.pool(poolBuilder.build());
            }

            if (source.isBuiltin() && key.equals(BuiltInLootTables.PIGLIN_BARTERING)) {

                int chance = (int) (golden * 100 * 0.8);

                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.add(
                            LootItem.lootTableItem(ModItems.GOLDEN_EYE)
                                    .setWeight(chance)
                    );
                });
            }

            if (source.isBuiltin() && key.equals(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE)) {

                int chance = (int) (gusty * 100 * 0.3);

                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.add(
                            LootItem.lootTableItem(ModItems.GUSTY_EYE)
                                    .setWeight(chance)
                    );
                });
            }

            if (source.isBuiltin() && key.equals(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE)) {

                int chance = (int) (ancient * 100 * 0.1);

                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.add(
                            LootItem.lootTableItem(ModItems.ANCIENT_EYE)
                                    .setWeight(chance)
                    );
                });
            }

            if (source.isBuiltin() && key.equals(BuiltInLootTables.ANCIENT_CITY)) {

                int chance = (int) (dark * 100);

                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.DARK_EYE).setWeight(chance))
                        .add(EmptyLootItem.emptyItem().setWeight(100 - chance));

                tableBuilder.pool(poolBuilder.build());
            }

            if (source.isBuiltin() && key.equals(BuiltInLootTables.WOODLAND_MANSION)) {

                int chance = (int) (wealthy * 100);

                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.WEALTHY_EYE).setWeight(chance))
                        .add(EmptyLootItem.emptyItem().setWeight(100 - chance));

                tableBuilder.pool(poolBuilder.build());
            }

            if (source.isBuiltin() && key.equals(BuiltInLootTables.DESERT_PYRAMID)) {

                int chance = (int) (sandy * 100);

                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.SANDY_EYE).setWeight(chance))
                        .add(EmptyLootItem.emptyItem().setWeight(100 - chance));

                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}