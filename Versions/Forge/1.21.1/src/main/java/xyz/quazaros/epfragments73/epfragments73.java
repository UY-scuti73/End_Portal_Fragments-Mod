package xyz.quazaros.epfragments73;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import xyz.quazaros.epfragments73.item.ModBlocks;
import xyz.quazaros.epfragments73.item.ModItems;
import xyz.quazaros.epfragments73.item.util.LootTables;
import xyz.quazaros.epfragments73.item.util.PortalListener;

@Mod(epfragments73.MODID)
public class epfragments73 {

    public static final String MODID = "epfragments73";
    public static final Logger LOGGER = LogUtils.getLogger();

    public epfragments73(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.RUINED_EYE.get());
            event.accept(ModItems.GOLDEN_EYE.get());
            event.accept(ModItems.ANCIENT_EYE.get());
            event.accept(ModItems.DARK_EYE.get());
            event.accept(ModItems.WITHERED_EYE.get());
            event.accept(ModItems.GUSTY_EYE.get());
            event.accept(ModItems.WEALTHY_EYE.get());
            event.accept(ModItems.SANDY_EYE.get());

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