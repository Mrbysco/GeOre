package com.shynieke.geore;

import com.shynieke.geore.client.ClientHandler;
import com.shynieke.geore.client.SpyglassHandler;
import com.shynieke.geore.config.GeOreConfig;
import com.shynieke.geore.features.GeOreFeatures;
import com.shynieke.geore.registry.GeOreRegistry;
import com.shynieke.geore.worldgen.GeOreWorldgen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class GeOre {
    public static final Logger LOGGER = LogManager.getLogger();

    public GeOre() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GeOreConfig.commonSpec);
        eventBus.register(GeOreConfig.class);

        eventBus.addListener(this::setup);

//        GeOreRegistry.DECORATORS.register(eventBus);
        GeOreRegistry.BLOCKS.register(eventBus);
        GeOreRegistry.ITEMS.register(eventBus);

        MinecraftForge.EVENT_BUS.register(new GeOreWorldgen());

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(ClientHandler::onClientSetup);
            MinecraftForge.EVENT_BUS.register(new SpyglassHandler());
        });
    }

    private void setup(final FMLCommonSetupEvent event) {
        GeOreFeatures.initialize();
    }
}
