package xyz.quazaros.epfragments73;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.quazaros.epfragments73.item.ModBlocks;
import xyz.quazaros.epfragments73.item.ModItems;

import static xyz.quazaros.epfragments73.item.EndPortals.PortalHelper.tryPortal;
import static xyz.quazaros.epfragments73.item.util.LootTables.registerLootTables;
import static xyz.quazaros.epfragments73.item.util.PortalListener.registerPortalListener;

public class main implements ModInitializer {
    public static final String MOD_ID = "epfragments73";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModBlocks.registerBlocks();

        //Initializes loot tables
        registerLootTables();

        //Set up listener for normal end portal on use method call
        registerPortalListener();

        LOGGER.info("EndPortalChallenge Initialized");
    }
}
