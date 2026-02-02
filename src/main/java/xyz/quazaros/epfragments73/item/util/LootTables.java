package xyz.quazaros.epfragments73.item.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.AMDOcclusionQueryEvent;
import xyz.quazaros.epfragments73.config.ConfigManager;
import xyz.quazaros.epfragments73.item.ModItems;
import xyz.quazaros.epfragments73.main;

public class LootTables {
    public static void registerLootTables() {
        int ruined = ConfigManager.get().ruined_chance;
        int withered = ConfigManager.get().withered_chance;
        int golden = ConfigManager.get().golden_chance;
        int gusty = ConfigManager.get().gusty_chance;
        int ancient = ConfigManager.get().ancient_chance;
        int dark = ConfigManager.get().dark_chance;
        int wealthy = ConfigManager.get().wealthy_chance;
        int sandy = ConfigManager.get().sandy_chance;

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "entities/elder_guardian"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.RUINED_EYE).weight(ruined))
                        .with(EmptyEntry.builder().weight(2));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "entities/wither"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WITHERED_EYE).weight(withered));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "gameplay/piglin_bartering"))) {
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.GOLDEN_EYE).weight(golden * 8));
                });
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/trial_chambers/reward_ominous_unique"))) {
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.GUSTY_EYE).weight(gusty * 3));
                });
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "archaeology/trail_ruins_rare"))) {
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.ANCIENT_EYE).weight(ancient * 2));
                });
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/ancient_city"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.DARK_EYE).weight(dark))
                        .with(EmptyEntry.builder().weight(14));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/woodland_mansion"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WEALTHY_EYE).weight(wealthy))
                        .with(EmptyEntry.builder().weight(2));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/desert_pyramid"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.SANDY_EYE).weight(sandy))
                        .with(EmptyEntry.builder().weight(4));

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
