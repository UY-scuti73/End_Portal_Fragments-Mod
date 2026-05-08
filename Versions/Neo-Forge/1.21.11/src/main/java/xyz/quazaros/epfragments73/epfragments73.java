package xyz.quazaros.epfragments73;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;
import xyz.quazaros.epfragments73.item.ModBlocks;
import xyz.quazaros.epfragments73.item.ModItems;
import xyz.quazaros.epfragments73.item.util.LootTables;
import xyz.quazaros.epfragments73.item.util.PortalListener;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(epfragments73.MODID)
public class epfragments73 {

    public static final String MODID = "epfragments73";
    public static final Logger LOGGER = LogUtils.getLogger();

    public epfragments73(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        PortalListener.register();

        NeoForge.EVENT_BUS.register(LootTables.class);

        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            // Items
            event.accept(ModItems.RUINED_EYE.get());
            event.accept(ModItems.GOLDEN_EYE.get());
            event.accept(ModItems.ANCIENT_EYE.get());
            event.accept(ModItems.DARK_EYE.get());
            event.accept(ModItems.WITHERED_EYE.get());
            event.accept(ModItems.GUSTY_EYE.get());
            event.accept(ModItems.WEALTHY_EYE.get());
            event.accept(ModItems.SANDY_EYE.get());

            // Blocks (uses the BlockItem registered alongside each block)
            event.accept(ModBlocks.RUINED_ENDPORTAL_FRAME.get());
            event.accept(ModBlocks.GOLDEN_ENDPORTAL_FRAME.get());
            event.accept(ModBlocks.ANCIENT_ENDPORTAL_FRAME.get());
            event.accept(ModBlocks.DARK_ENDPORTAL_FRAME.get());
            event.accept(ModBlocks.WITHERED_ENDPORTAL_FRAME.get());
            event.accept(ModBlocks.GUSTY_ENDPORTAL_FRAME.get());
            event.accept(ModBlocks.WEALTHY_ENDPORTAL_FRAME.get());
            event.accept(ModBlocks.SANDY_ENDPORTAL_FRAME.get());
        }
    }
}