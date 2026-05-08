package xyz.quazaros.epfragments73.item.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import xyz.quazaros.epfragments73.config.ConfigManager;
import xyz.quazaros.epfragments73.item.ModItems;

public class LootTables {
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
            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "entities/elder_guardian"))) {
                int chance = (int) (ruined * 100);
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.RUINED_EYE).weight(chance))
                        .with(EmptyEntry.builder().weight(100-chance));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "entities/wither"))) {
                int chance = (int) (withered * 100);
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WITHERED_EYE).weight(chance))
                        .with(EmptyEntry.builder().weight(100-chance));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "gameplay/piglin_bartering"))) {
                int chance = (int) (golden * 100 * (0.8));
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.GOLDEN_EYE).weight(chance));
                });
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/trial_chambers/reward_ominous_unique"))) {
                int chance = (int) (gusty * 100 * (0.3));
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.GUSTY_EYE).weight(chance));
                });
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "archaeology/trail_ruins_rare"))) {
                int chance = (int) (ancient * 100 * (0.1));
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.ANCIENT_EYE).weight(chance));
                });
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/ancient_city"))) {
                int chance = (int) (dark * 100);
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.DARK_EYE).weight(chance))
                        .with(EmptyEntry.builder().weight(100-chance));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/woodland_mansion"))) {
                int chance = (int) (wealthy * 100);
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WEALTHY_EYE).weight(chance))
                        .with(EmptyEntry.builder().weight(100-chance));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/desert_pyramid"))) {
                int chance = (int) (sandy * 100);
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.SANDY_EYE).weight(chance))
                        .with(EmptyEntry.builder().weight(100-chance));

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
