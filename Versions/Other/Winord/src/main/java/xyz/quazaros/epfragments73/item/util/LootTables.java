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
import xyz.quazaros.epfragments73.item.ModItems;
import xyz.quazaros.epfragments73.main;

public class LootTables {
    public static void registerLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "entities/elder_guardian"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.RUINED_EYE).weight(1))
                        .with(EmptyEntry.builder().weight(2));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "entities/wither"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.WITHERED_EYE).weight(1));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/trial_chambers/reward_ominous_unique"))) {
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.GUSTY_EYE).weight(3));
                });
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "archaeology/trail_ruins_rare"))) {
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.ANCIENT_EYE).weight(2));
                });
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/ancient_city"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.DARK_EYE).weight(1))
                        .with(EmptyEntry.builder().weight(14));

                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && key.getValue().equals(Identifier.of("minecraft", "chests/desert_pyramid"))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.SANDY_EYE).weight(1))
                        .with(EmptyEntry.builder().weight(4));

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
